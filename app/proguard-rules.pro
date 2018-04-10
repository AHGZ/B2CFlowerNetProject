# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /androidresouce/tools/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class * extends android.support.v4.app.Fragment
-keep class com.gyf.barlibrary.* {*;}
-keep public class * extends android.webkit.WebChromeClient
#pay
-dontwarn com.tsy.sdk.pay.**
-keep class com.tsy.sdk.pay.**{*;}

#weixin
-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-keep class com.baidu.** {*;}
-keep class vi.com.** {*;}

-libraryjars libs/pushservice-VERSION.jar
-dontwarn com.baidu.**
-keep class com.baidu.**{*; }
-keep public class * extends android.webkit.WebChromeClient