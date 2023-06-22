package clean.code.design_patterns.requirements._1_social_media_user_profile;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
}
