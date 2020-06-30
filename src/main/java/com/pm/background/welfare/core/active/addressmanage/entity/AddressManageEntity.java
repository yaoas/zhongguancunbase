package com.pm.background.welfare.core.active.addressmanage.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pm.background.common.base.entity.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 描述: 地区设置
 * author: Larry
 * date: 2020-05-28
 */
@TableName("address_manage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("地区设置实体类")
public class AddressManageEntity extends DataEntity<Long> {

    @TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value="地区设置")
    private Long id;

    @NotBlank(message = "地区名称不能为空")
    @TableField(value = "name")
    @Size(min=1,max = 20,message = "地区名称为1-20个字")
    @ApiModelProperty(value="地区名称")
    private String name;

    @TableField(value = "show_status")
    @ApiModelProperty(value="是否展示")
    private Integer showStatus;

    @TableField(value = "level")
    @ApiModelProperty(value="地区级别0为1级1为2级")
    private Integer level;

    @TableField(value = "parent_id")
    @ApiModelProperty(value="父集id没有为0")
    private Long parentId;

    @TableField(exist = false)
    @ApiModelProperty(value="子集合")
    private List<AddressManageEntity> Children;

    @Override
    public Long getId() {
    return id;
    }

    @Override
    public void setId(Long id) {
    this.id = id;
    }

    @Override
    protected Serializable pkVal() {
    return null;
    }
}