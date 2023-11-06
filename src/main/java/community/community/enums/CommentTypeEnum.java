package community.community.enums;

/**
 * Created by Coder on 2023/10/16 & 10:34.
 **/
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    public Integer type;

    CommentTypeEnum(int type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
    public static boolean isExist(Integer type){
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
