package com.hefu.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hefu.auth.dao.WebMenuDao;
import com.hefu.auth.entity.WebMenu;
import com.hefu.auth.service.WebMenuService;
import com.hefu.config.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * (WebMenu)表服务实现类
 */
@Service
@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class WebMenuServiceImpl extends ServiceImpl<WebMenuDao, WebMenu> implements WebMenuService {
    @Autowired
    private WebMenuDao webMenuDao;
    @Autowired
    RedisCache redisCache;
    @Value("${user.front.menu}")
    private String userFrontMenu;

    //查询该用户可操作所有资源
    @Override
    public List<String> selectSysMenuByUserId(Long userId) {
        List<String> list=null;
        list=redisCache.getCacheList("hefuajClient:webMenu:selectSysMenuByUserId"+userId);
        if(CollectionUtils.isEmpty(list)){
            list = webMenuDao.selectSysMenuByUserId(userId);
            //查询资源去空
            List nullList = new ArrayList<>();
            nullList.add(null);
            list.removeAll(nullList);
            redisCache.setCacheList("hefuajClient:webMenu:selectSysMenuByUserId"+userId,list);
        }
        return list;
    }

    //查询该用户可操作的路由信息
//    @Cacheable(cacheNames = {"hefuajClient"},key = "#root.methodName+':'+#userId")
    @Override
    public List<WebMenu> selectWebMenuTree(Long userId) {
        List<WebMenu> menus = null;
        menus = redisCache.getCacheList(userFrontMenu + userId);
        if(CollectionUtils.isEmpty(menus)){
            LinkedList<WebMenu> webMenus = webMenuDao.selectMenuTreeByUserId(userId);
            menus = buildMenuTree(webMenus);
            redisCache.setCacheList(userFrontMenu + userId,menus);
        }
        return menus;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<WebMenu> getChildPerms(List<WebMenu> list, int parentId)
    {
        List<WebMenu> returnList = new ArrayList();
        for (Iterator<WebMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            WebMenu t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<WebMenu> list, WebMenu t)
    {
        // 得到子节点列表
        List<WebMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (WebMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<WebMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    WebMenu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }
    /**
     * 得到子节点列表
     */
    private List<WebMenu> getChildList(List<WebMenu> list, WebMenu t)
    {
        List<WebMenu> tlist = new ArrayList<WebMenu>();
        Iterator<WebMenu> it = list.iterator();
        while (it.hasNext())
        {
            WebMenu n = it.next();
            if (n.getParentId().longValue() == t.getWebMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<WebMenu> list, WebMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<WebMenu> buildMenuTree(List<WebMenu> menus)
    {
        List<WebMenu> returnList = new ArrayList<>();
        for (Iterator<WebMenu> iterator = menus.iterator(); iterator.hasNext();)
        {
            WebMenu t = (WebMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0)
            {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = menus;
        }
        return returnList;
    }

}