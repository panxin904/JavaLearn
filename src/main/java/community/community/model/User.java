package community.community.model;

import lombok.Data;

/**
 * Created by Coder on 2023/9/22 & 13:07.
 **/
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
