package com.hefuaj.config;

import javax.validation.groups.Default;

/**
 * 针对某些DTO在新增和编辑时内部属性校验规则不一致的情况（例如 id字段通常编辑必须，新增不需要）
 * 定义validated分组器实现不同情况的校验
 *
 * 这里也可以不继承Default，但是不继承的话，接口校验部分就需要加上 @Validated(value = {Default.class}这个，不然不会对
 * DTO中其他未分组的属性进行校验
 *
 * @author lxq
 * @date 2021年05月19日 16:21
 */
public interface ValidGroup extends Default {

    //不同的接口操作类型 增删改查
    interface Create extends ValidGroup{

    }

    interface Update extends ValidGroup{

    }

    interface Query extends ValidGroup{

    }

    interface Delete extends ValidGroup{

    }
}

