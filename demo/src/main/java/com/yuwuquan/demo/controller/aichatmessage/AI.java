
//人类特征
//1、有擅长领域，有知有不知，不是全能
//2、有情绪，有性格
//3、懂聆听，理解讲的故事，能判断事情性质，理解上下文语义。
package com.yuwuquan.demo.controller.aichatmessage;

import com.alibaba.fastjson.JSON;
import com.yuwuquan.demo.controller.aichatmessage.answerdict.FixAnswerDictEnum;
import com.yuwuquan.demo.util.common.StringUtil;
import lombok.Data;

import java.util.List;

public class AI {
    private static final String name = "@小棕 ";
    private static final long moodScore = 100;
    private static final List<String> expert = null;
    private  PortraitContext portraitContext;
    private static boolean check(String received) {
        if(received == null || !received.startsWith(AI.name)){
            return false;
        }
        return true;
    }

    private static String getFixAnswer(String received) {
        return FixAnswerDictEnum.getEnumByCode(name, received);
    }
    public static AIAnswer chat(String received){
        if(!check(received)){
            return new AIAnswer(true,"未通过校验");
        }
        String context = getFixAnswer(received);

        if(StringUtil.isBlank(context)){
            return new AIAnswer(true,"回答为空");
        }
        return new AIAnswer(JSON.toJSONString(new ChatMessage(context)));
    }
    @Data
    private static class ChatMessage {
        private static Long defaultSenderUserId = 28L;
        private static String defaultSenderNickname = AI.name;
        private Long  senderUserId;
        private String  senderNickname;
        private String  content;

        public ChatMessage(Long senderUserId, String senderNickname, String content) {
            this.senderUserId = senderUserId;
            this.senderNickname = senderNickname;
            this.content = content;
        }
        public ChatMessage() {
            this(defaultSenderUserId,defaultSenderNickname,"");
        }

        public ChatMessage(String content) {
            this(defaultSenderUserId,defaultSenderNickname,content);
        }
    }
    @Data
    class PortraitContext {
        String name;
    }
}
