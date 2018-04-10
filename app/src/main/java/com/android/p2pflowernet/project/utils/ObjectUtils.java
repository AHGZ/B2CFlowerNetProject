/****************************
文件名:ObjectUtils.java

创建时间:2013-3-27
所在包:
作者:罗泽锋
说明:对象操作模块通用工具类
 ****************************/

package com.android.p2pflowernet.project.utils;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectUtils {

	// 从文件中序列化读取数据对象
	public static Object loadObjectData(String path) {
		Object ret = null;
		if (path == null)
			return ret;

		File file = new File(path);
		if (!file.exists())
			return ret;

		try {
			FileInputStream fis = new FileInputStream(file);
			ret = loadObjectData(fis);
			fis.close();
		} catch (Exception e) {
		}
		return ret;
	}

	// 从文件输入流中读取序列化数据对象
	public static Object loadObjectData(InputStream inputStream) {
		Object ret = null;
		try {
			ObjectInputStream istream = null;
			istream = new ObjectInputStream(inputStream);
			ret = istream.readObject();
			istream.close();
		} catch (Exception e) {
		}
		return ret;
	}
	
	public static Object copyObject(Object obj){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream ostream = null;
			ostream = new ObjectOutputStream(baos);
			ostream.writeObject(obj);
			ostream.close();
			
			InputStream is = new ByteArrayInputStream(baos.toByteArray());
			Object ob = loadObjectData(is);
			if(ob!=null){
				return ob;
			}
		} catch (Exception e) {
		}
		return obj;
	}

	// 将序列化数据对象缓存到文件
	public static void saveObjectData(Object obj, String fileName) {
		if (TextUtils.isEmpty(fileName))
			return;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream ostream = null;
			ostream = new ObjectOutputStream(baos);
			ostream.writeObject(obj);
			ostream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		int size = 0;
		try {
			size = is.available();
		} catch (Exception e1) {
		}

		if (size > 0)
			FileUtils.saveAsFile(is, fileName);
		try {
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void Wait(Object waitObject) {
		if (waitObject == null)
			return;
		synchronized (waitObject) {
			try {
				waitObject.wait();
			} catch (InterruptedException e) {
			}
		}
	}
	public static void WaitForTime(Object waitObject,long millis) {
		if (waitObject == null)
			return;
		synchronized (waitObject) {
			try {
				waitObject.wait(millis);
			} catch (InterruptedException e) {
			}
		}
	}
	public static void NotifyAll(Object waitObject) {
		if (waitObject == null)
			return;
		synchronized (waitObject) {
			waitObject.notifyAll();
		}
	}

}
