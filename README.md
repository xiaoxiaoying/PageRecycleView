# CheckBox

* 说明 来源[https://github.com/andyxialm/SmoothCheckBox][2]


![效果图](https://github.com/xiaoxiaoying/PageRecycleView/blob/master/gif/checkBox.gif)

<span style="color:red;">此项目不在维护</span>
* 新的 [libs][4]

# PageRecyclerView


为RecyclerView添加head

```java

 mRecyclerView.addHeaderView(view);

```
* 上拉加载

* 需要实现 OnLoadNextListener

```java
mRecyclerView.setLoadNextListener(this);
```

* 适配器试用ArrayAdapter，个人感觉挺好用的，和Android自带ArrayAdapter的使用方式一样，只是缺少Filter功能
同时也有条目监听事件

```java
adapter.setOnItemClickListener(this);

```
* 当然不使用ArrayAdapter也是可以的
* 还支持指定条目充满屏幕

```java
mRecyclerView.setItemLayoutMatchParent(5);
```

[2]:https://github.com/andyxialm/SmoothCheckBox
[3]:https://github.com/xiaoxiaoying/PageRecycleView/blob/master/gif/checkBox.gif
[4]:https://github.com/xiaoxiaoying/Page-RecyclerView-ArrayAdpter
