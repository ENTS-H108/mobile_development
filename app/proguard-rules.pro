# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

# Keep the KeyAttributes class and its constructor
-keep class androidx.constraintlayout.motion.widget.KeyAttributes {
    public <init>(...);
}

# Keep MotionLayout and related classes
-keep class androidx.constraintlayout.motion.widget.** { *; }
-keep class androidx.constraintlayout.widget.** { *; }

# Keep all model classes and their constructors
-keepclassmembers class com.ents_h108.petwell.data.model.** {
    public <init>(...);
}

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

-keep @androidx.annotation.Keep public class *