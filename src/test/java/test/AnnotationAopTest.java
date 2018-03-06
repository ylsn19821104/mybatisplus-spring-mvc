package test;

import com.baomidou.springmvc.aop_annotation.dao.TestDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.baomidou.springmvc.aop_annotation"})
public class AnnotationAopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AnnotationAopTest.class);
        TestDao testDao = context.getBean(TestDao.class);
        testDao.insert();
    }
}
