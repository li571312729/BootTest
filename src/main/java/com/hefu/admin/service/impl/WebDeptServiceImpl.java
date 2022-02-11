package com.hefu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.admin.dao.WebDeptDao;
import com.hefu.admin.entity.WebDept;
import com.hefu.admin.service.WebDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * (WebDept)表服务实现类
 *
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WebDeptServiceImpl extends ServiceImpl<WebDeptDao, WebDept> implements WebDeptService {

}