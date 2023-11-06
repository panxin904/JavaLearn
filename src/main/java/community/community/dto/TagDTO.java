package community.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Coder on 2023/10/23 & 17:25.
 **/
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
