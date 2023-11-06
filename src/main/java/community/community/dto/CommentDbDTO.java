package community.community.dto;

import community.community.model.User;
import lombok.Data;

/**
 * Created by Coder on 2023/10/18 & 14:58.
 **/
@Data
public class CommentDbDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;

}
