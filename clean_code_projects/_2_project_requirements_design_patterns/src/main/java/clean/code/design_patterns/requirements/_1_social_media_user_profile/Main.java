package clean.code.design_patterns.requirements._1_social_media_user_profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static Integer userId = 0;
    private static Integer messageId = 0;
    private static Integer option;

    public static void main(String[] args) {
        try {
            initializeUsers();
            showMenu();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void initializeUsers() {
        users.add(User.builder()
              .id(++userId)
              .name("John")
              .email("john@email.com")
              .mobile("1234567890")
              .city("San Francisco")
              .job("Software Engineer")
              .profileImageUrl("https://app.com/john.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Reading", "Gaming"})
              .build());
        users.add(User.builder()
              .id(++userId)
              .name("Jane")
              .email("jane@email.com")
              .mobile("2345678901")
              .city("Berlin")
              .job("UX Designer")
              .profileImageUrl("https://app.com/jane.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Drawing", "Painting"})
              .build());
        users.add(User.builder()
              .id(++userId)
              .name("Bob")
              .email("bob@email.com")
              .mobile("3456789012")
              .city("New York")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/bob.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Cooking", "Traveling"})
              .build());
        users.add(User.builder()
              .id(++userId)
              .name("Dave")
              .email("dave@email.com")
              .mobile("4567890123")
              .city("Boston")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/dave.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Sports", "Photography"})
              .build());
        users.add(User.builder()
              .id(++userId)
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

    private static void showMenu() {
        do {
            log.info("-------------------------");
            log.info("1. Manage users");
            log.info("2. Manage messages");
            log.info("3. Quit");
            log.info("Select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1: manageUsers(); break;
                case 2: manageMessages(); break;
                case 3: log.info("Thanks for using the application!"); break;
                default: log.info("Invalid option {}", option); break;
            }
        } while (option != 3);
    }

    private static void manageUsers() {
        do {
            log.info("-------------------------");
            log.info("1. Show all users");
            log.info("2. Save new user");
            log.info("3. Search user");
            log.info("4. Update user");
            log.info("5. Delete user");
            log.info("6. Go Back");
            log.info("Select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1: displayAllUsers(); break;
                case 2: saveUser(); break;
                case 3: displayUser(); break;
                case 4: updateUser(); break;
                case 5: deleteUser(); break;
                case 6: showMenu(); return;
                default: log.info("Invalid option {}", option); break;
            }
        } while (true);
    }

    private static void displayAllUsers() {
        try {
            log.info("");
            if (users.isEmpty()) {
                log.info("There are no users to display");
                return;
            }
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                log.info(iterator.next().toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void saveUser() {
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
            boolean userExists = users.stream().anyMatch(user -> name.equals(user.getName()) || email.equals(user.getEmail()));
            if (userExists) {
                log.info("There is already a user with the name / email specified");
                return;
            }
            users.add(User.builder()
                  .id(++userId)
                  .name(name)
                  .email(email)
                  .mobile(getProperty("mobile").orElse(null))
                  .city(getProperty("city").orElse(null))
                  .job(getProperty("job").orElse(null))
                  .profileImageUrl(getProperty("profile image url").orElse(null))
                  .hasDrivingLicence(getHasDrivingLicense().orElse(null))
                  .hobbies(getHobbies().orElse(null))
                  .build());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Optional<String> getProperty(String property) {
        scanner.nextLine();
        log.info("Do you want to introduce your {}? (y/n) ", property);
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.next().trim()) : Optional.empty();
    }

    private static Optional<Boolean> getHasDrivingLicense() {
        scanner.nextLine();
        log.info("Do you want to give details about driving licence? (y/n) ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            log.info("Do you have a driving licence? (y/n) ");
            return Optional.of(scanner.nextLine().trim().equalsIgnoreCase("y"));
        }
        return Optional.empty();
    }

    private static Optional<String[]> getHobbies() {
        scanner.nextLine();
        log.info("Do you want to introduce your hobbies? (y/n) ");
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.nextLine().trim().split(", ")) : Optional.empty();
    }

    private static void displayUser() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            log.info("User found: {}", filteredUser.get());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void updateUser() {
        try {
            Optional<User> filteredUser = getUserByName();
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
            log.info("User {} was updated successfully", user.getName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void updateProperty(String property, Consumer<String> setter) {
        log.info("Enter new {} value: ", property);
        String propertyValue = scanner.nextLine().trim();
        if (!propertyValue.equals("")) {
            setter.accept(propertyValue);
        }
    }

    private static void deleteUser() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            User user = filteredUser.get();
            users.remove(user);
            log.info("User {} was deleted successfully", user.getName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Optional<User> getUserByName() {
        scanner.nextLine();
        log.info("Enter name: ");
        String name = scanner.nextLine().trim();
        if (name.equals("")) {
            log.info("The name must not be empty");
            return Optional.empty();
        }
        Optional<User> filteredUser = users.stream().filter(user -> name.equals(user.getName())).findFirst();
        if (filteredUser.isEmpty()) {
            log.info("There is no user named {} in the application", name);
            return Optional.empty();
        }
        return filteredUser;
    }

    private static void manageMessages() {
        do {
            log.info("-------------------------");
            log.info("1. Show all messages");
            log.info("2. Send a message");
            log.info("3. Go back");
            log.info("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: displayAllMessages(); break;
                case 2: sendMessage(); break;
                case 3: showMenu(); return;
                default: log.info("Invalid option {}", option); break;
            }
        } while (true);
    }

    private static void displayAllMessages() {
        try {
            log.info("");
            List<Message> messages = new ArrayList<>();
            Iterator<User> userIterator = users.iterator();
            while (userIterator.hasNext()) {
                messages.addAll(userIterator.next().getMessages());
            }
            if (messages.isEmpty()) {
                log.info("There are no messages to display");
                return;
            }
            Iterator<Message> messageIterator = messages.iterator();
            while (messageIterator.hasNext()) {
                log.info(messageIterator.next().toString());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void sendMessage() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            log.info("Write your message here: ");
            String text = scanner.nextLine().trim();
            if (text.equals("")) {
                log.info("The message must not be empty");
                return;
            }
            Message message = Message.builder().id(++messageId).text(text).build();
            User user = filteredUser.get();
            List<Message> messages = user.getMessages();
            messages.add(message);
            user.setMessages(messages);
            log.info("Message sent to {} successfully", user.getName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
