package com.yuwuquan.demo.controller.request;

import com.yuwuquan.demo.common.RequestDTO;
import com.yuwuquan.demo.orm.model.PublishTask;
import lombok.Data;

@Data
public class PublishTaskRequest extends RequestDTO {
    private PublishTask publishTask;
}
