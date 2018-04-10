package com.android.p2pflowernet.project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by rxy on 17/7/21.
 */

public class ParamMatchUtils {

    public static boolean isPhoneAvailable(String phone) {
        String regex = "^1[3,4,5,7,8]{1}\\d{9}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isCodeAvailable(String code){
        String regex = "^\\d{6}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    //判断email格式是否正确

    public static boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();
    }
}
