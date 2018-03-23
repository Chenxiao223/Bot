package com.zhiziyun.dmptest.bot.entity;

/**
 * Created by Administrator on 2018/3/23.
 */

public class PayOrder {

    /**
     * status : true
     * errorcode :
     * errormsg :
     * response : {"orderInfo":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016091000479118&biz_content=%7B%22body%22%3A%22%E5%85%85%E5%80%BC%E8%B4%A6%E6%88%B7%22%2C%22enable_pay_channels%22%3A%22moneyFund%2CbankPay%2Cbalance%22%2C%22goods_type%22%3A%220%22%2C%22out_trade_no%22%3A%22201803231615500000000000000004%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%B4%A6%E6%88%B7%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2260m%22%2C%22total_amount%22%3A%221.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest2.zhiziyun.com%2Fapi-service%2FpayApp%2FnoticePayResult&sign=plDIOQGFokqAJ6HBiYIXIX0S0TS8gRZqUQfBvaxZC3S9HfcZjINcXk%2BgXvjAWNaHNOlI5wBEOLH%2F4xUo2aPrPvTHI2uIEENvKJqfnyzPZwfcPhdE%2Fhly64vMw8gMbH31OqRyYs273deo0iryusBY5JuCkCpOIbzmIjkWtc2YqRacNYyDcSvuSCaJuRSkPbqCMFGCJWn77FFugeofSJ4FyD2XhW7Ply%2FLM4XkPRrtwNcGFui9X5fLPjZaQ7zGx7yqaLPLZXVxP8PsUekHVOP1RMYvtafwAYZzh9yCC4%2F%2B4Q8rC3A4FuUchDb089ojzvL1h66L7T9SyNSsigfZ8YYmwA%3D%3D&sign_type=RSA2&timestamp=2018-03-23+16%3A15%3A50&version=1.0"}
     */

    private boolean status;
    private String errorcode;
    private String errormsg;
    private ResponseBean response;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * orderInfo : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016091000479118&biz_content=%7B%22body%22%3A%22%E5%85%85%E5%80%BC%E8%B4%A6%E6%88%B7%22%2C%22enable_pay_channels%22%3A%22moneyFund%2CbankPay%2Cbalance%22%2C%22goods_type%22%3A%220%22%2C%22out_trade_no%22%3A%22201803231615500000000000000004%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%B4%A6%E6%88%B7%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2260m%22%2C%22total_amount%22%3A%221.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest2.zhiziyun.com%2Fapi-service%2FpayApp%2FnoticePayResult&sign=plDIOQGFokqAJ6HBiYIXIX0S0TS8gRZqUQfBvaxZC3S9HfcZjINcXk%2BgXvjAWNaHNOlI5wBEOLH%2F4xUo2aPrPvTHI2uIEENvKJqfnyzPZwfcPhdE%2Fhly64vMw8gMbH31OqRyYs273deo0iryusBY5JuCkCpOIbzmIjkWtc2YqRacNYyDcSvuSCaJuRSkPbqCMFGCJWn77FFugeofSJ4FyD2XhW7Ply%2FLM4XkPRrtwNcGFui9X5fLPjZaQ7zGx7yqaLPLZXVxP8PsUekHVOP1RMYvtafwAYZzh9yCC4%2F%2B4Q8rC3A4FuUchDb089ojzvL1h66L7T9SyNSsigfZ8YYmwA%3D%3D&sign_type=RSA2&timestamp=2018-03-23+16%3A15%3A50&version=1.0
         */

        private String orderInfo;

        public String getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }
    }
}
