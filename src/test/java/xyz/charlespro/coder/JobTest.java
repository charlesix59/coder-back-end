package xyz.charlespro.coder;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.charlespro.coder.mapper.JobMapper;
import xyz.charlespro.coder.pojo.Company;
import xyz.charlespro.coder.pojo.Job;

import java.util.List;

@SpringBootTest
public class JobTest {
    @Autowired
    JobMapper jobMapper;
    @Test
    public void testSelect(){
        List<Job> jobs = jobMapper.selectAll();
        jobs.forEach(System.out::println);
    }
}
