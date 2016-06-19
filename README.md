# FoldLine
折线图，支持横向滚动，支持刻度自定义
</br>
</br>
<img src='https://github.com/lichangqiang/FoldLine/blob/master/demo.gif' height="350" width="250"/>
<h3>一、实现原理</h3>
本控件有三个实体类：</br>
1、CrossAxisScale 用于自定义横向刻度</br>
2、VerticalAxisScale 用于定义纵向刻度</br>
3、FoldLinePoint 用于定义每个折线点</br>
若横向刻度为空则FoldLinePoint的scaleTip用于定义刻度值。</br>
首先根据CrossAxisScale来画纵轴的刻度</br>
再用VerticalAxisScale或FoldLinePoint来画横轴的刻度</br>
最后FoldLinePoint把所有的点连接起来</br>
<h3>二、使用方法</h3>
1、添加横向刻度（若不定义则使用折线点刻度）</br>
2、添加纵向刻度</br>
3、添加折线点</br>
4、调用initData（）方法将这些添加进去</br>
5、设置Listener</br>
注：刻度的宽度、颜色属性等需要在layout里面进行设置 如下：</br>
<com.magicwork.foldline.FoldLineView</br>
        android:id="@+id/fl_content"</br>
        android:layout_width="match_parent"</br>
        android:layout_height="300dp"</br>
        android:paddingRight="15dp"</br>
        android:paddingTop="15dp"</br>
        app:axisScaleWidth="5dp"</br>
        app:axisWidth="1dp"</br>
        app:crossAxisColor="#F88D3D"</br>
        app:foldLineCircleColor="@android:color/white"</br>
        app:foldLineCircleRadius="1dp"</br>
        app:foldLineColor="#3396C574"</br>
        app:foldLineOuterColor="#8CBA68"</br>
        app:isDrawFoldLineCircle="true"</br>
        app:isFillMode="true"</br>
        app:verticalAxisColor="#F88D3D"  /></br>
 
