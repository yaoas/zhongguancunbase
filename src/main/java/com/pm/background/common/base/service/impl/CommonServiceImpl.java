package com.pm.background.common.base.service.impl;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IService<T> {




}
