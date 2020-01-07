package com.zys.order.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功");
    private Integer status;

    private String message;

    PayStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
