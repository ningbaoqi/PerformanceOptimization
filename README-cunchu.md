### 存储优化
#### [SharedPreferences]()
+ SharedPreferences的缺点是：只能存储boolean、int、float、long、string五种简单的数据类型；每一次commit和apply操作都是一次i/o写操作，i/o操作是最慢的操作之一，在主线程中操作会导致主线程缓慢；editor的commit或apply方法区别是同步写入和异步写入；最好的优化方式就是避免频繁的读写SharedPreferences；建议使用apply方法提交批量提交；`不能跨进程同步`；`不能跨进程读写数据`；`存储SharedPreference的文件过大问题，会造成界面卡顿`；`不使用静态变量保存核心的数据`；因为进程被杀死就会重新初始化；应该使用：`文件/SharedPreference/contentProvider等`；
#### [文件存储]()
+ 内存存储空间内的文件默认只有创建文件的应用可以访问，而外部存储所有应用都可以访问；内部存储在应用卸载后，数据一起也删除；外部存储在有的设备上可以移除，所以使用外部存储需要判断是否已经挂载；应用默认安装在内部存储中，但是可以在AndroidManifest中指定android:installLocation属性来使应用安装到外部存储空间；
#### [SQLite]()
+ SQLite的优点：多线程访问数据、需要事务处理、应用程序需要处理需要变化的复杂数据结构、数据库对于创建他们的包套件是私有的；数据库的操作都比较耗时，一定放在异步线程中，使用回调或者监听方式更新；
##### [一、使用SQLiteStatement]()
```
    SQLiteStatement sqLiteStatement;

    /**
     * 使用Android系统提供的SQLiteStatement类来将数据插入数据库，在性能上有一定的提高，并且也解决了sql注入的问题
     *
     * @return
     */
    public SQLiteStatement getSQLiteStatement() {
        if (sqLiteStatement == null) {
            sqLiteStatement = getSqliteDB().compileStatement(STR_INSERT_STATEMENT_CONTACTS);
        }
        return sqLiteStatement;
    }

    /**
     * 属于SQLiteStatement插入数据，SQLiteStatement只能插入具体的一个表中的数据，在插入数据前记得先清除上一次的数据
     *
     * @param info
     * @return
     */
    public boolean insertContactInfoForStat(ContactInfo info) {
        getSQLiteStatement().clearBindings();
        getSQLiteStatement().bindLong(1, info.getContactId());
        getSQLiteStatement().bindString(2, info.getContactName());
        getSQLiteStatement().bindString(3, info.getContactNum());
        return getSQLiteStatement().executeInsert() > 0;
    }
```
##### [二、使用事务]()
```
    /**
     * 显式使用事务可以大大的提高系统的性能
     * 原子提交：意味着数据库的当次事务所有修改只有两个结果，所有修改都完成或者什么都没做，事务提交不会产生部分提交的结果，从而保证数据的同步
     * 性能更好：可以非常明显的提高插入时间
     * 事务是可以嵌套的：并且只有调用了setTransactionSuccessful方法，事务才会成功提交完成
     */
    public void useTransaction() {
        getSqliteDB().beginTransaction();
        try {
            for (int i = 0; i < 10000; i++) {
                //插入数据库
            }
            getSqliteDB().setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getSqliteDB().endTransaction();
        }
    }
```
##### [三、使用索引]()
+ 数据表的索引类似字典中的拼音索引或首部索引，要查找某个数据，通过索引查找可以提高查找的效率，否则需要全局遍历所有数据，也不意味着需要执行一个全序列查找；索引信息会保存在一个独立的索引表中；SQLite会自动为每一个UNIQUE栏位创建索引，包括主键，另外也可以通过`create index`显式创建；索引的缺点：数据库的插入、更新和删除使用索引反而会变慢，因为删除/更新字典中的一个字也需要删除这个字在拼音索引或首部索引中的信息；建立索引会增加数据库的大小；不建议使用的场景：在较小的表上；在有频繁的大批量更新或插入操作的表上；在含有大量NULL值的列上；在频繁操作的列上；
##### [四、异步线程、写数据库统一管理]()
+ 非常有必要放在异步系统中操作，同时为了保证数据的同步和避免一些死锁等待的情况，可以考虑双缓冲机制；双缓冲机制是指在操作时，把一些常用的数据放在内存缓冲中，再异步更新到数据库中，把所有的数据库操作统一到一个线程队列执行；
##### [五、提高查询性能]()
+ 影响查询速度的主要原因：查询数据量的大小、查询数据的列数、排序的复杂度；在查询应该避免没有必要的数据，提高查询速度；
#### [ContentProvider]()
+ ContentProvider是为了其他的应用程序访问本应用数据的对外的接口；
### [序列化](https://github.com/ningbaoqi/PerformanceOptimization/blob/master/README-xuliehua.md)
