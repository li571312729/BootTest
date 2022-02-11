package com.hefu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.auth.dao.WebRoleMenuDao;
import com.hefu.auth.entity.WebRoleMenu;
import com.hefu.auth.service.WebRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * (WebRoleMenu)表服务实现类
 *
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WebRoleMenuServiceImpl extends ServiceImpl<WebRoleMenuDao, WebRoleMenu> implements WebRoleMenuService {

}