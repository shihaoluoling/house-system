package com.example.user.center.manual;

/**
 * @author MACHENIKE
 */

public enum TextTypeEnum {
    //富文本
    TEXT("text"),
    //PDF
    PDF("pdf"),
    //
    CAD("cad");
    private String paymentType;

    TextTypeEnum(String paymentType) {

        this.paymentType = paymentType;
    }


    public String getTextTypeType() {
        return this.paymentType;
    }

    public static TextTypeEnum getTextTypeEnum(String name) {
        for(TextTypeEnum TYPE: TextTypeEnum.values()) {
            if (TYPE.getTextTypeType().equals(name)) {
                return TYPE;
            }
        }
        return TEXT;
    }
}
