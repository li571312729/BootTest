package com.baili.test;

import com.baili.common.SpringBootTestCase;
import com.hefu.admin.entity.ProjectInformation;
import com.hefu.admin.service.ProjectInformationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xiaoqiangli
 * @Date 2022-02-14
 */
@Slf4j
public class ProjectTest extends SpringBootTestCase {

    @Autowired
    private ProjectInformationService projectInformationService;

    private static String[] projectType = new String[]{"居住建筑", "公共建筑", "工业建筑", "农业建筑"};

    @Test
    public void projectAddTest() throws InterruptedException {
        Map<String, Byte> map = new HashMap<>(10000000);
        List<ProjectInformation> lists = new ArrayList<>(10000000);

        for (int i = 0; i < 10000000; i++) {
            ProjectInformation projectInformation = new ProjectInformation();
            String projectName = null;
            while (true) {
                projectName = getRandomJianHan(ThreadLocalRandom.current().nextInt(3, 8));
                if (map.containsKey(projectName)) {
                    continue;
                }

                map.put(projectName, (byte) 1);
                break;
            }

            projectInformation.setProjectName(projectName);
            projectInformation.setProjectType(projectType[ThreadLocalRandom.current().nextInt(4)]);
            double v = ThreadLocalRandom.current().nextDouble(30, 1000);
            v = (int) (v * 100);
            projectInformation.setProjectCost(Double.toString(v / 100));
            double d = ThreadLocalRandom.current().nextDouble(30, 1000);
            d = (int) (d * 100);
            projectInformation.setProjectAera(d / 100 + "");
            projectInformation.setCreateTime(LocalDateTime.now());
            lists.add(projectInformation);
            log.info("number:{}, entinty:{}", i, projectInformation);
        }

        boolean b = projectInformationService.saveBatch(lists, 10000000);
        log.info(Thread.currentThread().getName() + "-> end");

    }

    //自动生成名字（中文）
    public static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位
            Random random = new Random();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

    //生成随机用户名，数字和字母组成,
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
