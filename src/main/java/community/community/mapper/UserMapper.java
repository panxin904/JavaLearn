package community.community.mapper;

import community.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Coder on 2023/9/22 & 13:02.
 **/
@Mapper
public interface UserMapper {
    @Insert("insert into USERS (name, account_id, token, gmt_create, gmt_modified) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(User user);
    @Select("select * from USERS where token = #{token}")
    User findByToken(@Param("token") String token);
}
