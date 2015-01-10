package com.next.aap.web.controller;

public enum DonationTemplateEnum {

	template01("template01"), template02("template02"),template03("template03"),template04("template04"),template05("template05")
, template06("template06"), template07("template07"), template08(
            "template08"), template09("template09");

    private String value;

    DonationTemplateEnum(String value) { this.value = value; }    

    public String getValue() { return value; }

    public static DonationTemplateEnum parse(String value) {
    	DonationTemplateEnum imageTemplateEnum = null; // Default
        for (DonationTemplateEnum item : DonationTemplateEnum.values()) {
            if (item.getValue().equals(value)) {
            	imageTemplateEnum = item;
                break;
            }
        }
        return imageTemplateEnum;
    }
}
