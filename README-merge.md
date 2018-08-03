### 使用merge标签    
#### res/layout/main.xml
```
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <include layout="@layout/merge"/>
    </FrameLayout>
</LinearLayout>
```
#### res/layout/merge.xml
```
<merge
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
    />
</merge>
```
