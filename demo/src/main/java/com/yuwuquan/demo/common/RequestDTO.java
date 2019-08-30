package com.yuwuquan.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel
public class RequestDTO {

	/**
	 * Do Not Remove this field
	 * 模块id
	 * */
	@ApiModelProperty(value="页面ID",example="10101",dataType="Integer")
	private Long moduleId;
	
}
