package demo;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk 动态代理类
 * 这里不用实现invocationHandLer这个接口 直接下面传入具体的接口实现
 */
public class JdkProxy {


    /**
     * 获取代理对象
     *
     * @param targetObject 目标类
     * @return
     */
    public static Object newProxyInstance(final Object targetObject) {
        //绑定关系，也就是和具体的那个实现类关联
        // targetObject.getClass() 目标对象的类加载器
        //targetObject.getClass().getInterfaces() 目标对象实现的所有接口
        //new InvocationHandler() 目标对象的接口实例  第三个参数为整合了业务逻辑和横切逻辑的编织器对象.
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(), new InvocationHandler() {
                    //这里直接传入需要代理的目标对象的接口实例
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object result = null;
                        try {
                            System.out.println("通过JDK动态代理调用" + method.getName() + ",打印日志 begin");
                            result = method.invoke(targetObject, args);
                            System.out.println("通过JDK动态代理调用" + method.getName() + ",打印日志 end");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return result;
                    }
                });
    }

    //CGLIB代理对象

    /**
     * JDK动态代理只能对实现了接口的类生成代理，而不能针对类 这句话的意思是这个需要用动态代理必须要有一个接口interface 而不是只有一个类class对象
     * CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法 因为是继承，所以该类或方法不要声明成final 不能被重写的方法,cglib操作都不会生成动态代理 无法实现动态代理
     * cglib的话就可以是针对类的 没有接口也可以实现代理对象
     * @param target
     * @return
     */
    public static Object cglibProxy(final Object target) {
        //这个是cglib创建代理对象增强的一个工具类
        Enhancer enhancer = new Enhancer();
        // Cglib需要对目标对象的Class字节码进行修改.
        // Cglib产生的代理对象实例.是目标对象的子类
        enhancer.setSuperclass(target.getClass());
        // 只要是代理都会对原来的内容进行增强操作 ( 增强就是在原有功能上 额外添加的功能 )
        //  设置用于增强    操作的实现类( MethodInterceptor对代理方法进行拦截 )
        // 每次只要调用Cglib代理的方法,都会执行 MethodInterceptor 接口中 intercept() 方法
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    System.out.println("通过CGLIB调用代理日志打印前" + method.getName() + "----begin");
                    Object o = methodProxy.invokeSuper(proxy, args);
//                    Object invoke1 = methodProxy.invoke(proxy, args); //通过坐标的方式返回代理对象
//                    Object invoke2 = method.invoke(proxy, args);//通过反射的方式返回代理对象
                    result = o;
                    System.out.println("通过CGLIB调用代理日志打印后" + method.getName() + "-----end");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常");
                }
                return result;
            }
        });

        return enhancer.create();
    }


    /***
     *      * public static Object createCglibProxy(Object target) {
     *         // 是Cglib用于创建代理对象的增强的一个工具类
     *         Enhancer enhancer = new Enhancer();
     *
     *         // Cglib需要对目标对象的Class字节码进行修改.
     *         // Cglib产生的代理对象实例.是目标对象的子类
     *         enhancer.setSuperclass(target.getClass());
     *
     *         // 只要是代理都会对原来的内容进行增强操作 ( 增强就是在原有功能上 额外添加的功能 )
     *         // setCallback() 设置用于增强    操作的实现类( MethodInterceptor对代理方法进行拦截 )
     *         // 每次只要调用Cglib代理的方法,都会执行 MethodInterceptor 接口中 intercept() 方法
     *         enhancer.setCallback(new MethodInterceptor() {
     *             /**
     *              * intercept() 方法 跟 InvocationHandler接口中 invoke() 功能完全一样
     *              * @param proxy         Cglib代理对象实例 <br/>
     *              * @param method        调用方法的反射对象实例 <br/>
     *              * @param args          调用方法时传递的参数 <br/>
     *              * @param methodProxy   代理方法的method代理对象 <br/>
     *              * @return 是代理对象方法的返回值. <br/>
     *              * @throws Throwable
     *              */

    // @Override
    /** *

     public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
     *
     *Object result = null;
     *try {
     *System.out.println("目标方法前");
     *                     //Object o = methodProxy.invokeSuper(target, args);
     *Object invoke1 = methodProxy.invoke(target, args);
     *Object invoke2 = method.invoke(target, args);
     *                     // 调用目标方法
     *result = invoke2;
     *                     // 执行增强的代码
     *System.out.println("目标方法后");
     *} catch (Exception e) {
     *e.printStackTrace();
     *System.out.println("异常");
     *}
     *return result;
     *}
     *
     });
     /* *
     *         //创建Cglib代理实例
     *return enhancer.create();
     *}
     *
     *     /**
     *      * 通过Jdk底层自带的Jdk动态代理技术
     *      *
     *      * @param target
     *      * @return
     *      */
    /**
     *public static Object createJdkProxy(Object target){
     *         /**
     *          * Proxy 是Jdk中自带的一个工具类(反射包下,属于反射的功能). <br/>
     *          * Proxy类的作用: 它可以帮我们创建代理类或实例 <br/>
     *          * 方法newProxyInstance()说明: 创建代理对象实例 <br/>
     *          *  第一个参数是: 目标对象的类加载器 <br/>
     *          *  第二个参数是: 目标对象实现的所有接口<br/>
     *          *  第三个参数是: InvocationHandler  接口的实例<br/>
     *          *  InvocationHandler 接口的实现类可以对代理的目标对象方法进行增强操作. <br/>
     *          *  代理的目标对象 ===>>>  需要额外增加功能的类(对象实例) <br/>
     *          *  增强操作    ===>>> 给原来功能添加的额外功能叫增强操作 ( 日记就是增强操作 )<br/>
     *          */
    /**
     *return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),
     *new InvocationHandler(){
     *                     /**
     *                      * invoke方法 是 InvocationHandler 接口中唯一的方法. <br/>
     *                      *  在代理对象每次调用方法时,都会执行 invoke() 方法 , 我们所有的增强操作都需要在invoke()方法中完成<br/>
     *                      * @param proxy     是代理对象实例 <br/>
     *                      * @param method    是代理调用的方法的反射 Method 对象实例 <br/>
     *                      * @param args      是调用代理方法时传递进来的参数 <br/>
     *                      * @return
     *                      * @throws Throwable
     *                      */
    /**
     *
     *
     *
     * @Override
     *
     * public Object invoke(Object proxy,Method method,Object[]args)throws Throwable{
     * //                        System.out.println(method); // 打印方法信息
     * //                        System.out.println( Arrays.asList(args) );// 打印参数信息
     *
     *Object result=null;
     *System.out.println("目标方法前");
     *try{
     *                             // 1 在 invoke() 方法我们要执行代理对象的操作 (加法 或 除法)
     *                             // 返回值是 method 方法调用时的返回值
     *result=method.invoke(target,args);
     *                             // 2 以及增强操作( 就是日记操作 )
     *System.out.println("目标方法后");
     *}catch(Exception e){
     *System.out.println("目标方法异常");
     *e.printStackTrace();
     *}
     *                         // invoke() 方法的返回值,就是代理方法的返回值.
     *return result;
     *}
     *}
     *);
     *}
     */


}
