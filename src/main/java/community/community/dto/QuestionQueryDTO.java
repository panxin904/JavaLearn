package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/11/6 & 14:25.
 **/
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
