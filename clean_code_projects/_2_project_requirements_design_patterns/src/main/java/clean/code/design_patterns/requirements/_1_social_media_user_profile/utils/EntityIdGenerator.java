package clean.code.design_patterns.requirements._1_social_media_user_profile.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EntityIdGenerator {
    private Integer userId;
    private Integer messageId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public Integer getNextUserId() {
        return ++userId;
    }

    public Integer getNextMessageId() {
        return ++messageId;
    }
}
