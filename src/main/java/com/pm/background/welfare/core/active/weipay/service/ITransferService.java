package com.pm.background.welfare.core.active.weipay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;
import com.pm.background.welfare.core.active.weipay.entity.TransferForm;

import java.util.List;

/**
 * 提现服务接口
 * <BR/>
 * @author Liub
 *
 */

public interface ITransferService extends IService<Transfer> {

    /**
     * 获取提现列表
     * <BR/>
     * @return List<TransferForm>
     *
     */
    List<TransferForm> findTransferList(User user);

}
