package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/9/17 & 19:33.
 **/
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
