package com.yuwuquan.demo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel
@NoArgsConstructor
public class PaginationDTO implements Serializable {

    private static final long serialVersionUID = -1815131555864442839L;

    @ApiModelProperty(value = "页号", required = true, example = "1", dataType = "Integer")
    private Integer pageNumber;

    @ApiModelProperty(value = "分页大小", required = true, example = "20", dataType = "Integer")
    private Integer pageSize;

    @ApiModelProperty(value = "记录总数", example = "201", dataType = "Integer")
    private Long totalCount;

    public PaginationDTO(int pageSize, int pageNumber) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }
}
