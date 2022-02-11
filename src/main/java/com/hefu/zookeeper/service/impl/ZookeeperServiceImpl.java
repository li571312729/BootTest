package com.hefu.zookeeper.service.impl;

import com.hefu.common.utils.StringUtils;
import com.hefu.zookeeper.config.ZookeeperConfig;
import com.hefu.zookeeper.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ZookeeperServiceImpl implements ZookeeperService {

    @Override
    public boolean isExistNode(String path) {
        CuratorFramework client = ZookeeperConfig.getClient();
        client.sync() ;
        try {
            Stat stat = client.checkExists().forPath(path);
            return client.checkExists().forPath(path) != null;
        } catch (Exception e) {
            log.error("isExistNode error...", e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createNode(CreateMode mode, String path) {
        boolean result = false;
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 递归创建所需父节点
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
            result = true;
        } catch (Exception e) {
            log.error("createNode error...", e);
        }
        return result;
    }

    @Override
    public boolean setNodeData(String path, String nodeData) {
        boolean result = false;
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 设置节点数据
            client.setData().forPath(path, nodeData.getBytes("UTF-8"));
            result = true;
        } catch (Exception e) {
            log.error("setNodeData error...", e);
        }
        return result;
    }

    @Override
    public boolean createNodeAndData(CreateMode mode, String path, String nodeData) {
        boolean result = false;
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 创建节点，关联数据
            client.create().creatingParentsIfNeeded().withMode(mode)
                    .forPath(path,nodeData.getBytes("UTF-8"));
            result = true;
        } catch (Exception e) {
            log.error("createNode error...", e);
        }
        return result;
    }

    @Override
    public String getNodeData(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            // 数据读取和转换
            byte[] dataByte = client.getData().forPath(path) ;
            String data = new String(dataByte,"UTF-8") ;
            if (StringUtils.isNotEmpty(data)){
                return data ;
            }
        }catch (Exception e) {
            log.error("getNodeData error...", e);
        }
        return null;
    }

    @Override
    public List<String> getNodeChild(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        List<String> nodeChildDataList = new ArrayList<>();
        try {
            // 节点下数据集
            nodeChildDataList = client.getChildren().forPath(path);
        } catch (Exception e) {
            log.error("getNodeChild error...", e);
        }
        return nodeChildDataList;
    }

    @Override
    public boolean deleteNode(String path, Boolean recursive) {
        boolean result = false;
        CuratorFramework client = ZookeeperConfig.getClient() ;
        try {
            if(recursive) {
                // 递归删除节点
                client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            } else {
                // 删除单个节点
                client.delete().guaranteed().forPath(path);
            }
            result = true;
        } catch (Exception e) {
            log.error("deleteNode error...", e);
        }
        return result;
    }

    @Override
    public InterProcessReadWriteLock getReadWriteLock(String path) {
        CuratorFramework client = ZookeeperConfig.getClient() ;
        // 写锁互斥、读写互斥
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, path);
        return readWriteLock ;
    }
}
