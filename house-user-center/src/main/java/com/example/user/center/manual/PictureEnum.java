package com.example.user.center.manual;

public enum PictureEnum {
    SUM("sum"),//楼房总图
    FACADE("facade");//楼房立面图
    private String paymentTypeName;

    PictureEnum(String paymentTypeName) {

        this.paymentTypeName = paymentTypeName;
    }


    public String getPaymentTypeName() {
        return this.paymentTypeName;
    }

    public static PictureEnum getPaymentTypeEnum(String name) {
        for(PictureEnum TYPE: PictureEnum.values()) {
            if (TYPE.getPaymentTypeName().equals(name)) {
                return TYPE;
            }
        }
        return SUM;
    }
}
