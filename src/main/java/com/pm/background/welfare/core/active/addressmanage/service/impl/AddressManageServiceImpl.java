package com.pm.background.welfare.core.active.addressmanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.background.welfare.core.active.addressmanage.dao.AddressManageMapper;
import com.pm.background.welfare.core.active.addressmanage.entity.AddressManageEntity;
import com.pm.background.welfare.core.active.addressmanage.service.AddressManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:地区设置实现类
 * author: Larry
 * date: 2020-05-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AddressManageServiceImpl extends ServiceImpl<AddressManageMapper, AddressManageEntity> implements AddressManageService {

}
