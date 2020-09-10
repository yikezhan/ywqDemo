package com.yuwuquan.demo.controller.aichatmessage;

import lombok.Data;

@Data
public class AIAnswer {
    private String context;//回答内容
    private boolean hasError;//是否匹配到了回答
    private String errMsg;//未匹配到回答的原因


    public AIAnswer(boolean hasError,String errMsg) {
        this.context = null;
        this.hasError = hasError;
        this.errMsg = errMsg;
    }
    public AIAnswer(String context) {
        this.context = context;
        this.hasError = false;
        this.errMsg = null;
    }
}
