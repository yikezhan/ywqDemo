package com.yuwuquan.demo.activemq.process;

import com.yuwuquan.demo.activemq.constant.ProcessResultEnum;
import com.yuwuquan.demo.activemq.message.template.MessageDetail;

public interface MessageProcess {

	public ProcessResultEnum processMessage(MessageDetail<?> messageDetail);

}
