#网络请求工具包
* 简化各参数设置
* 具有反调数据返回及直接返回方式
* 使用方法
    * 于项目build.gradle下添加

            compile 'com.mayousheng.www:basepojo:0.0.2'
    * 直接调用HttpUtils.getInstance()获取对象
    * 这里只封装了get/post两种请求方式，分别为直接获取数据和接口返回数据的方式
* 注意事项
    * android下使用时记得添加<uses-permission android:name="android.permission.INTERNET" />权限
    * android下不可主线程调用