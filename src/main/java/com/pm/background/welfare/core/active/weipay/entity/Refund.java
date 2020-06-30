package com.pm.background.welfare.core.active.weipay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pm.background.common.base.entity.RestEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款记录
 * <BR/>
 * @author Liub
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("su_refund")
public class Refund extends RestEntity<Long> {

    private static final long serialVersionUID = 1L;

    // ID
    @TableId
    private Long id;
    // 退款人ID
    @TableField(value = "refund_user")
    private Long refundUser;
    // 退款单号
    @TableField(value = "trade_no")
    private String tradeNo;
    // 订单金额
    @TableField(value = "pay_money")
    private BigDecimal payMoney;
    // 退款金额
    @TableField(value = "refund_money")
    private BigDecimal refundMoney;
    // 退款时间
    @TableField(value = "refund_time")
    private Date refundTime;
    // 支付内容
    @TableField(value = "refund_body")
    private String refundBody;
    // 退款状态
    @TableField(value = "refund_state")
    private String refundState;
    // 退款单号
    @TableField(value = "out_trade_no")
    private String outTradeNo;

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
