### 应用的启动流程
+ `APK文件可能运行在一个独立的进程中，也有可能产生多个进程，还可以多个APK运行在同一个进程中`；`每个应用只对应一个Application对象，并且启动应用一定会产生一个Application对象`；`一般在开发中都会创建一个继承于系统Application的类实现一些功能，如一些数据库的创建、模块的初始化等`；`启动Application时，系统会创建一个PID，即进程ID，所有的Activity都会在此进程上运行，在Application创建时初始化全局变量，同一个应用的所有Acivity都可以取到这个全局变量的值`；`尽量使用Application中的Context，因为Activity中的Context可能会导致内存泄露`；
