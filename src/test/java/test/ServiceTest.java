package test;

import com.baomidou.springmvc.listener.MyApplicationListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring/spring.xml"})
public class ServiceTest {
    @Autowired
    MyApplicationListener myApplicationListener;

    @Test
    public void test() {
        System.err.println(myApplicationListener.getName());
    }
}
