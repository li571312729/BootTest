package com.baili.common;

import com.hefu.BootServiceApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//启动Spring, webEnvironment用来给WebSocket提供一个测试的web环境
@SpringBootTest(classes = BootServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringBootTestCase {
}
