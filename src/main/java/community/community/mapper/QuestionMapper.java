package community.community.mapper;

import community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Coder on 2023/9/23 & 15:01.
 **/
@Mapper
public interface QuestionMapper {
    @Insert("insert into QUESTION (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);
    @Select("select * from QUESTION limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    @Select("select count(1) from QUESTION")
    Integer count();
    @Select("select * from QUESTION where creator = #{userId} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    @Select("select count(1) from QUESTION where creator = #{userId}")
    Integer countByUserId(@Param(value = "userId") Integer userId);
    @Select("select * from QUESTION where id = #{id}")
    Question getById(@Param(value = "id") Integer id);
}
