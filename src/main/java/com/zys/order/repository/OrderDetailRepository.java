package com.zys.order.repository;

import com.zys.order.model.OrderDetail;
import com.zys.order.model.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
}
