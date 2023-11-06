package community.community.dto;

import community.community.model.User;
import lombok.Data;

/**
 * Created by Coder on 2023/9/25 & 21:05.
 **/
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String tag;
    private User user;
}
