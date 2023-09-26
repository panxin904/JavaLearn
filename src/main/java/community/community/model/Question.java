package community.community.model;

import lombok.Data;

/**
 * Created by Coder on 2023/9/23 & 15:05.
 **/
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer likeCount;
    private Integer commentCount;
    private String tag;
}
