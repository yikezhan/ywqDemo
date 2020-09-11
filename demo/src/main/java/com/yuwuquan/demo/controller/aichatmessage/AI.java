package com.yuwuquan.demo.controller.aichatmessage;

import com.alibaba.fastjson.JSON;
import com.yuwuquan.demo.controller.aichatmessage.answerdict.FixAnswerDictEnum;
import com.yuwuquan.demo.util.common.StringUtil;
import lombok.Data;

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
        ChatMessage chatMessage = new ChatMessage(28L,"大白",context);
        return new AIAnswer(JSON.toJSONString(chatMessage));
    }
    @Data
    private static class ChatMessage {
        Long    senderUserId;
        String  senderNickname;
        String  content;

        public ChatMessage() {
        }

        public ChatMessage(Long senderUserId, String senderNickname, String content) {
            this.senderUserId = senderUserId;
            this.senderNickname = senderNickname;
            this.content = content;
        }
    }
}
