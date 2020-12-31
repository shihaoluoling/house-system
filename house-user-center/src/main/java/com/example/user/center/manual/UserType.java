package com.example.user.center.manual;

public enum UserType {
    USER("user"),//普通用户
    ADMIN("admin");//管理员
    private String paymentTypeName;

    UserType(String paymentTypeName) {

        this.paymentTypeName = paymentTypeName;
    }


    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }

    public static UserType getPaymentTypeEnum(String name) {
        for(UserType TYPE: UserType.values()) {
            if (TYPE.getPaymentTypeName().equals(name)) {
                return TYPE;
            }
        }
        return USER;
    }
}
