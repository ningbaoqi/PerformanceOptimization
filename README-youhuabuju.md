### 常用的布局优化工具
#### Hierarchy Viewer
### 布局优化方法

|布局优化方法|
|------|
|`布局优化`：`include、 marge、 viewstub标签`；`尽量不存在嵌套和过于复杂的布局`；`尽量使用gone替换invisible`；`尽量使用wrap替代长和宽减少运算`；`item如果存在非常复杂的嵌套时，可以考虑使用自定义View来取代，减少测量和layout执行的次数`|
|`列表及Adapter优化`：`复用`，滑动时候不要做元素的更新；`滑动到停止的时候采取加载数据`；`背景和图片等内存分配优化：图片进行压缩处理`；`避免ANR`|

|优化方式|说明|
|------|------|
|`include标签`|将可复用的组件抽取出来并通过`include`标签使用；作用：将共同的组件抽取出来单独放在一个xml文件中，然后使用`include标签`导入公共布局，效果：提高UI的制作和复用效率，也能保证制作的UI布局更加规整和易维护|
|`merge标签`|`使用merge标签减少布局的嵌套层次`，作用：合并UI布局，使用该标签能降低UI布局的嵌套层次；场景一：布局根节点是`FrameLayout`且不需要设置`background`或`padding`等属性时，可以使用`merge`代替；场景二：某布局作为子布局被其他布局`include`时，使用`merge`当作该布局的顶节点，这样在被引入时顶节点会自动被忽略；merge只能用在布局xml文件的根元素，不能在ViewStub中使用marge标签；使用merge来加载一个布局时，必须指定一个ViewGroup作为其父元素，并且要设置加载的attachToRoot参数为true；|
|`ViewStub标签`|使用ViewStub标签来加载一些不常用的布局，作用：`ViewStub标签同include标签一样用来引入一个外部布局`，不同的是，`ViewStub引入布局默认不会扩张，也就是不会显示，既不会占用显示也不会占用位置`，从而在解析layout时节省cpu和内存；ViewStub只能加载一次，之后ViewStub对象会被置为空，ViewStub只能用来加载一个布局文件，而不是某个具体的View，不能嵌套Merge标签|
