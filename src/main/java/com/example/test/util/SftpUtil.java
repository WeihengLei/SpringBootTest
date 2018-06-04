package com.example.test.util;

import com.example.test.error.ErrorCode;
import com.example.test.exceptions.BusinessException;
import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jasypt.encryption.StringEncryptor;

import java.io.*;
import java.util.Properties;


public class SftpUtil {
    protected static Logger log = Logger.getLogger(SftpUtil.class);

    private ChannelSftp sftp = null;
    private Session sshSession = null;

    private String username;
    private String password;
    private String host;
    private String priKeyFile;
    private String passphrase;
    private int port;

    public SftpUtil(String username, String password, String host, int port,
                    String priKeyFile, String passphrase) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.priKeyFile = priKeyFile;
        this.passphrase = passphrase;
    }

    /**
     * connection sftp service
     * @return ChannelSftp sftp
     * @throws BusinessException
     */
    public ChannelSftp connect() throws BusinessException {
        JSch jsch = new JSch();
        try {
            if(StringUtils.isNotEmpty(priKeyFile)){
                if(StringUtils.isNotEmpty(passphrase)){
                    jsch.addIdentity(priKeyFile, passphrase);
                }else{
                    jsch.addIdentity(priKeyFile);
                }
            }
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(properties);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("ftp---Connected to " + host);
        }
        catch (JSchException e) {
            throw new BusinessException(ErrorCode.SFTP_CONNECT_EXCEPTION,"sftp connect exception");
        }
        return sftp;
    }

    /**
     * download file
     * @param directory       path: /xxx/xxx/xxx/
     * @param remoteFileName  file name：xxx.txt ||xxx.txt.zip
     * @param localFile       local path: D:\\xxx.txt
     * @return  File
     * @throws BusinessException
     */
    public  File downloadFile(String directory, String remoteFileName, String localFile) throws BusinessException {
        connect();
        File file = null;
        OutputStream output = null;
        try {
            file = new File(localFile);
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            sftp.cd(directory);
            output = new FileOutputStream(file);
            sftp.get(remoteFileName, output);
        }
        catch (Exception e) {
            throw new BusinessException(ErrorCode.SFTP_DOWNLOAD_EXCEPTION,"sftp download file exception:"+e.getMessage());
        }
        finally {
            if (output != null) {
                try {
                    output.close();
                }
                catch (IOException e) {
                    throw new BusinessException(ErrorCode.CLOSE_STREAM_ERROR,"Close stream error."+ e.getMessage());
                }
            }
            disconnect();
        }
        return file;
    }

    /**
     * download As ByteArrayOutputStream
     * @param directory       path: /xxx/xxx/xxx/
     * @param remoteFileName  file name：xxx.txt ||xxx.txt.zip
     * @return  File
     * @throws BusinessException
     */
    public  ByteArrayOutputStream downloadAsByte(String directory, String remoteFileName) throws BusinessException {
        connect();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            sftp.cd(directory);
            sftp.get(remoteFileName,os);
        }
        catch (Exception e) {
            throw new BusinessException(ErrorCode.SFTP_DOWNLOAD_EXCEPTION,"sftp download file exception:"+e.getMessage());
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                }
                catch (IOException e) {
                    throw new BusinessException(ErrorCode.CLOSE_STREAM_ERROR,"Close stream error."+ e.getMessage());
                }
            }
            disconnect();
        }
        return os;
    }

    /**
     * closed connection
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                this.sftp = null;
                log.info("sftp is closed already");
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                this.sshSession = null;
                log.info("sshSession is closed already");
            }
        }
    }

    public void osClose(ByteArrayOutputStream os) throws BusinessException {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.CLOSE_STREAM_ERROR, "Close stream error." + e.getMessage());
            }
        }
    }


}