package com.example.familiycookmenu.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

public class DeleteCache {

	public static File fileName;

	public static long length = 0;

	public static String getBitmapCache(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File fileName = context.getExternalCacheDir();
			if (!fileName.exists()) {
				return null;
			}
			fileToSearch(fileName);
			return Formatter.formatFileSize(context, length);
		}
		return null;
	}

//用了递归遍历的方式删除文件
	public static void fileToSearch(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				 if (files[i].isDirectory()) {
					 fileToSearch(files[i]);
				 } else {
					length = files[i].length() + length;
					files[i].delete();
				 }
			}
	   }
	}


}
