package com.example.user.center.manual;

/**
 * @author shihao
 * @Title: LabelType
 * @ProjectName Second-order-center
 * @Description: 标签类型
 * @date Created in
 * @Version: $
 */
public enum  LabelType {
    //区域定位标签
    AREA("area"),
    //产品标签
    PRODUCT("product");
    private String paymentTypeName;

    LabelType(String paymentTypeName) {

        this.paymentTypeName = paymentTypeName;
    }


    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }

    public static LabelType getPaymentTypeEnum(String name) {
        for(LabelType TYPE: LabelType.values()) {
            if (TYPE.getPaymentTypeName().equals(name)) {
                return TYPE;
            }
        }
        return AREA;
    }
}
