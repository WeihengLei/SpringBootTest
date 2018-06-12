package com.example.test.util;

import com.example.test.exceptions.BusinessException;
import com.example.test.model.TestResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HttpUriUtil {

    public static void GetData(String header, String sdType, RestTemplateTool restTemplateTool, String url) throws BusinessException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION,header);
        final HttpEntity httpEntity = new HttpEntity(null,httpHeaders);
        final ResponseEntity<TestResponse> responseResponseEntity =
                restTemplateTool.exchange(url+"/"+sdType, HttpMethod.GET,httpEntity,TestResponse.class);
        final TestResponse testResponse = responseResponseEntity.getBody();
        if (!testResponse.getRespCode().equals("200")){
            throw new BusinessException(testResponse.getRespCode(), testResponse.getMessage());
        }
    }

    public static void test(String header, String sdType, RestTemplateTool restTemplateTool, String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add(HttpHeaders.AUTHORIZATION,header);
        final HttpEntity httpEntity = new HttpEntity(null,httpHeaders);
        final ResponseEntity<TestResponse> responseResponseEntity =
                restTemplateTool.exchange(url, HttpMethod.GET,httpEntity,TestResponse.class);
        final TestResponse testResponse = responseResponseEntity.getBody();
        System.out.println("====="+testResponse.getRespCode());
        System.out.println("=====2"+testResponse.getResponseBody());

        if (!testResponse.getRespCode().equals("200")){
            System.out.println("=====3"+testResponse.getResponseBody());
            //throw new BusinessException(testResponse.getRespCode(), testResponse.getMessage());
        }
    }

    public static byte[] watermarkPostHttp(String url, MultipartFile image, InputStream waterMarkInStream, Map<String, String> params) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost(url);
        //httpPost.setConfig(requestConfig);

        String fileName = image.getOriginalFilename();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody("image",image.getInputStream(), ContentType.MULTIPART_FORM_DATA,fileName);
        builder.addBinaryBody("wmimage",waterMarkInStream, ContentType.MULTIPART_FORM_DATA,"watermark.png");
        builder.setMode(HttpMultipartMode.RFC6532);

        if(params!=null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }
        }
        byte[] bytes = null;
        try {
            org.apache.http.HttpEntity httpEntity = builder.build();
            httpPost.setEntity(httpEntity);

            long startTime = System.currentTimeMillis();
            System.out.println("### RBB CALL watermarkAPI START ### use time ["+startTime+"] ms");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            System.out.println("### RBB CALL watermarkAPI END ### use time ["+(System.currentTimeMillis()-startTime)+"] ms");

            if(httpResponse != null){
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    bytes = EntityUtils.toByteArray(httpResponse.getEntity());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
