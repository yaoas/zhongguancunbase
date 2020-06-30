package com.pm.background.welfare.core.active.weipay.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pm.background.welfare.core.active.weipay.entity.Refund;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 退款数据接口
 * <BR/>
 * @author Liub
 *
 */

@Repository
@Mapper
public interface RefundMapper extends BaseMapper<Refund> {
}
