package com.zys.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zys.order.model.ProductInfo;
import com.zys.order.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductInfoConsumer {
    private @Autowired
    StringRedisTemplate stringRedisTemplate;
    //扔到redis中的key的格式
    private final static String KEY_ID = "product_stock_%s";
    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        List<ProductInfo> productInfos = (List<ProductInfo>) JsonUtil.fromJson(message, new TypeReference<List<ProductInfo>>() {});
        log.info("productInfo:"+productInfos);
        for (ProductInfo productInfo : productInfos) {
            stringRedisTemplate.opsForValue().set(String.format(KEY_ID,productInfo.getProductId()),
                    String.valueOf(productInfo.getProductStock()));
        }
    }
}
