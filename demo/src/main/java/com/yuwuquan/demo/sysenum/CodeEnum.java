package com.yuwuquan.demo.sysenum;

/**
 * 验证码类型在redis的前缀
 */
public enum CodeEnum {

    VERIFICATION_CODE_KEY_PRE(1,"phone_code_","用户验证码的key前缀",60*3),
    MODIFY_PASSWORD_KEY_PRE(2,"modify_password_code_","修改密码的key前缀",60*1),
    SESSION_KEY_PRE(3,"session_","",60*60*24*7),
    ;
    private Integer code;
    private String value;
    private String desc;
    private long time;//秒为单位

    CodeEnum(Integer code, String value, String desc, long time) {
        this.code = code;
        this.value = value;
        this.desc = desc;
        this.time = time;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public long getTime() {
        return time;
    }
    public static CodeEnum getCodeEnumByCode(Integer code) {
        for (CodeEnum c : CodeEnum.values()) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        return null;
    }
}
