package demo;

public class PayServiceStaticProxyImpl implements PayService {
    private PayService payService;

    public PayServiceStaticProxyImpl(Object o) {
        this.payService = payService;
    }

    public String callback(String outTradeNo) {
        System.out.println("PayServiceStaticProxyImpl callback begin");
        String result = payService.callback(outTradeNo);
        System.out.println("PayServiceStaticProxyImpl callback end");
        return result;
    }

    public int save(int userId, int productId) {
        System.out.println("PayServiceStaticProxyImpl save begin");
        int id = payService.save(userId, productId);
        System.out.println("PayServiceStaticProxyImpl save end");
        return id;
    }
}
