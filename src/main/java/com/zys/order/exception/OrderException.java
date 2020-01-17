package com.zys.order.exception;

import com.zys.order.enums.ResultEnums;

public class OrderException extends RuntimeException{
    private Integer code;

    public OrderException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public OrderException(ResultEnums resultEnums) {
        super(resultEnums.getMessage());
        this.code = resultEnums.getCode();
    }


}
