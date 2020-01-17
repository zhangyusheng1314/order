package com.zys.order.service.impl;

import com.zys.order.client.ProductClient;
import com.zys.order.dto.CartDTO;
import com.zys.order.dto.OrderDTO;
import com.zys.order.enums.OrderStatus;
import com.zys.order.enums.PayStatus;
import com.zys.order.enums.ResultEnums;
import com.zys.order.exception.OrderException;
import com.zys.order.model.OrderDetail;
import com.zys.order.model.OrderMaster;
import com.zys.order.model.ProductInfo;
import com.zys.order.repository.OrderDetailRepository;
import com.zys.order.repository.OrderMasterRepository;
import com.zys.order.service.OrderService;
import com.zys.order.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangbin
 * @since 2019-12-30
 */
@Service
public class OrderServiceImpl implements OrderService {
    private @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductClient productClient;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO order) {
        String orderId = KeyUtil.genUniqueKey();

        List<String> productId = order.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        //查询商品信息
        List<ProductInfo> productInfos = productClient.getListById(productId);
        //计算总价
        BigDecimal amount= new BigDecimal(0);
        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            for (ProductInfo productInfo : productInfos) {
                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
                    amount = productInfo.getProductPrice()
                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                            .add(amount);
                    //插入orderDetail
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
                    orderDetail.setOrderId(orderId);
                    orderDetailRepository.save(orderDetail);
                }
            }
        }
        //减库存
        List<CartDTO> cartDTOS = order.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOS);
        //订单入库
        OrderMaster orderMaster = new OrderMaster();
        order.setOrderId(orderId);
        BeanUtils.copyProperties(order, orderMaster);
        orderMaster.setOrderAmount(amount);
        orderMaster.setOrderStatus(OrderStatus.NEW.getStatus());
        orderMaster.setPayStatus(PayStatus.WAIT.getStatus());
        orderMasterRepository.save(orderMaster);
        return order;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        Optional<OrderMaster> orderMaster = orderMasterRepository.findById(orderId);
        OrderMaster order = orderMaster.get();
        //订单是否存在
        if (!orderMaster.isPresent()) {
            throw new OrderException(ResultEnums.PRODUCT_IS_EXISTS);
        }
        //是否是新订单
        if (order.getOrderStatus()!= OrderStatus.NEW.getStatus()) {
            throw new OrderException(ResultEnums.PRODUCT_IS_NEW);
        }
        //修改为完结订单
        order.setOrderStatus(OrderStatus.FINISHED.getStatus());
        orderMasterRepository.save(order);

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getOrderId());

        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new OrderException(ResultEnums.PRODUCT_DETAIL_IS_EXISTS);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }
}
