package com.zys.order.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    PRODUCT_IS_EXISTS(1,"商品不存在"),
    PRODUCT_IS_ENOUGH(2,"商品不足"),
    PRODUCT_DETAIL_IS_EXISTS(3,"商品详情不存在"),
    PRODUCT_IS_NEW(4,"不是新订单")
    ;
    private Integer code;

    private String message;

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message =message;
    }
}
