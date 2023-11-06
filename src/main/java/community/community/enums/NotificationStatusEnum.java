package community.community.enums;

/**
 * Created by Coder on 2023/10/24 & 19:32.
 **/
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1)
    ;
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
