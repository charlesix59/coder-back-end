package xyz.charlespro.coder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.charlespro.coder.pojo.Profile;

@Mapper
public interface ProfileMapper extends BaseMapper<Profile> {
}
