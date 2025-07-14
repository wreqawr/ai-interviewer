package cn.minglg.interview.mapper;

import cn.minglg.interview.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * ClassName:UserMapper
 * Package:cn.minglg.interview.mapper
 * Description:
 *
 * @Author kfzx-minglg
 * @Create 2025/6/13
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名获取用户（包含公司、角色、权限）
     *
     * @param userName 用户名
     * @return 用户详细信息
     */
    User getUserWithDetailsByUserName(@Param("userName") String userName);

    /**
     * 获取用户基本信息
     *
     * @param userName 用户名
     * @return 用户基本信息
     */
    User getBasicUserByUserName(@Param("userName") String userName);
}
