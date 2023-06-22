package clean.code.design_patterns.requirements._1_social_media_user_profile;

import java.util.Objects;

public class Message {
    private Integer id;
    private String text;

    public Message(MessageBuilder builder) {
        this.id = builder.id;
        this.text = builder.text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", text='" + text + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id) && text.equals(message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    static class MessageBuilder {
        private Integer id;
        private String text;

        public MessageBuilder() {}

        public MessageBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public MessageBuilder text(String text) {
            this.text = text;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
