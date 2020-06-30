package com.pm.background.welfare.core.active.weipay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pm.background.common.base.entity.DataEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 转账
 * <BR/>
 * @author Liub
 *
 */

@Data
@NoArgsConstructor
@TableName("su_transfer")
public class Transfer  extends DataEntity<Long> {

    private static final long serialVersionUID = 1L;

    // ID
    @TableId
    private Long id;
    // 付款人ID
    @TableField(value = "payer_id")
    private Long payerId;
    // 收款人ID
    @TableField(value = "payee_id")
    private Long payeeId;
    // 支付宝账号
    @TableField(value = "ali_name")
    private String aliName;
    // 提现单号
    @TableField(value = "out_biz_no")
    private String outBizNo;
    // 提现金额
    @TableField(value = "trans_amount")
    private BigDecimal transAmount;
    // 提现分类
    @TableField(value = "trans_type")
    private String transType;

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
        return this.id;
    }

}
