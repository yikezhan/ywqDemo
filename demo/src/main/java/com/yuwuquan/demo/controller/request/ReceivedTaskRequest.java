package com.yuwuquan.demo.controller.request;

import com.yuwuquan.demo.common.RequestDTO;
import com.yuwuquan.demo.orm.model.ReceivedTask;
import lombok.Data;

@Data
public class ReceivedTaskRequest extends RequestDTO {
    private ReceivedTask receivedTask;
}
