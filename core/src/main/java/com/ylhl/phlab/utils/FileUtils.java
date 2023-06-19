package com.ylhl.phlab.utils;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class FileUtils {

	public static void createNewFile(String path,String fileName,String content){
		try {
			File file =new File(path,fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileCopyUtils.copy(content.getBytes(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void workbookToFile(Workbook wb){
		try {
			String path = System.getProperty("user.dir" )+"\\data\\下载";
			File f =new File(path, UUID.randomUUID() +".xlsx");
			if (!f.exists()) {
				f.getParentFile().mkdirs();
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f.getPath());
			wb.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



    public static void createModelPage(String name,String content){
		try {
			System.out.println(System.getProperty("user.dir" ));
			String path = System.getProperty("user.dir" )+"\\src\\main\\resources\\templates";
			File f =new File(path,name+".ftl");
			if (!f.exists()) {
				f.createNewFile();
			}
			if(content!=null) {
				FileCopyUtils.copy(content.getBytes(), f);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static void listFile(File file, List<String> list, String type) {
		File[] files = file.listFiles();
		if(files!=null){
			for (File f : files) {
				if (f.isDirectory()) {
					listFile(f,list,type);
					continue;
				}
				if(f.getPath().endsWith(type)) {
					list.add("data/image/"+f.getName());
				}
			}
		}

	}

}
