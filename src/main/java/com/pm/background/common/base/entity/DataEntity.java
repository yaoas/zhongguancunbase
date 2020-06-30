package com.pm.background.common.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 数据Entity类
 * 
 * @author hs
 * @date 2018-09-27
 *
 */
@Getter
@Setter
public abstract class DataEntity<ID> extends AbstractEntity<ID> {

    @ApiModelProperty(value="创建人",required=false,hidden = true)
	@TableField(value = "create_by",  fill =FieldFill.INSERT)
	private Long createBy; // 创建者
    @ApiModelProperty(value="创建时间",required=false,hidden = true)
	@TableField(value = "create_time", fill =FieldFill.INSERT)
	private LocalDateTime createTime; // 创建日期
    @ApiModelProperty(value="最后一次修改人",required=false,hidden = true)
	@TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
	private Long updateBy; // 更新者
    @ApiModelProperty(value="最后一次修改时间",required=false,hidden = true)
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime; // 更新日期
    @ApiModelProperty(value="是否删除",hidden=true)
	@TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
	private Integer delFlag; // 删除标记（0：正常；1：删除 ）


	public DataEntity() {
		super();
	}
    @ApiModelProperty(value="用户id",hidden=true)
    @TableField(exist = false)
    private  Long userId;


}
