package com.zys.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.utils.FallbackMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "createOrderFallbackMethod4Timeout")
public class HystrixController {

    @RequestMapping("/getProducts")
//    @HystrixCommand(
//            commandProperties = {
//                    //设置超时时间，默认1000毫秒
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
//            },
//            fallbackMethod = "createOrderFallbackMethod4Timeout"
//    )
//    @HystrixCommand(
//            //指定特定的方法
//            commandKey = "getProductList",
//            commandProperties = {
//                    //设置熔断 打开断路器
//                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
//                    //设置最小请求数
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
//                    //当处于半开启状态的时候 定时器会有10秒的时间去判断主程序是否出错 不出错的话熔断器关闭 否则开启
//                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
//                    //出错率达到60%
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
//            }
//    )
    @HystrixCommand
    public String getProductList(@RequestParam Integer number){
        RestTemplate restTemplate = new RestTemplate();
        if (number==1){
            return "success";
        }
        String response = restTemplate.postForObject("http://127.0.0.1:8002/product/getListById",
                Arrays.asList("157875196366160022"),String.class);
        return response;
    }

    public String createOrderFallbackMethod4Timeout() throws Exception{
        System.err.println("-------超时降级策略执行------------");
        return "hystrix timeout !";
    }
}
