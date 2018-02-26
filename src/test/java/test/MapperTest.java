package test;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springmvc.mapper.system.UserMapper;
import com.baomidou.springmvc.model.system.User;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath:spring/spring.xml"})
public class MapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void insertUser() throws Exception {
        for (int i = 0; i < 100; i++) {
            User u = new User();
            u.setAge(RandomUtils.nextInt(30, 99));
            u.setName("test-" + RandomUtils.nextInt(200, 300));
            userMapper.insert(u);
        }
    }

    @Test
    public void SelectUserByParam() throws Exception {
        Page<User> userPage = new Page<>(1, 10);
        userMapper.selectPage(userPage,
                new EntityWrapper<User>().between("age", "30", "60"));
        List<User> users = userPage.getRecords();
        System.out.println("user between 30 and 60:" + users);
        System.out.println("current page:" + userPage.getCurrent() + ",total count:" + userPage.getTotal());

    }
}
