package com.pm.background.welfare.core.active.weipay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pm.background.welfare.core.active.weipay.entity.Pay;
import com.pm.background.welfare.core.active.weipay.entity.Refund;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;

import java.util.Map;

/**
 * 支付服务接口
 * <BR/>
 * @author Liub
 *
 */

public interface IPayService extends IService<Pay> {

    /**
     * 生成预支付单
     * <BR/>
     * @return String
     *
     */
    String initPay(Pay entity);

    /**
     * 退款
     * <BR/>
     * @return boolean
     *
     */
    boolean refund(Refund entity);

    /**
     * 转账
     * <BR/>
     * @return String
     *
     */
    String transfer(Transfer transfer);

    /**
     * 订单回调处理
     * <BR/>
     * @return boolean
     *
     */
    boolean orderReset(Pay entity);

    /**
     * 支付后的回调
     * <BR/>
     * @return boolean
     *
     */
    boolean notify(Map<String, String> params);

    /**
     * 退款后的回调
     * <BR/>
     * @return boolean
     *
     */
    boolean notify(Refund entity);

    /**
     * 转账后的回调
     * <BR/>
     * @return String
     *
     */
    public String notify(Transfer entity);

    /**
     * 支付回调判断
     * <BR/>
     * @return boolean
     *
     */
    boolean support(Pay entity);

    /**
     * 退款回调判断
     * <BR/>
     * @return boolean
     *
     */
    boolean support(Refund entity);

    /**
     * 转账回调判断
     * <BR/>
     * @return boolean
     *
     */
    boolean support(Transfer transfer);

}
