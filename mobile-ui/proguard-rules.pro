# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/fcejas/Software/SDKs/android-sdk/tools/proguard/proguard-android.txt
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

#################################################
# General
#################################################

# See this amazing post: https://medium.com/androiddevelopers/practical-proguard-rules-examples-5640a3907dc9

# Disabling obfuscation is useful if you collect stack traces from production crashes
# (unless you are using a system that supports de-obfuscate the stack traces).
# -dontobfuscate

# To keep class annotations
-keepattributes *Annotation*

# For allowing reflection on generic parameters: InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

# Attributes for de-obfuscating stack traces in Firebase uploading the mapping.txt file
# See:
# https://stackoverflow.com/a/39356251/5189200
# https://firebase.google.com/docs/crash/android#uploading_proguard_mapping_files_manually
-keepattributes SourceFile, LineNumberTable

# Add rules for all your models that are serialized and deserialized with Gson, Jackson, or
# any other library used by Retrofit, so that they are not removed by ProGuard, because they
# are created by reflection and not by code.
-keepclassmembers class org.buffer.android.boilerplate.datasources.remote.model.** {
  *;
}
-keepclassmembers class org.buffer.android.boilerplate.datasources.cache.model.** {
  *;
}
-keepclassmembers class org.buffer.android.boilerplate.datasources.remote.** {
  *;
}

#################################################
# Glide
#################################################

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder

#################################################
# Firebase
#################################################

# Don't add rules for Firebase, since they are automatically imported
# See: https://stackoverflow.com/a/41538901/5189200

#################################################
# Firebase Cloud Messaging
#################################################

-dontwarn com.google.firebase.messaging.**

#################################################
# Retrofit
#################################################

# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

#################################################
# OkHttp
#################################################

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform