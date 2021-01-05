package com.example.user.center.manual;

public enum TypePictureEnum {
    TYPE("type");//楼房立面图
    private String paymentTypeName;

    TypePictureEnum(String paymentTypeName) {

        this.paymentTypeName = paymentTypeName;
    }


    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }

    public static TypePictureEnum getPaymentTypeEnum(String name) {
        for(TypePictureEnum TYPE: TypePictureEnum.values()) {
            if (TYPE.getPaymentTypeName().equals(name)) {
                return TYPE;
            }
        }
        return TYPE;
    }
}
