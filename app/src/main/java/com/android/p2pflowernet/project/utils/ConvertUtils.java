package com.android.p2pflowernet.project.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.p2pflowernet.project.base.BaseApplication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 名称：ConvertUtils.java
 * 描述：数据类型转换、单位转换
 * 创建人：zhangpeisen
 * 创建时间：2016/12/9 11:46
 */
public class ConvertUtils {
    /**
     * The constant GB.
     */
    public static final long GB = 1073741824;
    /**
     * The constant MB.
     */
    public static final long MB = 1048576;
    /**
     * The constant KB.
     */
    public static final long KB = 1024;

    /**
     * To int int.
     *
     * @param obj the obj
     * @return the int
     */
    public static int toInt(Object obj) {
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * To int int.
     *
     * @param bytes the bytes
     * @return the int
     */
    public static int toInt(byte[] bytes) {
        int result = 0;
        byte abyte;
        for (int i = 0; i < bytes.length; i++) {
            abyte = bytes[i];
            result += (abyte & 0xFF) << (8 * i);
        }
        return result;
    }

    /**
     * To short int.
     *
     * @param first  the first
     * @param second the second
     * @return the int
     */
    public static int toShort(byte first, byte second) {
        return (first << 8) + (second & 0xFF);
    }

    /**
     * To long long.
     *
     * @param obj the obj
     * @return the long
     */
    public static long toLong(Object obj) {
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return -1L;
        }
    }

    /**
     * To float float.
     *
     * @param obj the obj
     * @return the float
     */
    public static float toFloat(Object obj) {
        try {
            return Float.parseFloat(obj.toString());
        } catch (Exception e) {
            return -1f;
        }
    }

