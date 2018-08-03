### 合理的刷新机制

|合理的刷新机制|说明|
|------|------|
|尽量减少刷新次数|控制刷新频率，避免没有必要的刷新|
|尽量避免后台有高CPU线程运行|如可以在ListView滑动的时候，停止工作线程的操作|
|减少刷新区域|自定义View中刷新指定区域调用方法：`invalidate(Rect dirty)`；`invalidate(int left , int top , int right , int bottom)`;ListView刷新单条数据可以调用Adapter的`notifyDataSetChanged()`刷新|

```
listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    workers.setPauseWork(true);
                } else {
                    workers.setPauseWork(false);
                } 
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
});
```
