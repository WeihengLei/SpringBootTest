package com.example.test.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

public class FileUtils {

	public static void checkFile(MultipartFile multipartFile, String fileTypeCode) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			if (checkFileType(multipartFile) == false) {
				//throw new ImageFileTypeNotMatchException(fileTypeCode);
			}
			System.out.println((multipartFile.getSize() +"--"+ 1024));
			if(multipartFile.getSize() > 1024){}
				//throw new ImageSizeOverLimitException();
		}
	}

	public static void checkPDFFile(MultipartFile multipartFile, String fileTypeCode) {
		if (multipartFile != null && !multipartFile.isEmpty()) {
			if (checkPDFFileType(multipartFile) == false) {
				//throw new PdfFileTypeNotMatchException(fileTypeCode);
			}
			System.out.println((multipartFile.getSize() +"--"+1024));
			if(multipartFile.getSize() > 1024){}
				//throw new PdfSizeOverLimitException();
		}
	}

	public static String uploadFile(String folderPath , String filename, MultipartFile multipartFile) {
		String filePath = null;
		if (multipartFile != null && !multipartFile.isEmpty()) {
			try {
				String originalFilename = multipartFile.getOriginalFilename();
				if (StringUtils.isNotEmpty(originalFilename)) {
					//String folderPath = basePath + "/" + DateUtil.parseString(new Date(), "yyyyMM");
					//String fileName = IdGenerator.getInstance().gen() + "." + FilenameUtils.getExtension(originalFilename);
					//String fileName = filename + "." + FilenameUtils.getExtension(originalFilename);
					//filePath = fileService.uploadFile(multipartFile, folderPath, filename);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
	/**
	 * 将MultipartFile转化为file并保存到服务器上的某地
	 */
	public static void SaveFileFromInputStream(FileInputStream stream, String targetPath,  String fileName) throws IOException
	{

		File targetFile = new File(targetPath);
		if(!targetFile.exists()){
			targetFile.mkdir();
		}
		String filePath = targetPath + "/"+fileName;
		FileOutputStream fs=new FileOutputStream( filePath);
		byte[] buffer =new byte[1024*1024];
		int bytesum = 0;
		int byteread = 0;
		while ((byteread=stream.read(buffer))!=-1)
		{
			bytesum+=byteread;
			fs.write(buffer,0,byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	public static boolean deleteFile(String filePath) {

		File file = new File(filePath);
		if (file.isFile() && file.exists()) {
			//fileService.deleteFile(file);
			System.out.println("delete file " + filePath + "success！");
			return true;
		}
		return false;
	}


	public static boolean checkPDFFileType(MultipartFile multipartFile) {
		boolean result = false;
		String fileName = multipartFile != null && !multipartFile.isEmpty() ? multipartFile.getOriginalFilename() : null;
		if (StringUtils.isNotEmpty(fileName) && ".pdf.PDF".indexOf(FilenameUtils.getExtension(fileName)) != -1) {
			result = true;
		}

		return result;
	}

	public static boolean checkFileType(MultipartFile multipartFile) {
		boolean result = false;
		String fileName = multipartFile != null && !multipartFile.isEmpty() ? multipartFile.getOriginalFilename() : null;
		if (StringUtils.isNotEmpty(fileName) && ".jpg.JPG.png.PNG.jpeg.JPEG".indexOf(FilenameUtils.getExtension(fileName)) != -1) {
			result = true;
		}

		return result;
	}

	public static void download(String path, HttpServletRequest request, HttpServletResponse response) {
		ServletOutputStream out = null;
		FileInputStream inputStream = null;
		try {

			if (StringUtils.isNotEmpty(path)) {
				File file = new File(path);
				if (file != null && file.exists()) {
					String ext = FilenameUtils.getExtension(path);
					String contentType = ContentTypeTool.getContentType(ext);
					response.setContentType(contentType);
					response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
					response.setHeader("filesize", file.length() + "");
					inputStream = new FileInputStream(file);

					out = response.getOutputStream();

					int b = 0;
					byte[] buffer = new byte[512];
					while (-1 != (b = inputStream.read(buffer))) {
						out.write(buffer, 0, b);
					}
					inputStream.close();
					out.close();
					out.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	/*public static String cropImageFromFile(MultipartFile imgFile, Integer x, Integer y, Integer w, Integer h, FileService fileService, String basePath) {
			InputStream inputStream = null;
			String outputPath = null;
			if (imgFile.getSize() > 0) try {
				inputStream = imgFile.getInputStream();

				BufferedImage bufferedImage = ImageIO.read(inputStream);
				BufferedImage croppedImage = bufferedImage.getSubimage(x, y, w, h);
				String folderPath = basePath + "/" + DateUtil.parseString(new Date(), "yyyyMM");
				String fileName = IdGenerator.getInstance().gen() + "." + FilenameUtils.getExtension(imgFile.getOriginalFilename());
				outputPath = fileService.uploadBufferedImage(croppedImage, imgFile.getOriginalFilename(), folderPath, fileName);
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return outputPath;
	}*/


}
