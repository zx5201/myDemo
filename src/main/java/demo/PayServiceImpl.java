package demo;

public class PayServiceImpl  implements PayService{


     String callback1(String outTradeNo) {
        System.out.println("目标类 PayServiceImpl 回调 方法 callback");
        return "888888";
    }

    public String callback(String outTradeNo) {
        return null;
    }

    public int save(int userId, int productId) {
        System.out.println("目标类 PayServiceImpl 回调 方法 save");
        return productId;
    }
}
