package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/9/17 & 20:48.
 **/
@Data
public class GithubUser {
    private String name;
    private Integer id;
    private String bio;
    private String avatarUrl;
}
