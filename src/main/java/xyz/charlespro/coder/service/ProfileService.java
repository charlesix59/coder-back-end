package xyz.charlespro.coder.service;

import xyz.charlespro.coder.pojo.Profile;
import xyz.charlespro.coder.pojo.User;

import java.util.Date;

public interface ProfileService {
    /**
     * 此方法用于修改isPublic属性
     * @param profile
     * @return 是否修改成功
     */
    boolean updateIsPublic(Profile profile);

    /**
     * 此方法用于新建新的简历
     *
     * @param profile
     * @return 新的简历类
     */
    boolean addProfile(Profile profile);

    /**
     * 此方法用于删除现有简历
     * @param profile
     * @return 是否删除成功
     */
    boolean remove(Profile profile);

    /**
     * 此方法用于查询用户的所有简历
     * @param id
     * @return 所有简历集合
     */
    Profile getProfileByUserId(Integer id);

    /**
     * 此方法用于按id查询单个简历
     * @param id
     * @return 简历
     */
    Profile getProfileById(int id);

    boolean uploadProfile(Profile profile);
}
