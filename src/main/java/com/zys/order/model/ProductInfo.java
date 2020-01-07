package com.zys.order.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangbin
 * @since 2019-12-30
 */
@Data
@Entity
public class ProductInfo {

    @Id
	@GeneratedValue
	private String productId;
    /**
     * 商品名称
     */
	private String productName;
    /**
     * 单价
     */
	private BigDecimal productPrice;
    /**
     * 库存
     */
	private Integer productStock;
    /**
     * 描述
     */
	private String productDescription;
    /**
     * 小图
     */
	private String productIcon;
    /**
     * 商品状态,0正常1下架
     */
	private Integer productStatus;
    /**
     * 类目编号
     */
	private Integer categoryType;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 修改时间
     */
	private Date updateTime;

}
