package community.community.dto;

import lombok.Data;

/**
 * Created by Coder on 2023/10/24 & 19:56.
 **/
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Long outerid;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
