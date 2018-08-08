### 安装包大小优化
#### [应用安装包的构成]()

|应用安装包的构成|说明|
|------|------|
|assets|该目录可以存放任何文件夹架构，如配置文件、资源文件如WebView本地资源等，可以通过AssetManager类获得assets不会自动生成对应的ID，而是通过AssetManager类的接口提取|
|lib|该目录存放应用程序依赖的用C/C++编写的native文件|
|res|该目录存放资源文件|
|META-INF|保存应用的签名信息|
|AndroidManifest.xml|应用程序的配置文件|
|classes.dex|Java可执行程序|
|proguard.cfg|代码混淆配置文件|
|resources.arsc|记录资源文件和资源ID之间的映射关系，用来根据资源ID寻找资源|

#### [减少安装包大小的常用方案]()

|减少安装包大小的常用方案|
|-------|
|[代码混淆](http://proguard.sourceforge.net/manual/usage.html#classspecification)：压缩，移除无效的类，属性，方法等；优化，移除没用的结构；混淆：把类名、属性名、方法名替换为一到两个字母|
|资源优化：使用Android Lint删除冗余资源；Analyze->Run Inspection by Name,在输入框中输入unused resources；Android Lint不会分析assets文件夹下的资源；资源文件最少化：尽量使用一套图片资源(但是会影响内存),使用一套图、一套布局、多套dimens.xml文件；使用轻量级的第三方库；减少项目中的预置图片；|

```
buildTypes {
    release {
       minifyEnabled true
       signingConfig signingConfigs.release
       proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
    }
    debug {
       minifyEnabled false
    }
}
```
