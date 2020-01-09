package com.zys.order.client;

import com.zys.order.dto.CartDTO;
import com.zys.order.model.ProductInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * feign方法调用product的接口
 */
@FeignClient(name = "product")
public interface ProductClient {
    //product接口的url
    @RequestMapping("/msg")
    String getMsg();
    @RequestMapping("/product/getListById")
    List<ProductInfo> getListById(List<String> productId);
    @RequestMapping("/product/decreaseStock")
    void decreaseStock(List<CartDTO> cartDTOS);
}
