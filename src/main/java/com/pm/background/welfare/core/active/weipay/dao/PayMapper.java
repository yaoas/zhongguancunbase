package com.pm.background.welfare.core.active.weipay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.background.welfare.core.active.weipay.entity.Pay;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 支付数据接口
 * <BR/>
 * @author Liub
 *
 */

@Repository
@Mapper
public interface PayMapper extends BaseMapper<Pay> {

}
