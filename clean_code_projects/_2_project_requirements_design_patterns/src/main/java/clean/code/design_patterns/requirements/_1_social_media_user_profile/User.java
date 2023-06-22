package clean.code.design_patterns.requirements._1_social_media_user_profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String mobile;
    private String city;
    private String job;
    private String profileImageUrl;
    private Boolean hasDrivingLicence;
    private String[] hobbies;
    private List<Message> messages;

    public User(UserBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.mobile = builder.mobile;
        this.city = builder.city;
        this.job = builder.job;
        this.profileImageUrl = builder.profileImageUrl;
        this.hasDrivingLicence = builder.hasDrivingLicence;
        this.hobbies = builder.hobbies;
        this.messages = builder.messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Boolean getHasDrivingLicence() {
        return hasDrivingLicence;
    }

    public void setHasDrivingLicence(Boolean hasDrivingLicence) {
        this.hasDrivingLicence = hasDrivingLicence;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id +
              ", name='" + name + '\'' +
              ", email='" + email + '\'' +
              ", mobile='" + mobile + '\'' +
              ", city='" + city + '\'' +
              ", job='" + job + '\'' +
              ", profileImageUrl='" + profileImageUrl + '\'' +
              ", hasDrivingLicence=" + hasDrivingLicence +
              ", hobbies=" + Arrays.toString(hobbies) +
              ", messages=" + messages + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
              name.equals(user.name) &&
              email.equals(user.email) &&
              mobile.equals(user.mobile) &&
              city.equals(user.city) &&
              job.equals(user.job) &&
              profileImageUrl.equals(user.profileImageUrl) &&
              hasDrivingLicence.equals(user.hasDrivingLicence) &&
              Arrays.equals(hobbies, user.hobbies) &&
              messages.equals(user.messages);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, email, mobile, city, job, profileImageUrl, hasDrivingLicence, messages);
        result = 31 * result + Arrays.hashCode(hobbies);
        return result;
    }

    public static UserBuilder builder(String name, String email) {
        return new UserBuilder(name, email);
    }

    static class UserBuilder {
        private Integer id;
        private String name;
        private String email;
        private String mobile;
        private String city;
        private String job;
        private String profileImageUrl;
        private Boolean hasDrivingLicence;
        private String[] hobbies;
        private final List<Message> messages;

        public UserBuilder(String name, String email) {
            this.name = name;
            this.email = email;
            this.messages = new ArrayList<>();
        }

        public UserBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public UserBuilder city(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder job(String job) {
            this.job = job;
            return this;
        }

        public UserBuilder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public UserBuilder hasDrivingLicence(Boolean hasDrivingLicence) {
            this.hasDrivingLicence = hasDrivingLicence;
            return this;
        }

        public UserBuilder hobbies(String[] hobbies) {
            this.hobbies = hobbies;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
