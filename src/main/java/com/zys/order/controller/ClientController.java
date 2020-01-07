package com.zys.order.controller;

import com.zys.order.client.ProductClient;
import com.zys.order.config.RestTemplateConfig;
import com.zys.order.dto.CartDTO;
import com.zys.order.model.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {
//    private @Autowired
//    LoadBalancerClient loadBalancerClient;

    private @Autowired
    ProductClient productClient;
//    private @Autowired
//    RestTemplate restTemplate;
    @RequestMapping("/getProduct")
    public String client(){
        //第一种方式 写死url
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://127.0.0.1:8080/msg",String.class);

        //第二种方式 使用loadBalancerClient通过应用名获取服务实例
//        RestTemplate restTemplate = new RestTemplate();
//        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
//        String url = String.format("http://%s:%s%s",serviceInstance.getHost(),serviceInstance.getPort(),"/msg");
//        String response = restTemplate.getForObject(url,String.class);
        //第三种方式 配置config类 使用注解@LoadBalanced
//        String response = restTemplate.getForObject("http://PRODUCT/msg",String.class);
//        log.info("" + response);
        //第四种 feign的方式
            String response = productClient.getMsg();
        return response;

    }

    @RequestMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productInfos = productClient.getListById(Arrays.asList("157875196366160022","157875227953464068"));
        log.info("response:{}" +productInfos);
        return "ok";
    }

    @RequestMapping("/decreaseStock")
    public String decreaseStock(){
        productClient.decreaseStock(Arrays.asList(new CartDTO("164103465734242707",3)));
        return "ok";
    }
}
