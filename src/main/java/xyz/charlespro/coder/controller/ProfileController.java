package xyz.charlespro.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.charlespro.coder.pojo.Profile;
import xyz.charlespro.coder.service.ProfileService;
import xyz.charlespro.coder.utils.FileUploadUtils;
import xyz.charlespro.coder.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ProfileController {
    @Autowired
    FileUploadUtils fileUploadUtils;
    @Autowired
    ProfileService profileService;
    @PostMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile uploadFile, String token, HttpServletRequest req) {
        Map<String,Object> resMap = new HashMap<>();
        System.out.println(token);
        Integer id = (Integer) JwtUtils.verifyJwt(token).get("userId");
        String path = fileUploadUtils.fileHandler(uploadFile, req);
        if (path.equals("上传失败! ")){
            resMap.put("success",false);
            resMap.put("msg","上传失败");
        }
        else{
            if (profileService.getProfileByUserId(id)!=null){
                Profile profile = profileService.getProfileByUserId(id);
                profile.setFileUrl(path);
                resMap.put("success",profileService.uploadProfile(profile));
            }
            else{
                Profile profile = new Profile();
                profile.setUserId(id);
                profile.setFileUrl(path);
                profile.setIsPublic(true);
                profile.setId(0);
                resMap.put("success",profileService.addProfile(profile));
            }
            resMap.put("msg","上传成功");
        }
        return resMap;
    }
    @ResponseBody
    @RequestMapping("/download")
    public Map<String,Object> download(@RequestBody Map<String,Object> userInfo){
        Profile profile = profileService.getProfileByUserId((Integer) userInfo.get("userId"));
        Map<String,Object> resMap = new HashMap<>();
        if (profile==null){
            resMap.put("success",false);
        }
        else {
            resMap.put("success",true);
            resMap.put("path",profile.getFileUrl());
        }
        return resMap;
    }
}
