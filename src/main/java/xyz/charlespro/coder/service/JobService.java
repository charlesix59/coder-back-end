package xyz.charlespro.coder.service;

import xyz.charlespro.coder.pojo.Job;
import xyz.charlespro.coder.pojo.UserJob;

import java.util.List;
import java.util.Map;

public interface JobService {
    //Fuzzy matching according to name
    List<Job> getAllJobs();
    Job getJobById(int id);
    List<Job> getJobsByCondition(Job job);
    List<Job> getJobsByConditionByPage(Job job,Integer pageNum,Integer pageSize);

    List<Job> getJobsByName(String name);
    List<Job> getJobsByRecruitNumber(Integer min);
    List<Job> getJobsByExperienceRequirement(String requirement);
    List<Job> getJobsByEducationRequirement(String requirement);
    List<Job> getJobsByTag(String tag);
    List<Job> getJobsByFirstCategories(String categories);
    List<Job> getJobsBySecondCategories(String categories);
    //(hr)set state of User's apply for job and send mail for him
    boolean setJobState(UserJob userJob);
    boolean applyJob(Integer userId,Integer jobId);
    boolean cancelApplication(Integer userId,Integer jobId);
    boolean addNewJob(Job job);
    boolean modifyJob(Job job);
    boolean deleteJob(int id);
    List<Map<String, Object>> getAllApplication(Integer id);
    List<Map<String,Object>> getALlApplicationsSubmitted(Integer id);

}
