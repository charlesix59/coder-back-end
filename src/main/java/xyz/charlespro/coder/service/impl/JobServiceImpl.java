package xyz.charlespro.coder.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.mapper.JobMapper;
import xyz.charlespro.coder.mapper.UserJobMapper;
import xyz.charlespro.coder.pojo.Job;
import xyz.charlespro.coder.pojo.UserJob;
import xyz.charlespro.coder.service.JobService;

import javax.annotation.Resource;
import java.util.*;

@Service
public class JobServiceImpl implements JobService {
    @Autowired(required = false)
    JobMapper jobMapper;
    @Autowired(required = false)
    UserJobMapper userJobMapper;
    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Override
    public List<Job> getAllJobs() {
        List<Job> jobList;
        assert redisTemplate!=null;
        String cacheJobsJson = redisTemplate.opsForValue().get("jobList");
        if (cacheJobsJson!=null){
            return JSONArray.parseArray(cacheJobsJson,Job.class);
        }
        jobList = jobMapper.selectAll();
        redisTemplate.opsForValue().set("jobList", JSONArray.toJSONString(jobList));
        int len = jobList.size();
        if(len>9){
            jobList.subList(0,9);
        }
        return jobList;
    }

    @Override
    public Job getJobById(int id) {
        return jobMapper.getJobById(id);
    }

    @Override
    public List<Job> getJobsByCondition(Job job){
        return jobMapper.getJobByCondition(job);
    }
    public List<Job> getJobsByConditionByPage(Job job,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        return jobMapper.getJobByCondition(job);
    }

    @Override
    public List<Job> getJobsByName(String name) {
        return jobMapper.getJobsByName(name);
    }

    @Override
    public List<Job> getJobsByRecruitNumber(Integer min) {
        return jobMapper.getJobsByRecruitNumber(min);
    }

    @Override
    public List<Job> getJobsByExperienceRequirement(String requirement) {
        return jobMapper.getJobsByExperienceRequirement(requirement);
    }

    @Override
    public List<Job> getJobsByEducationRequirement(String requirement) {
        return jobMapper.getJobsByEducationRequirement(requirement);
    }

    @Override
    public List<Job> getJobsByTag(String tag) {
        return jobMapper.getJobsByTag(tag);
    }

    @Override
    public List<Job> getJobsByFirstCategories(String categories) {
        return jobMapper.getJobsByFirstCategories(categories);
    }

    @Override
    public List<Job> getJobsBySecondCategories(String categories) {
        return jobMapper.getJobsBySecondCategories(categories);
    }

    @Override
    public boolean setJobState(UserJob userJob) {
        Map<String,Integer> eqMap = new HashMap<>();
        eqMap.put("user_id",userJob.getUserId());
        eqMap.put("job_id",userJob.getJobId());
        return userJobMapper.update(userJob,new UpdateWrapper<UserJob>().allEq(eqMap))>0;
    }

    @Override
    public boolean applyJob(Integer userId,Integer jobId) {
        UserJob userJob = new UserJob(userId,jobId,"applying",false,"",new Date());
        UserJob dbData = userJobMapper.selectOne(new QueryWrapper<UserJob>().eq("user_id",userId).eq("job_id",jobId));
        if (dbData!=null){
            System.out.println(dbData);
            return false;
        }
        return userJobMapper.insert(userJob)>0;
    }

    @Override
    public boolean cancelApplication(Integer userId,Integer jobId) {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("userId",userId);
        queryMap.put("jobId",jobId);
        UserJob userJob = userJobMapper.selectOne(new QueryWrapper<UserJob>().allEq(queryMap));
        if (userJob==null){
            return false;
        }
        userJob.setCancel(true);
        return userJobMapper.updateById(userJob)>0;
    }

    @Override
    public boolean addNewJob(Job job) {
        return jobMapper.insertJob(job)>0;
    }

    @Override
    public boolean modifyJob(Job job) {
        return jobMapper.updateJob(job)>0;
    }

    @Override
    public boolean deleteJob(int id) {
        return jobMapper.deleteJob(id)>0;
    }

    @Override
    public List<Map<String, Object>> getAllApplication(Integer id) {
        List<UserJob> applications = userJobMapper.selectList(new QueryWrapper<UserJob>().eq("user_id", id));
        if (applications==null){
            return null;
        }
        List<Map<String,Object>> res = new ArrayList<>();
        for(UserJob application : applications){
            if(application.isCancel()){
                continue;
            }
            Job job = jobMapper.getJobById(application.getJobId());
            if (job==null){
                continue;
            }
            Map<String,Object> applyInfo = new HashMap<>();
            applyInfo.put("jobName",job.getName());
            applyInfo.put("description",job.getDescription());
            applyInfo.put("state",application.getState());
            applyInfo.put("id",job.getId());
            res.add(applyInfo);
        }
        return res;
    }
    @Override
    public List<Map<String,Object>> getALlApplicationsSubmitted(Integer id){
        List<Map<String,Object>> res = new ArrayList<>();
        List<Job> jobs = jobMapper.getJobByCreatorId(id);
        for(Job job : jobs){
            List<UserJob> applications = userJobMapper.selectList(new QueryWrapper<UserJob>().eq("job_id",job.getId()));
            for(UserJob application : applications){
                if(!application.getState().equals("applying")||application.isCancel()){
                    continue;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("job",job);
                map.put("application",application);
                res.add(map);
            }
        }
        return res;
    }
}
