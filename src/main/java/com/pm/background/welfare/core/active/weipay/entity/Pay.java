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
 * 支付记录
 * <BR/>
 * @author Liub
 *
 */

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("su_pay")
public class Pay extends RestEntity<Long> {

    private static final long serialVersionUID = 1L;

    // ID
    @TableId
    private Long id;
    // 支付人ID
    @TableField(value = "pay_user")
    private Long payUser;
    // 订单ID
    @TableField(value = "order_id")
    private Long orderId;
    // 订单分类
    @TableField(value = "order_type")
    private String orderType;
    // 支付金额
    @TableField(value = "pay_money")
    private BigDecimal payMoney;
    // 支付内容
    @TableField(value = "pay_body")
    private String payBody;
    // 支付时间
    @TableField(value = "pay_time")
    private Date payTime;
    // 支付方式
    @TableField(value = "pay_way")
    private String payWay;
    // 支付类型
    @TableField(value = "pay_type")
    private String payType;
    // 支付状态
    @TableField(value = "pay_state")
    private String payState;
    // 支付单号
    @TableField(value = "out_trade_no")
    private String outTradeNo;

    // 发起请求IP地址
    @TableField(exist = false)
    private String ip;
    // 客户端支付方式细节
    @TableField(exist = false)
    private String tradeType;
    // OpenId
    @TableField(exist = false)
    private String openId;

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
