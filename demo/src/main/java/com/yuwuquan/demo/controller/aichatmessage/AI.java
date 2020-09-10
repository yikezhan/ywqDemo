package com.yuwuquan.demo.controller.aichatmessage;

import com.yuwuquan.demo.controller.aichatmessage.answerdict.FixAnswerDictEnum;
import com.yuwuquan.demo.util.common.StringUtil;

public class AI {
    private static final String name = "";
    private static boolean check(String received) {
        if(received == null || !received.startsWith("@大白 ")){
            return false;
        }
        return true;
    }

    private static String getFixAnswer(String received) {
        return FixAnswerDictEnum.getEnumByCode(received);
    }
    public static AIAnswer chat(String received){
        if(!check(received)){
            return new AIAnswer(true,"未通过校验");
        }
        String context = getFixAnswer(received);
        if(StringUtil.isBlank(context)){
            return new AIAnswer(true,"回答为空");
        }
        return new AIAnswer(context);
    }
}
