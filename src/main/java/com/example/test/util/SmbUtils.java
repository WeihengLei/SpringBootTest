package com.example.test.util;

import com.hierynomus.msdtyp.AccessMask;
import com.hierynomus.msfscc.FileAttributes;
import com.hierynomus.mssmb2.SMB2CreateDisposition;
import com.hierynomus.mssmb2.SMB2CreateOptions;
import com.hierynomus.mssmb2.SMB2ShareAccess;
import com.hierynomus.mssmb2.SMBApiException;
import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.SmbConfig;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import com.hierynomus.smbj.share.DiskShare;
import jcifs.*;
import jcifs.smb.*;
import jcifs.smb.SmbSession;
import jcifs.util.LogStream;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashSet;

//import jcifs.config.PropertyConfiguration;
//import jcifs.context.BaseContext;


public class SmbUtils {
	private static LogStream log = LogStream.getInstance();  //打印日志
	private String url = "";
	private SmbFile smbFile = null;
	private SmbFileOutputStream smbOut = null;
	private static SmbUtils smb = null; //共享文件协议

	public static synchronized SmbUtils getInstance(String url) {
		if (smb == null)
			return new SmbUtils(url);
		return smb;
	}

	/**
	 * @param url server url
	 */
	private SmbUtils(String url) {
		this.url = url;
		this.init();
	}

