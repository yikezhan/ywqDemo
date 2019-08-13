package com.yuwuquan.demo.designpatterns.specialstrategy.pojo;

import lombok.Data;

@Data
public class Order{
    //产品编号
    private String productCode;
    //产品名
    private String productName;
    /** 单号模块处理 */
    private String orderId;
    /** 库存模块处理。返回是否充足可售 */
    private Boolean canSale;
    /** 价格模块处理 */
    private Double price;
}
