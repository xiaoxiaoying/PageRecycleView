# CheckBox

* 说明 来源[https://github.com/andyxialm/SmoothCheckBox][2]


![效果图](https://github.com/xiaoxiaoying/PageRecycleView/blob/master/gif/checkBox.gif)

# PageRecyclerView


为RecyclerView添加head

```java

 mRecyclerView.addHeaderView(view);

```
* 下拉刷新 只支持 **VERTICAL**

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
