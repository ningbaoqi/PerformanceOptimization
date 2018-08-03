### 应用的启动流程
+ `APK文件可能运行在一个独立的进程中，也有可能产生多个进程，还可以多个APK运行在同一个进程中`；`每个应用只对应一个Application对象，并且启动应用一定会产生一个Application对象`；`一般在开发中都会创建一个继承于系统Application的类实现一些功能，如一些数据库的创建、模块的初始化等`；`启动Application时，系统会创建一个PID，即进程ID，所有的Activity都会在此进程上运行，在Application创建时初始化全局变量，同一个应用的所有Acivity都可以取到这个全局变量的值`；`尽量使用Application中的Context，因为Activity中的Context可能会导致内存泄露`；

|Application中的方法|说明|
|------|------|
|`void attachBaseContext(Context base)`|得到应用上下文的Context，在应用创建时首先调用|
|`void onCreate()`|应用创建时调用，晚于上面方法|
|`void onTerminate()`|应用结束时调用|
|`void onConfigurationChanged(Configuration newConfig)`|系统配置发生变化时调用|
|`void onLowMemory()`|系统低内存时调用|
|`void onTrimMemory(int level)`|系统要求应用释放内存时调用|

|启动方式|说明|
|------|------|
|冷启动|因为系统会重新创建一个新的进程分配给它，所以会先创建和初始化Application类，再创建和初始化MainActivity，最后显示在界面上；第一次启动该应用；应用被杀死了，再次开启的情况下|
|热启动|因为会从有的进程中启动，所以热启动不会再创建和初始化Application，而是直接创建和初始化MainActivity；`热启动：用户使用返回键退出应用，然后马上又重新启动应用`，进程还在只不过是退出了；|
