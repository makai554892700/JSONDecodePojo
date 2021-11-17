#json解析工具包
* 初衷是为了pojo能够混淆，去除getter/setter方法从而达到减小包大小
* 基于反射，注解，抽象类，性能不高，侵入性强
*
#为防止丢失关键构造方法，在Android开启混淆的情况下需要此配置

    -keep class * extends com.mayousheng.www.basepojo.BasePoJo{
        public <init>(java.lang.String);
    }