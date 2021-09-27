package demo;

public class OrderServiceImpl  implements  OrderService{
    public void submit(String orderNo) {
        System.out.println("目标类 OrderServiceImpl 下单回调 方法 callback");
    }
}
