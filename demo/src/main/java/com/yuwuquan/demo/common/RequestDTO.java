package com.yuwuquan.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ApiModel
public class RequestDTO implements Serializable {
	private static final long serialVersionUID = -1234567891234567891L;
	/**
	 * 模块id，占未使用
	 * */
	@Deprecated
	@ApiModelProperty(value="页面ID",example="10101",dataType="Integer")
	private Long moduleId;

	@ApiModelProperty(value="分页参数")
	@Setter
	@Getter
	private PaginationDTO paginationDTO;
}
