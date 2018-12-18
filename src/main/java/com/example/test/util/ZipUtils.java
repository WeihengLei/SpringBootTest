package com.example.test.util;


import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private ZipUtils(){
	}

	public static void doCompress(String srcFile, String zipFile) throws IOException {
		doCompress(new File(srcFile), new File(zipFile));
	}

	/**
	 * 文件压缩
	 * @param srcFile 目录或者单个文件
	 * @param zipFile 压缩后的ZIP文件
	 */
	public static void doCompress(File srcFile, File zipFile) throws IOException {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			doCompress(srcFile, out);
		} catch (Exception e) {
			throw e;
		} finally {
			out.close();//记得关闭资源
		}
	}

	public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
		doCompress(new File(filelName), out);
	}

	public static void doCompress(File file, ZipOutputStream out) throws IOException{
		doCompress(file, out, "");
	}

	public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
		if ( inFile.isDirectory() ) {
			File[] files = inFile.listFiles();
			if (files!=null && files.length>0) {
				for (File file : files) {
					String name = inFile.getName();
					if (!"".equals(dir)) {
						name = dir + "/" + name;
					}
					ZipUtils.doCompress(file, out, name);
				}
			}
		} else {
			ZipUtils.doZip(inFile, out, dir);
		}
	}

	public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
		String entryName = null;
		if (!"".equals(dir)) {
			entryName = dir + "/" + inFile.getName();
		} else {
			entryName = inFile.getName();
		}
		ZipEntry entry = new ZipEntry(entryName);
		out.putNextEntry(entry);

		int len = 0 ;
		byte[] buffer = new byte[1024];
		FileInputStream fis = new FileInputStream(inFile);
		while ((len = fis.read(buffer)) > 0) {
			out.write(buffer, 0, len);
			out.flush();
		}
		out.closeEntry();
		fis.close();
	}


	public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName, List<Object> updateFileList, boolean isUpdateAll){
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		File dirFile = new File(zipFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if(sourceFile.exists() == false){
			System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
			sourceFile.mkdir(); // 新建目录
		}
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		try {

			File zipFile = new File(zipFilePath + "/" + fileName);

			if(zipFile.exists()){
				zipFile.delete();
			}
			File[] sourceFiles = sourceFile.listFiles();
			if(null == sourceFiles || sourceFiles.length<1){
				System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
			}else{
				fos = new FileOutputStream(zipFile);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				byte[] bufs = new byte[1024*10];
				for(int i=0;i<sourceFiles.length;i++){
					//创建ZIP实体，并添加进压缩包
					//System.out.println("1======"+sourceFiles[i].getName());

					if(isUpdateAll){
						zipEntry(fis ,bis ,fos,zos ,bufs, sourceFiles[i]);
					}else{
						if(updateFileList != null && updateFileList.size() > 0){
							zipEntry(fis ,bis ,fos,zos ,bufs, sourceFiles[i]);

						}else{
							if("ftlife.plist".equals(sourceFiles[i].getName())){
								zipEntry(fis ,bis ,fos,zos ,bufs, sourceFiles[i]);
							}
						}
					}
				}
				flag = true;
			//}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			//关闭流
			try {
				if(null != bis) bis.close();

				if(null != zos) zos.close();
				if(null != fos) fos.close();

			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return flag;
	}


	public static void zipEntry(FileInputStream fis ,
			BufferedInputStream bis ,
			FileOutputStream fos,
			ZipOutputStream zos ,byte[] bufs, File sourceFiles) throws IOException {

		ZipEntry zipEntry = new ZipEntry(sourceFiles.getName());
		zos.putNextEntry(zipEntry);
		//读取待压缩的文件并写进压缩包里
		fis = new FileInputStream(sourceFiles);
		bis = new BufferedInputStream(fis, 1024*10);
		int read = 0;
		while((read=bis.read(bufs, 0, 1024*10)) != -1){
			zos.write(bufs,0,read);
		}
	}

}