package com.poly.easylearning.config;

public class MoMoConfig {
    public static String PARTNER_CODE = "MOMOLACR20200918";
    public static String ACCESS_KEY = "906yYpQGxMORiQUb";
    public static String SECRET_KEY = "hUJdkl0kTscX9jBPU8WllyRqQWgIXY9R";
    public static String PAY_QUERY_STATUS_URL = "https://test-payment.momo.vn/pay/query-status";
    public static String PAY_CONFIRM_URL = "https://test-payment.momo.vn/pay/confirm";
    public static String RETURN_URL = "http://localhost:4200/payment-fail";
    public static String NOTIFY_URL = "http://localhost:1001/member/v1/payment/payment-success";
    public static String IPN_URL = "https://fcf6-123-24-233-164.ngrok.io";
    public static String CREATE_ORDER_URL = "https://test-payment.momo.vn/gw_payment/transactionProcessor";
    public static String REDIRECT_URL = "http://localhost:4200/payment-success";
}
