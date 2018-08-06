### 序列化
+ 对象的类型信息、对象的数据、以及对象中的数据类型可以用来在内存中新建对象；
#### [Serializable&Parcelable]()

```
class ContactInfo implements Serializable {
    private String contactName;
    private int contactNum;

    public void setName(String name) {
        this.contactName = name;
    }

    public void setNumber(int number) {
        this.contactNum = number;
    }
}
```
+ 但是这种实现方式在性能方面不理想，Serializable在序列化时会产生大量的临时变量，在序列化的过程中会消耗更多的内存，从而引起频繁的GC;Parcelable:不能使用在要将数据存储在磁盘上的情况，如永久性保存对象、保存对象的字节序列到本地文件中，因为Parcel本质上是为了更好的实现对象在IPC间传递，并不是一个通用的序列化机制，改变Parcel中任何数据的底层实现都可能导致之前的数据不可读取，所以此时还是建议使用Serializable；

#### [Gson实现JSON的序列化&反序列化]()
```
public void gson() {
    Gson gson = new Gson();
    ContactInfo info = new ContactInfo();
    info.setName("cao ni ma");
    info.setNumber(123);
    String jsonContent = gson.toJson(info);//序列化
    ContactInfo myObj = gson.fromJson(jsonContent, ContactInfo.class);//反序列化
    String[] names = {"a", "b", "c"};
    String numbersJson = gson.toJson(names);//序列化数组
    ArrayList<String> myList = new ArrayList<>();
    String jsonList = gson.toJson(myList);//序列化集合
    List<ContactInfo> ms = gson.fromJson(jsonList, new TypeToken<ArrayList<ContactInfo>>() {}.getType());//反序列化集合数组
}
```
