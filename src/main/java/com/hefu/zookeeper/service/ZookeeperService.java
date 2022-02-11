package com.hefu.zookeeper.service;

import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;

import java.util.List;

public interface ZookeeperService {
    /**
     * 判断节点是否存在
     */
    boolean isExistNode (final String path) ;
    /**
     * 创建节点
     */
    boolean createNode (CreateMode mode, String path ) ;
    /**
     * 设置节点数据
     * @return
     */
    boolean setNodeData (String path, String nodeData) ;
    /**
     * 创建节点
     * @return
     */
    boolean createNodeAndData (CreateMode mode, String path , String nodeData) ;
    /**
     * 获取节点数据
     */
    String getNodeData (String path) ;
    /**
     * 获取节点下数据
     */
    List<String> getNodeChild (String path) ;
    /**
     * 是否递归删除节点
     * @return
     */
    boolean deleteNode (String path, Boolean recursive) ;
    /**
     * 获取读写锁
     */
    InterProcessReadWriteLock getReadWriteLock (String path) ;
}

