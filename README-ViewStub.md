### 使用ViewStub标签

#### Activity
```
public class ViewStubActvitiy extends AppCompatActivity {
    private ViewStub viewStub;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewstub);
        viewStub = (ViewStub) findViewById(R.id.viewstub);
    }
    public void click(View view) {//使ViewStub解析，就可以显示在屏幕上了
        viewStub.inflate();
    }
}
```
#### res/layout/viewstub.xml
```
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="click"
        android:text="click"/>
    <ViewStub
        android:id="@+id/viewstub"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout="@layout/listview_item"/>
</LinearLayout>
```
