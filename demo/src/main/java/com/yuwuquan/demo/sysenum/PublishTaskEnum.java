package com.yuwuquan.demo.sysenum;

public enum PublishTaskEnum {
    Reserve(1),//新建状态，保留字段，占无含义
    AduitPass(2),//审核通过，占不审核，新建后自动通过
    PaymentSuccess(4),//付款成功
    PublishSuccess(8);//发布成功

    private Integer code;

    public Integer getCode() {
        return code;
    }

    private PublishTaskEnum(Integer code) {
        this.code = code;
    }

}
