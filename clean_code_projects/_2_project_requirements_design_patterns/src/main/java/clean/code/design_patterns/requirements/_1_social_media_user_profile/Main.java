package clean.code.design_patterns.requirements._1_social_media_user_profile;

import clean.code.design_patterns.requirements._1_social_media_user_profile.utils.EntityIdGenerator;
import clean.code.design_patterns.requirements._1_social_media_user_profile.entity.User;
import clean.code.design_patterns.requirements._1_social_media_user_profile.service.MessageService;
import clean.code.design_patterns.requirements._1_social_media_user_profile.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, User> users = new HashMap<>();
    private static final EntityIdGenerator entityIdGenerator = new EntityIdGenerator(0, 0);
    private static final UserService userService = new UserService(scanner, users, entityIdGenerator);
    private static final MessageService messageService = new MessageService(scanner, users, entityIdGenerator, userService);

    public static void main(String[] args) {
        try {
            initializeUsers();
            showMenu();
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void initializeUsers() {
        users.put(entityIdGenerator.getNextUserId(), User.builder()
              .id(entityIdGenerator.getUserId())
              .name("John")
              .email("john@email.com")
              .mobile("1234567890")
              .city("San Francisco")
              .job("Software Engineer")
              .profileImageUrl("https://app.com/john.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Reading", "Gaming"})
              .build());
        users.put(entityIdGenerator.getNextUserId(), User.builder()
              .id(entityIdGenerator.getUserId())
              .name("Jane")
              .email("jane@email.com")
              .mobile("2345678901")
              .city("Berlin")
              .job("UX Designer")
              .profileImageUrl("https://app.com/jane.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Drawing", "Painting"})
              .build());
        users.put(entityIdGenerator.getNextUserId(), User.builder()
              .id(entityIdGenerator.getUserId())
              .name("Bob")
              .email("bob@email.com")
              .mobile("3456789012")
              .city("New York")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/bob.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Cooking", "Traveling"})
              .build());
        users.put(entityIdGenerator.getNextUserId(), User.builder()
              .id(entityIdGenerator.getUserId())
              .name("Dave")
              .email("dave@email.com")
              .mobile("4567890123")
              .city("Boston")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/dave.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Sports", "Photography"})
              .build());
        users.put(entityIdGenerator.getNextUserId(), User.builder()
              .id(entityIdGenerator.getUserId())
              .name("Eve")
              .email("eve@email.com")
              .mobile("5678901234")
              .city("Seattle")
              .job("Project Manager")
              .profileImageUrl("https://app.com/eve.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Hiking", "Coding"})
              .build());
    }

    public static void showMenu() {
        while (true) {
            log.info("-------------------------");
            log.info("1. Manage users");
            log.info("2. Manage messages");
            log.info("3. Quit");
            log.info("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: userService.manageUsers(); break;
                case 2: messageService.manageMessages(); break;
                case 3: log.info("Thanks for using the application!"); return;
                default: log.info("Invalid option {}", option);
            }
        }
    }
}
