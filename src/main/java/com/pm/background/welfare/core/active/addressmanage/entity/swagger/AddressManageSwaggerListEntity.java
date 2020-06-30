package com.pm.background.welfare.core.active.addressmanage.entity.swagger;
import com.pm.background.welfare.core.active.addressmanage.entity.AddressManageEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* 描述: 地区设置作为swagger list查询的类
* @author: Larry
* date: 2020-05-28
*/

@Data
@ApiModel("地区设置集合实体类")
    public class AddressManageSwaggerListEntity {
    @ApiModelProperty(value = "响应状态值,200为成功",example = "200")
    private Integer code;
    @ApiModelProperty(value = "响应信息,出错显示错误的具体信息",example = "成功")
    private String msg;
    @ApiModelProperty(value = "地区设置实体类集合",example = "{}")
    private List<AddressManageEntity> data;
    @ApiModelProperty(value = "是否失败",example = "false")
    private boolean error;
}
