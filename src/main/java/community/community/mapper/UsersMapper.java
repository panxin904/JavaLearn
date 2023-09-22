package community.community.mapper;

import community.community.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Coder on 2023/9/22 & 13:02.
 **/
@Mapper
public interface UsersMapper {
    @Insert("insert into USERS (name, account_id, token, gmt_create, gmt_modified) values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    void insert(Users users);
}
