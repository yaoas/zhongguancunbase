package com.pm.background.welfare.core.active.weipay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现交互类
 * <BR/>
 * @author Liub
 *
 **/

@Data
public class TransferForm {

    private String userName;
    private String phone;
    private Date transTime;
    private BigDecimal transAmount;

    private Long id;

}
