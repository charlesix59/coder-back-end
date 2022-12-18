package xyz.charlespro.coder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.charlespro.coder.mapper.JobMapper;
import xyz.charlespro.coder.mapper.UserMapper;
import xyz.charlespro.coder.pojo.Company;
import xyz.charlespro.coder.pojo.Job;
import xyz.charlespro.coder.pojo.User;
import xyz.charlespro.coder.service.UserService;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class MybaitsPlusTest {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelect(){
        System.out.println(("----- selectAll method test ------"));
        System.out.println(userMapper.selectList(null));
    }
}