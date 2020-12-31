package com.example.user.center.manual;

/**
 * @author shihao
 * @Title: Login
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */
public enum  Login {
    PHONE("phone"),//手机号登录
    ADMIN("admin");//后台账号密码登录
    private String paymentTypeName;

    Login(String paymentTypeName) {

        this.paymentTypeName = paymentTypeName;
    }


    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }

    public static Login getPaymentTypeEnum(String name) {
        for(Login TYPE: Login.values()) {
            if (TYPE.getPaymentTypeName().equals(name)) {
                return TYPE;
            }
        }
        return PHONE;
    }
}
