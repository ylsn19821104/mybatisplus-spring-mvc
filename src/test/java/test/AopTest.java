package test;

import com.baomidou.springmvc.aop_xml.dao.TestDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring/aop.xml"})
public class AopTest {
    @Autowired
    TestDao testDao;

    @Test
    public void aop() {
        testDao.insert();
    }
}
