package community.community.mapper;

import community.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Coder on 2023/9/23 & 15:01.
 **/
@Mapper
public interface QuestionMapper {
    @Insert("insert into QUESTION (title, description, gmt_create, gmt_modified, creator, tag) values (#{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);
    @Select("select * from question")
    List<Question> list();
}
