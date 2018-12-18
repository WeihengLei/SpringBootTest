package com.example.test.util;


import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

public class AntZipUtils {


	public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName, List<Object> updateFileList, boolean isUpdateAll){
		File sourceFile = new File(sourceFilePath);
		File dirFile = new File(zipFilePath);

		if(sourceFile.exists() == false){
			System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
			sourceFile.mkdir(); // 新建目录
		}
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		File zipFile = new File(zipFilePath + "/" + fileName);
		if(zipFile.exists()){
			zipFile.delete();
		}

		zip(sourceFile, zipFile,null, isUpdateAll);

		return true;
	}


		/**
         *
         * @param file 要压缩的文件
         * @param zipFile 压缩文件存放地方
         */
	public static void zip(File file, File zipFile, List<Object> updateFileList, boolean isUpdateAll) {
		ZipOutputStream outputStream = null;
		try {
			outputStream = new ZipOutputStream(new FileOutputStream(zipFile));
			zipFile(outputStream, file, "",updateFileList,isUpdateAll);
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();
			}
		} catch (IOException ex) {
			Logger.getLogger(AntZipUtils.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				outputStream.close();
			} catch (IOException ex) {
				Logger.getLogger(AntZipUtils.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 *
	 * @param output ZipOutputStream对象
	 * @param file 要压缩的文件或文件夹
	 * @param basePath 条目根目录
	 */
	private static void zipFile(ZipOutputStream output, File file, String basePath, List<Object> updateFileList, boolean isUpdateAll ) {
		FileInputStream input = null;
		try {
			// 文件为目录
			if (file.isDirectory()) {
				// 得到当前目录里面的文件列表
				File list[] = file.listFiles();
				// basePath = basePath + (basePath.length() == 0 ? "" : "/")+ file.getName();
				// 循环递归压缩每个文件
				for (File f : list) {

					if(isUpdateAll){
						zipFile(output, f, basePath, updateFileList, isUpdateAll);
					}else{
						if(updateFileList != null && updateFileList.size() > 0){
							for (Object fileLog :updateFileList) {
								//if(fileLog.getFileName().equals(f.getName()) || "ftlife.plist".equals(f.getName())){
									zipFile(output, f, basePath, updateFileList, isUpdateAll);
								//}
							}
						}else{
							if("ftlife.plist".equals(f.getName())){
								zipFile(output, f, basePath, updateFileList, isUpdateAll);
							}
						}
					}
					//zipFile(output, f, basePath);
				}
			} else {
				// 压缩文件
				basePath = (basePath.length() == 0 ? "" : basePath + "/")
						+ file.getName();
				// System.out.println(basePath);
				output.putNextEntry(new ZipEntry(basePath));
				input = new FileInputStream(file);
				int readLen = 0;
				byte[] buffer = new byte[1024 * 8];
				while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
					output.write(buffer, 0, readLen);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 关闭流
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {
					Logger.getLogger(AntZipUtils.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	/**
	 *
	 * @param sourceZip 待解压文件路径
	 * @param destDir 解压到的路径
	 */
	public static void unZip(String sourceZip, String destDir) {
		//保证文件夹路径最后是"/"或者"\"
		char lastChar = destDir.charAt(destDir.length() - 1);
		if (lastChar != '/' && lastChar != '\\') {
			destDir += File.separator;
		}
		Project p = new Project();
		Expand e = new Expand();
		e.setProject(p);
		e.setSrc(new File(sourceZip));
		e.setOverwrite(false);
		e.setDest(new File(destDir));
        /*
         ant下的zip工具默认压缩编码为UTF-8编码，
         而winRAR软件压缩是用的windows默认的GBK或者GB2312编码
         所以解压缩时要制定编码格式
         */
		e.setEncoding("gbk");
		e.execute();
	}

	public static void main(String[] args) {
		String sourcePath = "C:/model.zip";
		String destPath = "C:/test";
		unZip(sourcePath, destPath);
		//zip(new File("C:/test/model"), new File("d:/model.zip"));
	}

}