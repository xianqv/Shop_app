# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android-Studio\Android-SDK/tools/proguard/proguard-android.txt
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

######################## 公共 ########################

#指定代码的压缩级别
-optimizationpasses 5

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#记录生成的日志数据,gradle build时在本项目根目录输出
#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

#移除log代码
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** i(...);
    public static *** d(...);
    public static *** w(...);
    public static *** e(...);
}

#不混淆反射用到的类
-keepattributes Signature
-keepattributes EnclosingMethod

#保持继承自系统类的class不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep interface android.support.v7.app.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep public class * extends android.app.Fragment
-keep class * extends android.**{*;}

#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

######################## Module自定义 ########################

############ 不混淆引用的jar ############

#不混淆butterknife
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
-dontwarn butterknife.internal.**

#不混淆AndroidBootstrap
-keep class com.beardedhen.androidbootstrap.**{*;}
-dontwarn com.beardedhen.androidbootstrap.**

#不混淆应用宝自更新jar
-keep class com.qq.**
-dontwarn com.qq.**
-keep class com.tencent.**
-dontwarn com.tencent.**

############ 保持自定义控件不被混淆 ############

-keepclasseswithmembernames class * {
    public <init>(android.content.Context);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int, int);
}

############ 项目内部类的混淆配置 ############

#不混淆整个包
#-keep class com.test.test.**{*;}

#不混淆对外接口的public类名和成员名，否则外部无法调用
#-keep public interface com.test.test.**{*;}
#-keep public enum com.test.test.**{*;}
#-keep public class com.test.test.**{
#    public *;
#}

#忽略项目中其他Module的警告 ############
#-dontwarn com.test.test.**

