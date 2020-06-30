package com.pm.background.welfare.core.active.weipay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.welfare.core.active.weipay.entity.Transfer;
import com.pm.background.welfare.core.active.weipay.entity.TransferForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 提现数据接口
 * <BR/>
 * @author Liub
 *
 */

@Repository
@Mapper
public interface TransferMapper extends BaseMapper<Transfer> {

    /**
     * 查询提现列表
     * <BR/>
     * @return List<TransferForm>
     *
     */
    List<TransferForm> findTransferList(User user);

}