	public void init() {
		try {
			log.println("Start connect...url：" + this.url);
			smbFile = new SmbFile(this.url);
			smbFile.connect();
			log.println("Connect success...url：" + this.url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.print(e);
		} catch (IOException e) {
			e.printStackTrace();
			log.print(e);
		}
	}


	/**
	 *  upload file to share folder
	 */
	public String uploadFile(File file, String remoteUrl) {
		String errorMsg = "";
		int flag = -1;
		BufferedInputStream bf = null;
		try {
			log.println("Start upload ["+ file.getName() +"] to remote file: "+remoteUrl+ file.getName());
			bf = new BufferedInputStream(new FileInputStream(file));
			SmbFile hasRemoteUrl = new SmbFile(remoteUrl);
			if (!hasRemoteUrl.exists()) {
				hasRemoteUrl.mkdirs();
			}
			smbOut = new SmbFileOutputStream(remoteUrl + file.getName(), false);

			byte[] bt = new byte[8192];
			int n = bf.read(bt);
			while (n != -1) {
				this.smbOut.write(bt, 0, n);
				this.smbOut.flush();
				n = bf.read(bt);
			}
			flag = 0;
			log.println("Upload ["+ file.getName() +"]to remote file: "+remoteUrl+ file.getName()+" success!!");

		} catch (SmbException e) {
			e.printStackTrace();
			log.println("SmbException:"+e);
			errorMsg = "Upload ["+ file.getName() +"] to network drive fail: "+e.getMessage();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.println("MalformedURLException:"+e);
			errorMsg = "Upload ["+ file.getName() +"] to network drive fail: "+e.getMessage();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			log.println("UnknownHostException:" + this.url);
			errorMsg = "Upload ["+ file.getName() +"] to network drive fail: "+e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			log.println("IOException:"+e);
			errorMsg = "Upload ["+ file.getName() +"] to network drive fail: "+e.getMessage();
		} finally {
			try {
				if (null != this.smbOut)
					this.smbOut.close();
				if (null != bf)
					bf.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return errorMsg;
	}

	/**
	 *  upload file to share folder
	 */
	public static String uploadFileBySmbj(File file, String remoteIp, String remoteLoginUser, String remoteLoginPass, String remoteDomain, String shareName, String filePath) {
		String errorMsg = "";
		SmbConfig cfg = SmbConfig.builder().withMultiProtocolNegotiate(true).withSigningRequired(true).withMultiProtocolNegotiate(true).withSigningRequired(false).build();//

		SMBClient client = new SMBClient(cfg);
		AuthenticationContext ac = null;
		Session session = null;
		Connection connection = null;

		try {
			log.println("Start connect====="+remoteIp);
			connection =  client.connect(remoteIp,445);
			log.println("--- connect IP success ---");
			if(StringUtils.isNotEmpty(remoteLoginUser)){
				ac = new AuthenticationContext(remoteLoginUser, remoteLoginPass.toCharArray(), remoteDomain);
			}else{
				ac = new AuthenticationContext("", new char[0], (String)null); //anonymous
				//ac = new AuthenticationContext("Guest", new char[0], (String)null); //guest

			}
			session = connection.authenticate(ac);
			log.println("--- connect session success ---");
			// Connect to Share
			try (DiskShare share = (DiskShare) session.connectShare(shareName)) {
				// this is com.hierynomus.smbj.share.File !
				com.hierynomus.smbj.share.File f = null;
				int idx = filePath.lastIndexOf("/");

				// if file is in folder(s), create them first
				if(idx > -1) {
					String folder = filePath.substring(0, idx);
					try {
						if(!share.folderExists(folder)) share.mkdir(folder);
					} catch (SMBApiException ex) {
						throw new IOException(ex);
					}
				}
				// I am creating file with flag FILE_CREATE, which will throw if file exists already
				if(!share.fileExists(filePath)){
					f = share.openFile(filePath,
							new HashSet<>(Arrays.asList(AccessMask.GENERIC_ALL)),
							new HashSet<>(Arrays.asList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
							SMB2ShareAccess.ALL,
							SMB2CreateDisposition.FILE_CREATE,
							new HashSet<>(Arrays.asList(SMB2CreateOptions.FILE_DIRECTORY_FILE))
					);
				}else{
					f = share.openFile(filePath,
							new HashSet<>(Arrays.asList(AccessMask.GENERIC_ALL)),
							new HashSet<>(Arrays.asList(FileAttributes.FILE_ATTRIBUTE_NORMAL)),
							SMB2ShareAccess.ALL,
							SMB2CreateDisposition.FILE_OVERWRITE,
							new HashSet<>(Arrays.asList(SMB2CreateOptions.FILE_DIRECTORY_FILE))
					);
				}
				OutputStream os = f.getOutputStream();
				os.write(toByteArray(file));
				os.close();
				log.println("--- end: "+filePath+" ---");
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorMsg = "Upload ["+ file.getName() +"] to network drive fail: "+e.getMessage();
		}finally {
			try {
				if(session!= null){
					session = null;
				}
				if((connection!= null)&&(connection.isConnected())){
					connection.close();
				}
			}catch (Exception e) {

			}
		}

		return errorMsg;
	}



	public static void main(String[] args) throws UnknownHostException, SmbException, MalformedURLException {
		File  file= new File("/Users/weiheng.lei/ageas/updatefile/updateftlife.plist");
		//uploadFileBySmb3(file, "192.168.7.178","test","updateftlife.plist");


		//服務器地址 格式為 smb://电脑用户名:电脑密码@电脑IP地址/IP共享的文件夹
		/*String remoteUrl = "smb://jian:123@192.168.7.178/test/";

		//String remoteUrl = "smb://DESKTOP-FU92EA3/test/";
		String allZip = "/Users/weiheng.lei/ageas/allfile/allftlife.plist";  //本地要上传的文件
		String updateZip = "/Users/weiheng.lei/ageas/updatefile/updateftlife.plist";
		String plist = "/Users/weiheng.lei/ageas/file/ftlife.plist";

		File allZipFile = new File(allZip);
		File updateZipFile = new File(updateZip);
		File plistFile = new File(plist);

		String allZipUrl = "smb://jian:123@192.168.7.178/test/allfile/";
		String updateZipUrl = "smb://jian:123@192.168.7.178/test/updatefile/";
		String plistUrl = "smb://jian:123@192.168.7.178/test/";

		SmbUtils smb = SmbUtils.getInstance(remoteUrl);
		smb.uploadFile(allZipFile, allZipUrl);
		//SmbUtils smb1 = SmbUtils.getInstance(remoteUrl);
		smb.uploadFile(updateZipFile, updateZipUrl);
		//SmbUtils smb2 = SmbUtils.getInstance(remoteUrl);
		smb.uploadFile(plistFile, plistUrl);*/
		String removeDir="\\\\DESKTOP-FU92EA3\\test";

		//PingUtils.ping("192.168.7.178");

		File dd = new File("/Volumes/test/test.txt");

		//File dd = new File("\\\\192.168.7.178\\test\\");
		//System.out.println(dd.listFiles());

		System.out.println(dd.getName());
		File[] lists = dd.listFiles();
		for(File file1:lists){
			System.out.println(file1.getName());
		}



}



	/**
	 * 方法说明：上传文件到远程共享目录
	 * @param localDir         本地临时路径(A:/测试/测试.xls)
	 * @param remoteDir        远程共享路径(smb://10.169.2.xx/测试/,特殊路径只能用/)
	 * @param remoteIp         远程共享目录IP(10.169.2.xx)
	 * @param remoteLoginUser  远程共享目录用户名(user)
	 * @param remoteLoginPass  远程共享目录密码(password)
	 * @return
	 * @throws Exception   0成功/-1失败
	 */
	public static int smbUploading(String localDir, String remoteDir,
								   String remoteIp, String remoteLoginUser, String remoteLoginPass) throws Exception {
		NtlmPasswordAuthentication auth = null;
		OutputStream out = null;
		int retVal = 0;
		try {
			File dir = new File(localDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			if(StringUtils.isNotEmpty(remoteLoginUser)){
				InetAddress ip = InetAddress.getByName(remoteIp);
				UniAddress address = new UniAddress(ip);
				// 权限验证
				auth = new NtlmPasswordAuthentication(remoteIp, remoteLoginUser, remoteLoginPass);
				SmbSession.logon(address,auth);
			}

			//远程路径判断文件文件路径是否合法
			SmbFile remoteFile = null ;
			if(auth == null){
				remoteFile = new SmbFile(remoteDir + dir.getName());
			}else {
				remoteFile = new SmbFile(remoteDir + dir.getName(), auth);
			}
			remoteFile.connect();
			if (!remoteFile.exists()) {
				remoteFile.createNewFile();
			}
			if(remoteFile.isDirectory()){
				retVal = -1;
			}

			// 向远程共享目录写入文件
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
			out.write(toByteArray(dir));

		} catch (UnknownHostException e) {
			retVal = -1;
			e.printStackTrace();
		} catch (MalformedURLException e) {
			retVal = -1;
			e.printStackTrace();
		} catch (SmbException e) {
			retVal = -1;
			e.printStackTrace();
		} catch (IOException e) {
			retVal = -1;
			e.printStackTrace();
		} finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return retVal;
	}
	/**
	 * Mapped File way MappedByteBuffer  improve performance when working with large files
	 *
	 * @param file 文件
	 * @return   字节数组
	 * @throws IOException IO异常信息
	 */
	@SuppressWarnings("resource")
	public static byte[] toByteArray(File file) throws IOException {
		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(file, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
					fc.size()).load();
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