    /**
     * int占4字节
     *
     * @param i the
     * @return byte [ ]
     */
    public static byte[] toByteArray(int i) {
        // byte[] bytes = new byte[4];
        // bytes[0] = (byte) (0xff & i);
        // bytes[1] = (byte) ((0xff00 & i) >> 8);
        // bytes[2] = (byte) ((0xff0000 & i) >> 16);
        // bytes[3] = (byte) ((0xff000000 & i) >> 24);
        // return_ticket bytes;
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    /**
     * To byte array byte [ ].
     *
     * @param hexData the hex data
     * @param isHex   the is hex
     * @return the byte [ ]
     */
    public static byte[] toByteArray(String hexData, boolean isHex) {
        if (hexData == null || hexData.equals("")) {
            return null;
        }
        if (!isHex) {
            return hexData.getBytes();
        }
        hexData = hexData.replaceAll("\\s+", "");
        String hexDigits = "0123456789ABCDEF";
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                hexData.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < hexData.length(); i += 2) {
            baos.write((hexDigits.indexOf(hexData.charAt(i)) << 4 | hexDigits
                    .indexOf(hexData.charAt(i + 1))));
        }
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * To hex string string.
     *
     * @param str the str
     * @return the string
     */
    public static String toHexString(String str) {
        if (TextUtils.isEmpty(str))
            return "";
        StringBuilder buffer = new StringBuilder();
        byte[] bytes = str.getBytes();
        for (byte aByte : bytes) {
            buffer.append(Integer.toHexString(Integer.valueOf(0xFF & aByte).intValue()));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    /**
     * To hex string string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String toHexString(byte... bytes) {
        char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // 参见：http://www.oschina.net/code/snippet_116768_9019
        char[] buffer = new char[bytes.length * 2];
        for (int i = 0, j = 0; i < bytes.length; ++i) {
            int u = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];//转无符号整型
            buffer[j++] = DIGITS[u >>> 4];
            buffer[j++] = DIGITS[u & 0xf];
        }
        return new String(buffer);
    }

    /**
     * To hex string string.
     *
     * @param num the num
     * @return the string
     */
    public static String toHexString(int num) {
        String hexString = Integer.toHexString(num);
        LogUtils.e("",String.format("%d to hex string is %s", num, hexString));
        return hexString;
    }

    /**
     * To binary string string.
     *
     * @param bytes the bytes
     * @return the string
     */
    public static String toBinaryString(byte... bytes) {
        char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
                '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        // 参见：http://www.oschina.net/code/snippet_116768_9019
        char[] buffer = new char[bytes.length * 8];
        for (int i = 0, j = 0; i < bytes.length; ++i) {
            int u = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];//转无符号整型
            buffer[j++] = DIGITS[(u >>> 7) & 0x1];
            buffer[j++] = DIGITS[(u >>> 6) & 0x1];
            buffer[j++] = DIGITS[(u >>> 5) & 0x1];
            buffer[j++] = DIGITS[(u >>> 4) & 0x1];
            buffer[j++] = DIGITS[(u >>> 3) & 0x1];
            buffer[j++] = DIGITS[(u >>> 2) & 0x1];
            buffer[j++] = DIGITS[(u >>> 1) & 0x1];
            buffer[j++] = DIGITS[u & 0x1];
        }
        return new String(buffer);
    }

    /**
     * To binary string string.
     *
     * @param num the num
     * @return the string
     */
    public static String toBinaryString(int num) {
        String binaryString = Integer.toBinaryString(num);
        LogUtils.e("",String.format("%d to binary string is %s", num, binaryString));
        return binaryString;
    }

    /**
     * 转换为6位十六进制颜色代码，不含“#”
     *
     * @param color the color
     * @return string string
     */
    public static String toColorString(int color) {
        return toColorString(color, false);
    }

    /**
     * 转换为6位十六进制颜色代码，不含“#”
     *
     * @param color        the color
     * @param includeAlpha the include alpha
     * @return string string
     */
    public static String toColorString(int color, boolean includeAlpha) {
        String alpha = Integer.toHexString(Color.alpha(color));
        String red = Integer.toHexString(Color.red(color));
        String green = Integer.toHexString(Color.green(color));
        String blue = Integer.toHexString(Color.blue(color));
        if (alpha.length() == 1) {
            alpha = "0" + alpha;
        }
        if (red.length() == 1) {
            red = "0" + red;
        }
        if (green.length() == 1) {
            green = "0" + green;
        }
        if (blue.length() == 1) {
            blue = "0" + blue;
        }
        String colorString;
        if (includeAlpha) {
            colorString = alpha + red + green + blue;
            LogUtils.e("",String.format("%d to color string is %s", color, colorString));
        } else {
            colorString = red + green + blue;
            LogUtils.e("",String.format("%d to color string is %s%s%s%s, exclude alpha is %s", color, alpha, red, green, blue, colorString));
        }
        return colorString;
    }

    /**
     * 将指定的日期转换为一定格式的字符串
     *
     * @param date   the date
     * @param format the format
     * @return string string
     */
    public static String toDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 将当前的日期转换为一定格式的字符串
     *
     * @param format the format
     * @return string string
     */
    public static String toDateString(String format) {
        return toDateString(Calendar.getInstance(Locale.CHINA).getTime(), format);
    }

    /**
     * 将指定的日期字符串转换为日期时间
     *
     * @param dateStr 如：2014-04-08 23:02
     * @return date date
     */
    public static Date toDate(String dateStr) {
        return DateUtils.parseDate(dateStr);
    }

    /**
     * 将指定的日期字符串转换为时间戳
     *
     * @param dateStr 如：2014-04-08 23:02
     * @return long long
     */
    public static long toTimemillis(String dateStr) {
        return toDate(dateStr).getTime();
    }

    /**
     * To slash string string.
     *
     * @param str the str
     * @return the string
     */
    public static String toSlashString(String str) {
        String result = "";
        char[] chars = str.toCharArray();
        for (char chr : chars) {
            if (chr == '"' || chr == '\'' || chr == '\\') {
                result += "\\";//符合"、'、\这三个符号的前面加一个\
            }
            result += chr;
        }
        return result;
    }

    /**
     * To array t [ ].
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the t [ ]
     */
    public static <T> T[] toArray(List<T> list) {
        //noinspection unchecked
        return (T[]) list.toArray();
    }

    /**
     * To list list.
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return the list
     */
    public static <T> List<T> toList(T[] array) {
        return Arrays.asList(array);
    }

    /**
     * To string string.
     *
     * @param objects the objects
     * @return the string
     */
    public static String toString(Object[] objects) {
        return Arrays.deepToString(objects);
    }

    /**
     * To string string.
     *
     * @param objects the objects
     * @param tag     the tag
     * @return the string
     */
    public static String toString(Object[] objects, String tag) {
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object);
            sb.append(tag);
        }
        return sb.toString();
    }

