### 图片内存优化
#### [设置位图规格]()
+ 最高的是`ARGB_8888`也是系统默认的位图格式，其他的位图格式`RGB_565`、`ARGB_4444`、`ALPHA_8`,可以减少内存开销；只要满足以下其中之一就可以考虑使用`RGB_565`：显示局部图片，比如列表中的小图片；小屏幕手机或者对图片质量要求不高的场景；`ARGB_4444`使用在头像；`ALPHA_8`图像要渲染两次，虽然减少了内存，但增加了绘制的开销；

```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inPreferredConfig = Bitmap.Config.RGB_565;//通过设置inPreferredConfig参数来实现不同的位图规格
BitmapFactory.decodeStream(is, null, options);
```
#### [设置图片缩放]()

```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inSampleSize = 4;//实现位图的缩放功能
BitmapFactory.decodeStream(is, null, options);
```
#### [设置图片更细节的缩放]()

```
BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = true;
BitmapFactory.decodeStream(is, null, options);
options.inScaled = true;//系统会按照现有的密度来划分目标密度，但是会有更多的时间开销，会影响图片的显示效果
options.inDensity = options.outWidth;
options.inSampleSize = 4;
options.inTargetDensity = dstWidth * options.inSampleSize;
options.inJustDecodeBounds = false;
BitmapFactory.decodeStream(is, null, options);
```
