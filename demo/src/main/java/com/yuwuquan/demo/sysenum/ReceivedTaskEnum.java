package com.yuwuquan.demo.sysenum;

public enum ReceivedTaskEnum {
    ;
    public enum AnswerEnum{
        FALSE(0,"错误"),
        TRUE(1, "正确");
        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        private AnswerEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
    public enum CommitEnum{
        NoCommit(0,"未提交"),
        Commit(1, "已提交"),
        GiveUpCommit(2, "放弃");
        private Integer code;
        private String desc;

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        private CommitEnum(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
