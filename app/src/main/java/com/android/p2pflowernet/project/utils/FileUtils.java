package com.android.p2pflowernet.project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.android.p2pflowernet.project.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String SDCardPathRoot = Environment.getExternalStorageDirectory() + File.separator;
    public static String InsidePath = BaseApplication.getContext().getFilesDir().getPath() + File.separator + "p2pflowernet";

    public static String ROOT = SDCardPathRoot + "p2pflowernet";
    public static String TEMP = SDCardPathRoot + "p2pflowernet" + File.separator + "temp" + File.separator;
    public static String CACHE = SDCardPathRoot + "p2pflowernet" + File.separator + "cache" + File.separator;

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/formats/";

    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

    // 写文件在./data/data/<包名>/files/下面
    public static boolean writeFileData(Context context, String fileName, String message) {
        try {
            FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("write file error: " + e);

            return false;
        }
    }

    public static File creatSDFile(String dir, String fileName) throws IOException {
        File file = new File(SDCardPathRoot + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    public static File creatSDDir(String dirName) {
        File dir = new File(SDCardPathRoot + dirName + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean isSDFileExist(String dir, String fileName) {
        File file = new File(SDCardPathRoot + dir + File.separator + fileName);
        return file.exists();
    }

    public static File write2SDFromInput(String dir, String fileName, InputStream inputStream) {
        File file = null;            // 声明文件对象
        OutputStream output = null; // 声明输出流对象
        try {
            creatSDDir(dir);        // 在SDCard中创建目录
            file = creatSDFile(dir, fileName); // 在SDCard中创建文件
            output = new FileOutputStream(file); // 将文件放入到输出流中
            int temp;
            byte buffer[] = new byte[4 * 1024];
            while ((temp = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static String read(Context context, String fileName) {
        String data = "";
        try {
            FileInputStream stream = context.openFileInput(fileName);
            StringBuffer sb = new StringBuffer();
            int c;
            while ((c = stream.read()) != -1) {
                sb.append((char) c);
            }
            stream.close();
            data = sb.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return data;
    }

    public static void write(Context context, String fileName, String msg) {
        try {
            FileOutputStream stream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            stream.write(msg.getBytes());
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public static void deleteTheFile(File file) {
        if (file.exists()) {
            if (file.isFile()) { // 判断是否是文件
                file.delete();
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteTheFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            System.out.println("文件不存在！" + "\n");
        }
    }

    public static void deleteTheFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            Log.d("", "yhj:deleteTheFile:1");
            file.delete();
        } else {
            Log.d("", "yhj:deleteTheFile:2");
            System.out.println("文件不存在！" + "\n");
        }
    }

    public static List<String> getFile(String path) {
        File file = new File(path);
        File[] childFiles = file.listFiles();    //得到所有子目录或文件，
        List<String> filename = new ArrayList<String>();
        for (File files : childFiles) {
            String myfilename = files.getName();//得到文件名称
            filename.add(myfilename);
        }
        return filename;
    }

    public static long getFileSizes(File f) throws Exception {
        long s = 0;
        if (f.exists()) {
            @SuppressWarnings("resource")
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    public static long getFileSize(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static File creatMemoryDir(String dirName) {
        File dir = new File(dirName + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean isMemoryFileExist(String dirName, String fileName) {
        File file = new File(dirName + File.separator + fileName);
        return file.exists();
    }

    public static File creatMemoryFile(String dir, String fileName) throws IOException {
        File file = new File(dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    public static File write2MemoryFromInput(String dir, String fileName, InputStream inputStream) {
        File file = null;            // 声明文件对象
        OutputStream output = null; // 声明输出流对象
        try {
            creatMemoryDir(dir);
            file = creatMemoryFile(dir, fileName);
            output = new FileOutputStream(file); // 将文件放入到输出流中
            int temp;
            byte buffer[] = new byte[4 * 1024];
            while ((temp = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void saveToSDCard(String filename, String content) throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        OutputStream out = new FileOutputStream(file);
        out.write(content.getBytes());
        out.close();
    }


    //输入流保存为文件
    public static void saveAsFile(InputStream inputStream, String fileName) {
        saveAsFile(inputStream, fileName, false);
    }

    // 输入流保存为文件
    public static void saveAsFile(InputStream inputStream, String fileName,
                                  boolean append) {
        try {
            if (TextUtils.isEmpty(fileName)) {
                return;
            }
            // make sure this file exist
            makesureFileExist(fileName);

            OutputStream os = new FileOutputStream(fileName, append);
            byte[] buf = new byte[255];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
            inputStream.close();
            os.flush();
            os.close();
        } catch (IOException e) {
        }
    }


    // 确定指定文件是否存在，如果不存在，则创建空文件
    public static void makesureFileExist(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        // file path
        int index = fileName.lastIndexOf("/");
        File file = null;
        if (index != -1) {
            String filePath = fileName.substring(0, index);
            file = new File(filePath);
            if (!file.exists()) {
                boolean ret = file.mkdirs();
            }
        }
        file = new File(fileName);
        if (!file.exists()) {// 确保文件存在
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
    }


    public static void makesureDirExist(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }
        int index = fileName.lastIndexOf("/");
        File file = null;
        if (index != -1) {
            String filePath = fileName.substring(0, index);
            file = new File(filePath);
            if (!file.exists()) {
                boolean ret = file.mkdirs();
            }
        }
    }


    // 数组保存为文件
    public static void saveAsFile(String data, String fileName) {
        saveAsFile(data, fileName, false);
    }

    // 数组保存为文件
    public static void saveAsFile(String data, String fileName, boolean append) {
        try {
            InputStream is = new ByteArrayInputStream(data.getBytes());
            FileUtils.saveAsFile(is, fileName, false);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 转换url地址为文件名，去掉http头和ip字段
    public static String convertFilenameFromUrl(String open_url, String param) {
        return convertFilenameFromUrl(open_url + "_" + param);
    }

    // 转换url地址为文件名，去掉http头和ip字段
    public static String convertFilenameFromUrl(String open_url) {
        String name = open_url;
        name = name.replace("http://", "");
        name = name.replace("https://", "");
        int pos = name.indexOf("/");
        if (pos > 0) {
            name = name.substring(pos);
        }
        name = name.replace("/", "_");
        name = name.replace("?", "_");
        name = name.replace("&", "_");
        name = name.replace("=", "_");
        return name;
    }

}