    /**
     * To byte array byte [ ].
     *
     * @param is the is
     * @return the byte [ ]
     */
    public static byte[] toByteArray(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                os.write(buff, 0, rc);
            }
            byte[] bytes = os.toByteArray();
            os.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * To byte array byte [ ].
     *
     * @param bitmap the bitmap
     * @return the byte [ ]
     */
    public static byte[] toByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储，除了PNG还有很多常见格式，如jpeg等。
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] bytes = os.toByteArray();
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * To bitmap bitmap.
     *
     * @param bytes  the bytes
     * @param width  the width
     * @param height the height
     * @return the bitmap
     */
    public static Bitmap toBitmap(byte[] bytes, int width, int height) {
        Bitmap bitmap = null;
        if (bytes.length != 0) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                // 不进行图片抖动处理
                options.inDither = false;
                // 设置让解码器以最佳方式解码
                options.inPreferredConfig = null;
                if (width > 0 && height > 0) {
                    options.outWidth = width;
                    options.outHeight = height;
                }
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            } catch (Exception e) {
                LogUtils.e("",""+e);
            }
        }
        return bitmap;
    }

    /**
     * To bitmap bitmap.
     *
     * @param bytes the bytes
     * @return the bitmap
     */
    public static Bitmap toBitmap(byte[] bytes) {
        return toBitmap(bytes, -1, -1);
    }

    /**
     * convert Drawable to Bitmap
     * 参考：http://kylines.iteye.com/blog/1660184
     *
     * @param drawable the drawable
     * @return bitmap bitmap
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            //color
            Bitmap bitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
            if (Build.VERSION.SDK_INT >= 11) {
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(((ColorDrawable) drawable).getColor());
            }
            return bitmap;
        } else if (drawable instanceof NinePatchDrawable) {
            //.9.png
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    /**
     * 从第三方文件选择器获取路径。
     * 参见：http://blog.csdn.net/zbjdsbj/article/details/42387551
     *
     * @param context the context
     * @param uri     the uri
     * @return string string
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String toPath(Context context, Uri uri) {
        LogUtils.e("","uri: " + uri.toString());
        String path = uri.getPath();
        String scheme = uri.getScheme();
        String authority = uri.getAuthority();
        //是否是4.4及以上版本
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (authority.equals("com.android.externalstorage.documents")) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (authority.equals("com.android.providers.downloads.documents")) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return _queryPathFromMediaStore(context, contentUri, null, null);
            }
            // MediaProvider
            else if (authority.equals("com.android.providers.media.documents")) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return _queryPathFromMediaStore(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else {
            if ("content".equalsIgnoreCase(scheme)) {
                // Return the remote address
                if (authority.equals("com.google.android.apps.photos.content")) {
                    return uri.getLastPathSegment();
                }
                return _queryPathFromMediaStore(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(scheme)) {
                return uri.getPath();
            }
        }
        LogUtils.e("","uri to path: " + path);
        return path;
    }

    private static String _queryPathFromMediaStore(Context context, Uri uri, String selection, String[] selectionArgs) {
        String filePath = null;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        } catch (Exception e) {
            LogUtils.e("",""+e);
        }
        return filePath;
    }

    /**
     * convert View to Bitmap.
     *
     * @param view the view
     * @return the bitmap
     * @link http ://www.cnblogs.com/lee0oo0/p/3355468.html
     */
    public static Bitmap toBitmap(View view) {
        //以下代码用于把当前view转化为bitmap（截图）
        int width = view.getWidth();
        int height = view.getHeight();
        if (view instanceof ListView) {
            height = 0;
            // 获取listView实际高度
            ListView listView = (ListView) view;
            for (int i = 0; i < listView.getChildCount(); i++) {
                height += listView.getChildAt(i).getHeight();
            }
        } else if (view instanceof ScrollView) {
            height = 0;
            // 获取scrollView实际高度
            ScrollView scrollView = (ScrollView) view;
            for (int i = 0; i < scrollView.getChildCount(); i++) {
                height += scrollView.getChildAt(i).getHeight();
            }
        }
        view.setDrawingCacheEnabled(true);
        view.clearFocus();
        view.setPressed(false);
        boolean willNotCache = view.willNotCacheDrawing();
        view.setWillNotCacheDrawing(false);
        // Reset the drawing cache background color to fully transparent for the duration of this operation
        int color = view.getDrawingCacheBackgroundColor();
        view.setDrawingCacheBackgroundColor(Color.WHITE);//截图去黑色背景(透明像素)
        if (color != Color.WHITE) {
            view.destroyDrawingCache();
        }
        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        cacheBitmap.recycle();
        // Restore the view
        view.destroyDrawingCache();
        view.setWillNotCacheDrawing(willNotCache);
        view.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * convert Bitmap to Drawable
     *
     * @param bitmap the bitmap
     * @return drawable drawable
     */
    public static Drawable toDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(null, bitmap);
    }

    /**
     * convert Drawable to byte array
     *
     * @param drawable the drawable
     * @return byte [ ]
     */
    public static byte[] toByteArray(Drawable drawable) {
        return toByteArray(toBitmap(drawable));
    }

    /**
     * convert byte array to Drawable
     *
     * @param bytes the bytes
     * @return drawable drawable
     */
    public static Drawable toDrawable(byte[] bytes) {
        return toDrawable(toBitmap(bytes));
    }

    /**
     * dp转换为px
     *
     * @param context the context
     * @param dpValue the dp value
     * @return int int
     */
    public static int toPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * scale + 0.5f);
        LogUtils.e("",dpValue + " dp == " + pxValue + " px");
        return pxValue;
    }

    /**
     * To px int.
     *
     * @param dpValue the dp value
     * @return the int
     */
    public static int toPx(float dpValue) {
        Resources resources = Resources.getSystem();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.getDisplayMetrics());
        return (int) px;
    }

    /**
     * px转换为dp
     *
     * @param context the context
     * @param pxValue the px value
     * @return int int
     */
    public static int toDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int dpValue = (int) (pxValue / scale + 0.5f);
        LogUtils.e("",pxValue + " px == " + dpValue + " dp");
        return dpValue;
    }

    /**
     * px转换为sp
     *
     * @param context the context
     * @param pxValue the px value
     * @return int int
     */
    public static int toSp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        int spValue = (int) (pxValue / fontScale + 0.5f);
        LogUtils.e("",pxValue + " px == " + spValue + " sp");
        return spValue;
    }

    /**
     * To gbk string.
     *
     * @param str the str
     * @return the string
     */
    public static String toGbk(String str) {
        try {
            return new String(str.getBytes("utf-8"), "gbk");
        } catch (UnsupportedEncodingException e) {
            LogUtils.e("",e.getMessage());
            return str;
        }
    }

    /**
     * To file size string string.
     *
     * @param fileSize the file size
     * @return the string
     */
    public static String toFileSizeString(long fileSize) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString;
        if (fileSize < KB) {
            fileSizeString = fileSize + "B";
        } else if (fileSize < MB) {
            fileSizeString = df.format((double) fileSize / KB) + "K";
        } else if (fileSize < GB) {
            fileSizeString = df.format((double) fileSize / MB) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / GB) + "G";
        }
        return fileSizeString;
    }

    /**
     * To string string.
     *
     * @param is the is
     * @return the string
     */
    public static String toString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (IOException e) {
            LogUtils.e("",e.getMessage());
        }
        return sb.toString();
    }

    /**
     * To round drawable shape drawable.
     *
     * @param color  the color
     * @param radius the radius
     * @return the shape drawable
     */
    public static ShapeDrawable toRoundDrawable(int color, int radius) {
        int r = toPx(radius);
        float[] outerR = new float[]{r, r, r, r, r, r, r, r};
        RoundRectShape shape = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(color);
        return drawable;
    }

    /**
     * 对TextView、Button等设置不同状态时其文字颜色。
     * 参见：http://blog.csdn.net/sodino/article/details/6797821
     * Modified by liyujiang at 2015.08.13
     *
     * @param normalColor  the normal color
     * @param pressedColor the pressed color
     * @param focusedColor the focused color
     * @param unableColor  the unable color
     * @return the color state list
     */
    public static ColorStateList toColorStateList(int normalColor, int pressedColor, int focusedColor, int unableColor) {
        int[] colors = new int[]{pressedColor, focusedColor, normalColor, focusedColor, unableColor, normalColor};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        return new ColorStateList(states, colors);
    }

    /**
     * To color state list color state list.
     *
     * @param normalColor  the normal color
     * @param pressedColor the pressed color
     * @return the color state list
     */
    public static ColorStateList toColorStateList(int normalColor, int pressedColor) {
        return toColorStateList(normalColor, pressedColor, pressedColor, normalColor);
    }

    /**
     * To state list drawable state list drawable.
     *
     * @param normal  the normal
     * @param pressed the pressed
     * @param focused the focused
     * @param unable  the unable
     * @return the state list drawable
     */
    public static StateListDrawable toStateListDrawable(Drawable normal, Drawable pressed, Drawable focused, Drawable unable) {
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        drawable.addState(new int[]{android.R.attr.state_enabled}, normal);
        drawable.addState(new int[]{android.R.attr.state_focused}, focused);
        drawable.addState(new int[]{android.R.attr.state_window_focused}, unable);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    /**
     * To state list drawable state list drawable.
     *
     * @param normalColor  the normal color
     * @param pressedColor the pressed color
     * @param focusedColor the focused color
     * @param unableColor  the unable color
     * @return the state list drawable
     */
    public static StateListDrawable toStateListDrawable(int normalColor, int pressedColor, int focusedColor, int unableColor) {
        StateListDrawable drawable = new StateListDrawable();
        Drawable normal = new ColorDrawable(normalColor);
        Drawable pressed = new ColorDrawable(pressedColor);
        Drawable focused = new ColorDrawable(focusedColor);
        Drawable unable = new ColorDrawable(unableColor);
        drawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        drawable.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        drawable.addState(new int[]{android.R.attr.state_enabled}, normal);
        drawable.addState(new int[]{android.R.attr.state_focused}, focused);
        drawable.addState(new int[]{android.R.attr.state_window_focused}, unable);
        drawable.addState(new int[]{}, normal);
        return drawable;
    }

    /**
     * To state list drawable state list drawable.
     *
     * @param normal  the normal
     * @param pressed the pressed
     * @return the state list drawable
     */
    public static StateListDrawable toStateListDrawable(Drawable normal, Drawable pressed) {
        return toStateListDrawable(normal, pressed, pressed, normal);
    }

    /**
     * To state list drawable state list drawable.
     *
     * @param normalColor  the normal color
     * @param pressedColor the pressed color
     * @return the state list drawable
     */
    public static StateListDrawable toStateListDrawable(int normalColor, int pressedColor) {
        return toStateListDrawable(normalColor, pressedColor, pressedColor, normalColor);
    }

    /**
     * 内容替换
     *
     * @param str            原文，需要被操作的内容
     * @param replaceContent 需要被替换的内容
     * @param targetContent  替换内容
     * @return
     */
    public static String mReplaceBlank(String str, String replaceContent, String targetContent) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile(replaceContent);
            Matcher m = p.matcher(str);
            dest = m.replaceAll(targetContent);

        }
        return dest;
    }

    /*
     * 字符串半角转为全角
     */
    public static String trimStringTDC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    // 替换、过滤特殊字符
    public static String StringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]").replaceAll("！", "!");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String[] chineseDigits = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    public static String amountToChinese(double amount) {
        if (amount > 999999999999999999.99 || amount < -999999999999999999.99)
            throw new IllegalArgumentException("参数值超出允许范围 (-999999999999999999.99 ～ 999999999999999999.99)！");
        boolean negative = false;
        if (amount < 0) {
            negative = true;
            amount = amount * (-1);
        }
        long temp = Math.round(amount * 100);
        int numFen = (int) (temp % 10); // 分
        temp = temp / 10;
        int numJiao = (int) (temp % 10); // 角
        temp = temp / 10; // temp 目前是金额的整数部分
        int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
        int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
        for (int i = 0; ; i++) {
            if (temp == 0)
                break;
            int part = (int) (temp % 10000);
            parts[i] = part;
            numParts++;
            temp = temp / 10000;
        }
        boolean beforeWanIsZero = true; // 标志“万”下面一级是不是 0
        boolean beforeYiIsZero = true;//标志“亿”下面一级是不是 0
        String chineseStr = "";
        for (int i = 0; i < numParts; i++) {
            String partChinese = partTranslate(parts[i]);
            if (i % 2 == 0) {
                beforeWanIsZero = "".equals(partChinese);
            }
            if (i == 1) {
                beforeYiIsZero = "".equals(partChinese);
            }
            if (i != 0) {
                if (i % 2 == 0) {
                    if ("".equals(partChinese) && !beforeYiIsZero) {//万之前为0，万之后不为0则加零
                        chineseStr = "零" + chineseStr;
                    } else if (!"".equals(partChinese) && parts[i - 1] < 1000 && parts[i - 1] > 0) {//万之前不为0，万之后不到1000，则
                        //加万和零
                        chineseStr = "零" + chineseStr;
                        chineseStr = "亿" + chineseStr;
                    } else if ("".equals(partChinese) && beforeYiIsZero) {//如果都为0,说明是整数亿，则什么都不加
                    } else if (!"".equals(partChinese) && beforeYiIsZero) {
                        chineseStr = "亿" + chineseStr;
                    } else {
                        chineseStr = "亿" + chineseStr;
                    }
                } else {
                    if ("".equals(partChinese) && !beforeWanIsZero) {//万之前为0，万之后不为0则加零
                        chineseStr = "零" + chineseStr;
                    } else if (!"".equals(partChinese) && parts[i - 1] < 1000 && parts[i - 1] > 0) {//万之前不为0，万之后不到1000，则
                        //加万和零
                        chineseStr = "零" + chineseStr;
                        chineseStr = "万" + chineseStr;
                    } else if ("".equals(partChinese) && beforeWanIsZero) {//如果都为0,说明是整数亿，则什么都不加
                    } else if (!"".equals(partChinese) && beforeWanIsZero) {
                        chineseStr = "万" + chineseStr;
                    } else {
                        chineseStr = "万" + chineseStr;
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }
        if ("".equals(chineseStr)) // 整数部分为 0, 则表达为"零元"
            chineseStr = chineseDigits[0];
        else if (negative) // 整数部分不为 0, 并且原金额为负数
            chineseStr = "负" + chineseStr;
        chineseStr = chineseStr + "元";
        if (numFen == 0 && numJiao == 0) {
            chineseStr = chineseStr + "整";
        } else if (numFen == 0) { // 0 分，角数不为 0
            chineseStr = chineseStr + chineseDigits[numJiao] + "角";
        } else { // “分”数不为 0
            if (numJiao == 0)
                chineseStr = chineseStr + "零" + chineseDigits[numFen] + "分";
            else
                chineseStr = chineseStr + chineseDigits[numJiao] + "角" + chineseDigits[numFen] + "分";
        }
        return chineseStr;
    }

    /**
     * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
     *
     * @param amountPart
     * @return
     */
    private static String partTranslate(int amountPart) {
        if (amountPart < 0 || amountPart > 10000) {
            throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
        }
        String[] units = new String[]{"", "拾", "佰", "仟"};
        int temp = amountPart;
        String amountStr = new Integer(amountPart).toString();
        int amountStrLength = amountStr.length();
        boolean lastIsZero = true; // 在从低位往高位循环时，记录上一位数字是不是 0
        String chineseStr = "";
        for (int i = 0; i < amountStrLength; i++) {
            if (temp == 0) // 高位已无数据
                break;
            int digit = temp % 10;
            if (digit == 0) { // 取到的数字为 0
                if (!lastIsZero) // 前一个数字不是 0，则在当前汉字串前加“零”字;
                    chineseStr = "零" + chineseStr;
                lastIsZero = true;
            } else { // 取到的数字不是 0
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }

    public static String getTo4Division(String str) {
        StringBuilder sb = new StringBuilder(str);
        int i = str.length() / 4;
        if (str.length() % 4 == 0) {
            i--;
        }
        for (int j = 1; j <= i; j++) {
            sb.insert(j * 4 + j - 1, " ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 格式化金额
     *
     * @param money
     * @return
     */
    @SuppressLint("NewApi")
    public static String fmtMoney(String money) {
        if (money == null || "".equals(money)) {
            return "";
        }

        double dobleValue = Double.parseDouble(money);
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        fmt.setRoundingMode(RoundingMode.DOWN);
        String fmtMoney = fmt.format(dobleValue);
        return fmtMoney;
    }


    /**
     * Description: 格式化银行卡号
     *
     * @param mEditText
     * @param carSub_tv
     * @Version1.0 2015-6-6 下午3:56:53 by xupeng (xupeng@csii.com.cn)
     */
    public static void bankCardNumAddSpace(final EditText mEditText, final TextView carSub_tv) {
        mEditText.addTextChangedListener(new TextWatcher() {

            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (carSub_tv != null) {
                    carSub_tv.setText(mEditText.getText());
                }
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19 || index == 24 || index == 29)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    if (str.length() >= 23) {
                        str = str.substring(0, 23);
                        location = str.length();
                    }

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * Description: 解析银行卡
     *
     * @param cardNo
     * @return
     * @Version1.0 2015-6-6 下午4:00:24 by xupeng (xupeng@csii.com.cn)
     */
    public static String parseCardNo(String cardNo) {
        return cardNo.replaceAll(" ", "");
    }

    /**
     * 格式化卡号，每隔4位加空格
     *
     * @param cardNo 银行卡号
     * @return 格式化后的银行卡号
     */
    public static String fmtCardNo(String cardNo) {
        if (TextUtils.isEmpty(cardNo))
            return null;
        StringBuffer fmtCardNo = new StringBuffer();
        char[] charArray = cardNo.toCharArray();
        for (int i = 0; i < cardNo.length(); i++) {
            if (0 != i && (i % 4 == 0)) {
                fmtCardNo.append(" ");
            }
            fmtCardNo.append(charArray[i]);
        }
        return fmtCardNo.toString();
    }

    /**
     * 格式化卡号，只有前四位和最后4位显示数字，其他位显示空格和**
     *
     * @param cardNo 银行卡号
     * @return 格式化后的银行卡号
     */
    public static String fmtCardNoByStar(String cardNo) {
        StringBuffer fmtCardNo = new StringBuffer();
        if (TextUtils.isEmpty(cardNo)) {
            return "";
        } else {
            char[] charArray = cardNo.toCharArray();
            for (int i = 0; i < cardNo.length() - 4; i++) {
                if (0 != i && (i % 4 == 0)) {
                    fmtCardNo.append(" ");
                }
                if (i > 3) {
                    fmtCardNo.append("*");
                } else {
                    fmtCardNo.append(charArray[i]);
                }
            }
            fmtCardNo.append(" ");
            for (int j = cardNo.length() - 4; j < cardNo.length(); j++) {
                fmtCardNo.append(charArray[j]);
            }
            return fmtCardNo.toString();
        }
    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurtDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * @param str      需要格式化的值
     * @param decimals 保留到小数点后几位
     * @param is       扩展
     * @return
     */
    @SuppressLint("UseValueOf")
    public static String formatAmount(String str, int decimals, boolean is) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String patt = "";
        for (int i = 0; i < decimals; i++) {
            patt = patt + "0";
        }
        if (!TextUtils.isEmpty(patt)) {
            patt = "." + patt;
        }
        Double doubl = new Double(str);
        if (doubl == 0) {
            return "0" + patt;
        }
        DecimalFormat myformat = new DecimalFormat();
        // myformat.applyPattern("##,###"+patt);
        myformat.applyPattern("##,##0" + patt);
        return myformat.format(doubl);
    }

    /**
     * 动态格式化金额（录入金额时将金额格式化为#,##0.00格式）
     *
     * @param etMoney 金额输入框对象
     */
    public static void dynamicFmtMoney(final EditText etMoney) {
        etMoney.addTextChangedListener(new TextWatcher() {

            // 是否是后台自动改变值（代码自动格式化后重新设置的值非用户输入，避免死循环）
            boolean isAutoChange = false;
            // 后台自动改变值之前的值
            String beforeAutoChangeValue;

            // 用户光标位置
            int userCursorIndex;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 若是后台自动改变值则重置是否后台自动改变值标识为false、设置最新光标位置
                if (isAutoChange) {
                    isAutoChange = false;
                    // 设置改变值后的光标位置
                    Editable etable = etMoney.getText();
                    // 设置值改变后对应用户光标位置，改变之前值长度为1，格式后只会往后面加”.00“无需改变用户光标位置，否则设置最新光标位置为【用户原始光标位置+(改变值后的长度-改变之前的长度）】
                    int cursorIndex = userCursorIndex;
                    if (1 < beforeAutoChangeValue.length()) {
                        // 后台自动改变值后的长度
                        int afterAutoChangeLength = s.toString().length();
                        cursorIndex = userCursorIndex + (afterAutoChangeLength - beforeAutoChangeValue.length());
                    }
                    Selection.setSelection(etable, cursorIndex);
                }
                // 否则格式化用户录入金额
                else {
                    try {
                        // 记录用户输入值
                        beforeAutoChangeValue = s.toString();
                        // 记录用户光标位置
                        userCursorIndex = etMoney.getSelectionEnd();
                        // 获取用户输入金额并解析金额（将金额中的逗号“,”剔除）
                        String money = parseMoney(beforeAutoChangeValue);
                        // 若当前输入的是点则不做格式化
                        if (beforeAutoChangeValue.length() == 1
                                && beforeAutoChangeValue.substring(0, 1).equals(".")) {
                            etMoney.setText("0.");
                            Selection.setSelection(etMoney.getText(), 2);
                            return;
                        }
                        if (beforeAutoChangeValue.substring(start, beforeAutoChangeValue.length()).equals(".")) {
                            return;
                        }
                        // 若当前输入的是点则不做格式化
                        if (beforeAutoChangeValue.substring(start, beforeAutoChangeValue.length()).equals("0") && start >= 1
                                && beforeAutoChangeValue.substring(start - 1, start).equals(".")) {
                            return;
                        }
                        if (beforeAutoChangeValue.length() >= 3
                                && beforeAutoChangeValue.indexOf(".") != -1
                                && beforeAutoChangeValue.substring(beforeAutoChangeValue.indexOf(".") + 2
                                , beforeAutoChangeValue.indexOf(".") + 3).equals("0")
                                ) {
                            if (money.contains(".")) {
                                money = money.substring(0, money.indexOf(".") + 3);
                            }
                            double dobleValue = Double.parseDouble(money);
                            DecimalFormat fmt = new DecimalFormat("#,##0.00");
                            String fmtMoney = fmt.format(dobleValue);
                            // 设置自动改变值标识为true
                            //                        String fmtMoney = fmtMoney(money);
                            isAutoChange = true;
                            // 自动改变金额为格式化后的金额
                            etMoney.setText(fmtMoney);
                            return;
                        }
                        // 格式化金额
                        if (money.contains(".")) {
                            money = money.substring(0, money.indexOf(".") + 3);
                        }
                        final double doubleValue;
                        if (TextUtils.isEmpty(money)) {
                            return;
                        } else {
                            doubleValue = Double.parseDouble(money);
                        }
                        DecimalFormat fmt = new DecimalFormat("#,###.##");
                        String fmtMoney = fmt.format(doubleValue);
                        // 设置自动改变值标识为true
//                        String fmtMoney = fmtMoney(money);
                        isAutoChange = true;
                        // 自动改变金额为格式化后的金额
                        etMoney.setText(fmtMoney);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 解析金额（将金额中的逗号“,”剔除）
     *
     * @param money 要格式化的金额
     * @return 格式化后的金额
     */
    public static String parseMoney(String money) {
        return money.replaceAll(",", "");
    }

    /**
     * 格式化手机号码（隐藏4~7位）
     *
     * @param s 需要格式化的号码
     * @return 格式化后的号码
     */
    public static String formatPhoneNum(String s) {
        if (!TextUtils.isEmpty(s)) {
            char[] data = s.toCharArray();
            for (int i = 3; i < 7; i++) {
                data[i] = '*';
            }
            return new String(data);
        } else {
            return "";
        }
    }

    /**
     * 格式化身份证号码（隐藏4~14位）
     *
     * @param s 需要格式化的号码
     * @return 格式化后的号码
     */
    public static String parseIDNo(String s) {
        char[] data = s.toCharArray();
        for (int i = 3; i < 14; i++) {
            data[i] = '*';
        }
        return new String(data);
    }

    /**
     * 获取字符串资源
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }
    /**
     * 获取Resource对象
     */
    public static Resources getResources() {
        return BaseApplication.getContext().getResources();
    }


    /**把数据源HashMap转换成json
     * @param map
     */
    public static String hashMapToJson(HashMap map) {
        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Map.Entry e = (Map.Entry) it.next();
            string += "'" + e.getKey() + "':";
            string += "'" + e.getValue() + "',";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";
        return string;
    }
}
