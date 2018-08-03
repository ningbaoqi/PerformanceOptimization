### 提升动画性能
+ 优先使用属性动画->补间动画->帧动画;
#### 硬件加速
##### Application
```
<application android:hardwareAccelerated="true"/>//整个应用全局使用硬件加速
```
##### Activity
```
<activity android:hardwareAccelerated="true"/>//单个Activity使用硬件加速
```
##### Window
```
//对某个Window进行硬件加速
getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
```
