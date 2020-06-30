package com.pm.background.admin.sys.entity.swagger;
import com.pm.background.admin.sys.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* 描述: 作为swagger list查询的类
* @author: Larry
* date: 2020-05-26
*/

@Data
@ApiModel("集合实体类")
    public class SysRoleSwaggerListEntity {
    @ApiModelProperty(value = "响应状态值,200为成功",example = "200")
    private Integer code;
    @ApiModelProperty(value = "响应信息,出错显示错误的具体信息",example = "成功")
    private String msg;
    @ApiModelProperty(value = "实体类集合",example = "{}")
    private List<Role> data;
    @ApiModelProperty(value = "是否失败",example = "false")
    private boolean error;
}
