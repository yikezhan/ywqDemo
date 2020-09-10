package com.yuwuquan.demo.controller.aichatmessage.answerdict;

public enum FixAnswerDictEnum {
    Default ("","叽叽哇哇说些什么鸟语，听不懂，再见！"),
    HELLO("你好","你好~"),
    Name("名字","大白!"),
    JOKE("讲个笑话","你真帅"),
    OnlyOne("就这?","咋地？"),
    Bye ("再见","期待下次为您服务");
    private String received;
    private String answer;

    static final String name = "@大白 ";
    FixAnswerDictEnum(String received, String answer) {
        this.received = received;
        this.answer = answer;
    }

    public String getReceived() {
        return received;
    }

    public String getAnswer() {
        return answer;
    }

    public static String getEnumByCode(String received){
        for(FixAnswerDictEnum answerDictEnum : values()){
            if((name+answerDictEnum.getReceived()).equals(received)){
                return answerDictEnum.answer;
            }
        }
        return null;
    }
}
