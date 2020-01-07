package com.zys.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消");
    private Integer status;

    private String message;

    OrderStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
