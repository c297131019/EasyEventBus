# EasyEventBus
一个轻量级的事件总线库

依赖

    dependencies {
	        compile 'com.github.c297131019:EasyEventBus:1.0.0'
	}

1、注册事件接收

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusManager.getInstance().registerEvent(this.getClass(),this);
    }
    
2、注销事件接收

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusManager.getInstance().unregisterEvent(this.getClass(),this);
    }


3、实现接口的方法

    @Override    
    public void onMsg(String s) {
           //这里面可以获取发送的内容
    }


4、发送事件

      1）
         //这种针对指定的对象发送
	 
         EventBusManager.getInstance().sendMsg(MainActivity.class,"6666");

      2）
         //这种对所有事件接收对象发送
	 
         EventBusManager.getInstance().sendMsg("6666");
