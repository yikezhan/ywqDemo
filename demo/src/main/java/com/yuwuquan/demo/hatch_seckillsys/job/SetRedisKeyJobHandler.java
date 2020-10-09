package com.yuwuquan.demo.hatch_seckillsys.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.yuwuquan.demo.hatch_seckillsys.SecKillEnum;
import com.yuwuquan.demo.util.RedisUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * 秒杀系统设置key定时任务，可以配置在什么时候触发。key由触发时传入，没有的话默认为随机数
 */
@JobHandler(value="setRedisKeyJobHandler")
@Component
public class SetRedisKeyJobHandler extends IJobHandler {
	@Autowired
	RedisUtil redisUtil;
	@Override
	public ReturnT<String> execute(String param) throws Exception {
		XxlJobLogger.log("XXL-JOB, setRedisKeyJobHandler.time={}",new Date());
		String value = (param==null? String.valueOf(RandomUtils.nextInt()):param);
		redisUtil.set(SecKillEnum.SEC_KILL_START_KEY.getKey(),value,SecKillEnum.SEC_KILL_START_KEY.getTime());
		return SUCCESS;
	}

}
