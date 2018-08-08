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
