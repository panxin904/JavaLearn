package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/10/11 & 9:41.
 **/
@Data
public class CommentDTO {
    private Long parentId;
    private Integer type;
    private String content;
}
