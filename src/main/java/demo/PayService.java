package demo;

/**
 * 支付接口
 */
public interface PayService {

    /**
     * 支付回调
     * @param outTradeNo 订单号
     * @return
     */
    String callback(String outTradeNo);


    /**
     * 下单
     * @param userId 用户id
     * @param productId 产品id
     * @return
     */
    int save(int userId,int productId);
}
