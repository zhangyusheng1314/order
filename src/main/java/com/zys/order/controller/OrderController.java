package com.zys.order.controller;

import com.zys.order.dto.OrderDTO;
import com.zys.order.service.OrderService;
import com.zys.order.utils.ResultVOUtil;
import com.zys.order.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/order")
@RestController
public class OrderController {
    private @Autowired
    OrderService orderService;

    /**
     * 1查询商品信息（调用商品服务）
     * 2计算总价
     * 3扣库存（调用商品服务）
     * 4保存订单
     * @param order
     * {
     *  "buyerName": "张三",
     * 	"buyerPhone": "18868822111",
     * 	"buyerAddress": "慕课网总部",
     * 	"buyerOpenid": "ew3euwhd7sjw9diwkq",
     * 	"orderDetailList": [{
     * 	    "productId": "1423113435324",
     * 	    "productQuantity": 2
     *   }]
     * }
     * @return
     */
    @RequestMapping("/create")
    public ResultVO<Map<String,Object>> create(@RequestBody OrderDTO order){
        Map<String,Object> map = new HashMap<>();

        OrderDTO orderDTO = orderService.create(order);
        map.put("orderId",orderDTO.getOrderId());

        return ResultVOUtil.success(map);
    }
}
