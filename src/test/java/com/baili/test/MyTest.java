package com.baili.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.baili.common.SpringBootTestCase;
import com.hefu.zookeeper.service.ZookeeperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Slf4j
public class MyTest extends SpringBootTestCase {

    @Autowired
    DruidDataSource druidDataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private ZookeeperService zookeeperService ;

    @Autowired
    DataSource dataSource;

    @Test
    public void test(){
        if(!zookeeperService.isExistNode("/hefuzoo")){
            System.out.println("节点不存在");
        }else{
            System.out.println("节点存在");
        }
    }

    @Test
    public void test2(){
        System.out.println(1);
    }


}
