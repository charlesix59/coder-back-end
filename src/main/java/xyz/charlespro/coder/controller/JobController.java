package xyz.charlespro.coder.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.charlespro.coder.pojo.Job;
import xyz.charlespro.coder.service.JobService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    JobService jobService;
    @ResponseBody
    @RequestMapping("/get/all")
    public Map<String, Object> getAllJobs(){
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("data",jobService.getAllJobs());
        resMap.put("success",jobService==null);
        return resMap;
    }
    @ResponseBody
    @RequestMapping("/get/one")
    public Map<String, Object> getJobById(@RequestBody Map<String,Object> jobInfo){
        System.out.println(jobInfo);
        String idStr = (String) jobInfo.get("id");
        Integer id = Integer.valueOf(idStr);
        System.out.println(id);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("data",jobService.getJobById(id));
        resMap.put("success",jobService==null);
        return resMap;
    }
    /**
     * 此方法用于处理按名称查找job的请求
     * @param info job对象和pageNum、pageSize
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("/search")
    public Map<String, Object> getJobByCondition(@RequestBody Map<String,Object> info){
        int pageNum = Integer.parseInt(info.get("pageNum")!=null? (String) info.get("pageNum") :"0");
        int pageSize = 5;
        String name = null;
        String des = null;
        String experienceRequirement = null;
        String educationRequirement = null;
        String firstCategories = null;
        String secondCategories = null;
        String tag = null;
        try{
            name = (String) info.get("name");
            des = (String)info.get("description");
            educationRequirement = (String) info.get("educationRequirement");
            experienceRequirement = (String) info.get("experienceRequirement");
            firstCategories = (String) info.get("firstCategories");
            secondCategories = (String) info.get("secondCategories");
            tag = (String) info.get("tag");
        }catch (Exception ignored){}
        Job job = new Job(null,0,name,null,des,null,experienceRequirement,educationRequirement,firstCategories,secondCategories,tag);
        System.out.println(pageNum+" "+pageSize);
        System.out.println(job);
        if(pageNum <= 0) {
            pageNum = 1;
        }
        Map<String,Object> res = new HashMap<>();
        List<Job> jobs = jobService.getJobsByConditionByPage(job,pageNum,pageSize);
        PageInfo<Job> pageInfo = new PageInfo<>(jobs,pageSize);
        res.put("success",!jobs.isEmpty());
        res.put("data",jobs);
        res.put("pageInfo",pageInfo);
        return res;
    }

    /**
     * 此方法用于处理按标签查找job的请求
     * @param tag job的tag属性
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("/get/tag")
    public String getJobByTag(String tag){
        return "tag";
    }

    /**
     * 此方法用于处理按招募编号查找job的请求
     * @param num 编号
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("get/recruit")
    public String getJobByRecruitNumber(Integer num){
        return null;
    }

    /**
     * 此方法用于处理按工作经验要求查找job的请求
     * @param requirement 工作经验要求
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("get/experience")
    public String getJobByExperienceRequirement(String requirement){
        return null;
    }

    /**
     * 此方法用于处理按学历要求查找job的请求
     * @param requirement 学历要求
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("get/education")
    public String getJobByEducationRequirement(String requirement){
        return null;
    }

    /**
     * 此方法用于处理按第一分类查找job的请求
     * @param categories 第一分类
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("get/categories/first")
    public String getJobByFirstCategories(String categories){
        return "first";
    }

    /**
     * 此方法用于处理按第二分类查找job的请求
     * @param categories 第二分类
     * @return success：查找是否成功
     */
    @ResponseBody
    @RequestMapping("get/categories/second")
    public String getJobBySecondCategories(String categories){
        return "second";
    }
}
