### 避免过度绘制
+ `过度绘制是指在屏幕上的某个像素在同一帧的时间内被绘制了很多次`；
#### 过度绘制检测工具Show GPU Overdraw
+ `开发者模式中有过度绘制的工具`；Show GPU Overdraw;

|颜色|说明|
|------|------|
|无色|没有过度绘制|
|蓝色|每个像素多绘制了1次|
|绿色|每个像素多绘制了2次|
|淡红|每个像素多绘制了3次|
|深红|每个像素多绘制了4或更多次|

#### 避免过度绘制
+ 移除`XML`中非必须的背景，或按需设置；移除window默认的背景；按需显示占位背景图片;`设置`DecorView`背景为空的代码;`this.getWindow().setBackgroundDrawable(null);自定义View时使用`canvas.clipRect()`只有在这个区域内才会被绘制，其他区域将会被忽略；
### [无过度绘制View的实现](https://github.com/ningbaoqi/PerformanceOptimization/commit/2ddd3b15d93fb47d5ddb6bc1903fe28c1b93358e)
