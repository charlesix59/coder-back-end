package xyz.charlespro.coder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.charlespro.coder.pojo.Job;
import xyz.charlespro.coder.pojo.UserJob;

import java.util.List;

public interface JobMapper extends BaseMapper<Job> {
    List<Job> selectAll();
    int insertJob(Job job);
    int updateJob(Job job);
    int deleteJob(int id);
    Job getJobById(int id);
    List<Job> getJobByCondition(Job job);
    List<Job> getJobByCreatorId(Integer id);
    List<Job> getJobsByName(String name);
    List<Job> getJobsByRecruitNumber(Integer min);
    List<Job> getJobsByExperienceRequirement(String requirement);
    List<Job> getJobsByEducationRequirement(String requirement);
    List<Job> getJobsByTag(String tag);
    List<Job> getJobsByFirstCategories(String categories);
    List<Job> getJobsBySecondCategories(String categories);
}
