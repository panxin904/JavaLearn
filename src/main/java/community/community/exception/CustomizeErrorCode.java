package community.community.exception;

/**
 * Created by Coder on 2023/10/10 & 9:15.
 **/
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001, "当前寻找的问题丢失啦，换一个问题试试吧。"),
    NO_LOGIN(2002, "当前操作需要登陆，请登录后重试。"),
    SYS_ERROR(2003, "服务器忙，请稍后再试！"),
    TARGET_PARAM_NOT_FOUND(2004, "未选中任何问题或评论进行回复"),
    TYPE_NOT_FOUND(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "当前评论不存在，请换一个试试"),
    CONTENT_IS_BLANK(2007, "评论内容不能为空！！！"),
    READ_NOTIFICATION_FAIL(2008, "不支持读取别人的信息！"),
    NOTIFICATION_NOT_FOUND(2009, "当前寻找的消息丢失啦，换一个问题试试吧。"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败。"),
    ;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
