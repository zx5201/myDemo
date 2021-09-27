package demo;

import org.springframework.cglib.core.DebuggingClassWriter;

public class Hello {

    public void sayHello(){
        System.out.println("打印hello world");
    }

    public static void main(String[] args) {
        //静态代理实现
        //TODO 静态代理缺陷 由于是编译时就把代理类织入进去
        // 相当于提前写死绑定的目标类 如果有成百上千个类 那就需要为每个类都编写代理类 冗余代码太多
        // 不符合开闭原则 所以还是建议使用动态代理
//       PayService payService = new PayServiceStaticProxyImpl(new PayServiceImpl());
//       payService.callback("1236");

       //动态代理实现 分为两种 jdk动态代理和CGLIB代理
        //经典面试题: JDK动态代理和CGLIB代理的区别
        /**
         * 在程序运行时，运用反射机制动态创建而成，无需手动编写代码
         *
         * JDK动态代理 (定义一个InvocationHandler 然后重写 invoke的方法)
         ***/
//        JdkProxy jdkProxy = new JdkProxy(); //不用去创建代理类的对象 直接把目标代理对象放进去就行了
//        PayService proxy = (PayService)JdkProxy.newProxyInstance(new PayServiceImpl());
//        proxy.callback("24444");
//        OrderService orderProxy = (OrderService)JdkProxy.newProxyInstance(new OrderServiceImpl());
//        orderProxy.submit("12344");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/zhangxiang/Downloads/MybatisDemo/code");

    //333333



        /**
         * CGLIB动态代理(原理：是对指定的业务类生成一个子类，并覆盖其中的业务方法来实现代理)
         */
        PayServiceImpl cglibProxy = (PayServiceImpl)JdkProxy.cglibProxy(new PayServiceImpl());
        cglibProxy.callback1("1234");


    }
}
