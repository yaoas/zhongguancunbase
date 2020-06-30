package com.pm.background.welfare.core.active.weipay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.welfare.core.active.weipay.dao.TransferMapper;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;
import com.pm.background.welfare.core.active.weipay.entity.TransferForm;
import com.pm.background.welfare.core.active.weipay.service.ITransferService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 提现服务
 * <BR/>
 * @author Liub
 *
 */

@Service
public class TransferService extends ServiceImpl<TransferMapper, Transfer> implements ITransferService {

    /**
     * 获取提现列表
     * <BR/>
     * @return List<TransferForm>
     *
     */
    public List<TransferForm> findTransferList(User user) {

        // 验证参数
        if(user == null) {
            return null;
        }

        return baseMapper.findTransferList(user);
    }

}
