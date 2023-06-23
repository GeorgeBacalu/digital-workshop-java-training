package clean.code.design_patterns.requirements._1_social_media_user_profile.service;

import clean.code.design_patterns.requirements._1_social_media_user_profile.entity.User;
import clean.code.design_patterns.requirements._1_social_media_user_profile.utils.EntityIdGenerator;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final Scanner scanner;
    private final Map<Integer, User> users;
    private final EntityIdGenerator entityIdGenerator;

    public void manageUsers() {
        while (true) {
            log.info("-------------------------");
            log.info("1. Show all users");
            log.info("2. Save new user");
            log.info("3. Search user");
            log.info("4. Update user");
            log.info("5. Delete user");
            log.info("6. Go Back");
            log.info("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: displayAllUsers(); break;
                case 2: saveUser(); break;
                case 3:
                    log.info("Enter the id of the user to display: ");
                    displayUserById(scanner.nextInt());
                    break;
                case 4:
                    log.info("Enter the id of the user to update: ");
                    updateUserById(scanner.nextInt());
                    break;
                case 5:
                    log.info("Enter the id of the user to delete: ");
                    deleteUserById(scanner.nextInt()); break;
                case 6: return;
                default: log.info("Invalid option {}", option);
            }
        }
    }

    private void displayAllUsers() {
        try {
            log.info("");
            if (users.isEmpty()) {
                log.info("There are no users to display");
                return;
            }
            Iterator<Map.Entry<Integer, User>> iterator = users.entrySet().iterator();
            while (iterator.hasNext()) {
                log.info(iterator.next().getValue().toString());
            }
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    private void saveUser() {
        try {
            scanner.nextLine();
            log.info("Enter name: ");
            String name = scanner.nextLine().trim();
            log.info("Enter email: ");
            String email = scanner.nextLine().trim();
            if (name.equals("") || email.equals("")) {
                log.info("The name and email must not be empty");
                return;
            }
            boolean userExists = users.values().stream().anyMatch(user -> name.equals(user.getName()) || email.equals(user.getEmail()));
            if (userExists) {
                log.info("There is already a user with the name / email specified");
                return;
            }
            users.put(entityIdGenerator.getNextUserId(), User.builder()
                  .id(entityIdGenerator.getUserId())
                  .name(name)
                  .email(email)
                  .mobile(getProperty("mobile").orElse(null))
                  .city(getProperty("city").orElse(null))
                  .job(getProperty("job").orElse(null))
                  .profileImageUrl(getProperty("profile image url").orElse(null))
                  .hasDrivingLicence(getHasDrivingLicense().orElse(null))
                  .hobbies(getHobbies().orElse(null))
                  .build());
            log.info("User {} was saved successfully", name);
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    private Optional<String> getProperty(String property) {
        scanner.nextLine();
        log.info("Do you want to introduce your {}? (y/n) ", property);
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.next().trim()) : Optional.empty();
    }

    private Optional<Boolean> getHasDrivingLicense() {
        scanner.nextLine();
        log.info("Do you want to give details about driving licence? (y/n) ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            log.info("Do you have a driving licence? (y/n) ");
            return Optional.of(scanner.nextLine().trim().equalsIgnoreCase("y"));
        }
        return Optional.empty();
    }

    private Optional<String[]> getHobbies() {
        scanner.nextLine();
        log.info("Do you want to introduce your hobbies? (y/n) ");
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.nextLine().trim().split(", ")) : Optional.empty();
    }

    private void displayUserById(Integer id) {
        try {
            Optional<User> filteredUser = getUserById(id);
            if (filteredUser.isEmpty()) return;
            log.info("User found: {}", filteredUser.get());
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    private void updateUserById(Integer id) {
        try {
            Optional<User> filteredUser = getUserById(id);
            if (filteredUser.isEmpty()) return;
            User user = filteredUser.get();
            updateProperty("name", user::setName);
            updateProperty("email", user::setEmail);
            updateProperty("mobile", user::setMobile);
            updateProperty("city", user::setCity);
            updateProperty("job", user::setJob);
            updateProperty("profile image url", user::setProfileImageUrl);
            updateProperty("driving license status (taken / not taken)", newHasDrivingLicense -> user.setHasDrivingLicence(newHasDrivingLicense.trim().equalsIgnoreCase("taken")));
            updateProperty("hobbies", newHobbies -> user.setHobbies(newHobbies.trim().split(", ")));
            log.info("User with id {} was updated successfully", id);
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    private void updateProperty(String property, Consumer<String> setter) {
        log.info("Enter updated {} value: ", property);
        String updatedValue = scanner.nextLine().trim();
        if (!updatedValue.equals("")) {
            setter.accept(updatedValue);
        }
    }

    private void deleteUserById(Integer id) {
        try {
            Optional<User> filteredUser = getUserById(id);
            if (filteredUser.isEmpty()) return;
            users.remove(id);
            log.info("User with id {} was deleted successfully", id);
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    public Optional<User> getUserById(Integer id) {
        scanner.nextLine();
        User user = users.get(id);
        if(user == null) {
            log.info("There is no user with the id {} in the application", id);
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
