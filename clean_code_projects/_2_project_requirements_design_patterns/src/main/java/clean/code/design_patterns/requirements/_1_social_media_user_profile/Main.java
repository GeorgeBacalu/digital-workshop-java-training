package clean.code.design_patterns.requirements._1_social_media_user_profile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

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
        users.add(User.builder("John", "john@email.com")
              .id(++userId)
              .mobile("1234567890")
              .city("San Francisco")
              .job("Software Engineer")
              .profileImageUrl("https://app.com/john.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Reading", "Gaming"})
              .build());
        users.add(User.builder("Jane", "jane@email.com")
              .id(++userId)
              .mobile("2345678901")
              .city("Berlin")
              .job("UX Designer")
              .profileImageUrl("https://app.com/jane.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Drawing", "Painting"})
              .build());
        users.add(User.builder("Bob", "bob@email.com")
              .id(++userId)
              .mobile("3456789012")
              .city("New York")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/bob.png")
              .hasDrivingLicence(true)
              .hobbies(new String[]{"Cooking", "Traveling"})
              .build());
        users.add(User.builder("Dave", "dave@email.com")
              .id(++userId)
              .mobile("4567890123")
              .city("Boston")
              .job("Data Scientist")
              .profileImageUrl("https://app.com/dave.png")
              .hasDrivingLicence(false)
              .hobbies(new String[]{"Sports", "Photography"})
              .build());
        users.add(User.builder("Eve", "eve@email.com")
              .id(++userId)
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
            System.out.println("-------------------------");
            System.out.println("1. Manage users");
            System.out.println("2. Manage messages");
            System.out.println("3. Quit");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1: manageUsers(); break;
                case 2: manageMessages(); break;
                case 3: System.out.println("Thanks for using the application!"); break;
                default: System.out.println("Invalid option " + option); break;
            }
        } while (option != 3);
    }

    private static void manageUsers() {
        do {
            System.out.println("-------------------------");
            System.out.println("1. Show all users");
            System.out.println("2. Save new user");
            System.out.println("3. Search user");
            System.out.println("4. Update user");
            System.out.println("5. Delete user");
            System.out.println("6. Go Back");
            System.out.print("Select an option: ");
            option = scanner.nextInt();
            switch (option) {
                case 1: displayAllUsers(); break;
                case 2: saveUser(); break;
                case 3: displayUser(); break;
                case 4: updateUser(); break;
                case 5: deleteUser(); break;
                case 6: showMenu(); return;
                default: System.out.println("Invalid option " + option); break;
            }
        } while (true);
    }

    private static void displayAllUsers() {
        try {
            System.out.println();
            if (users.isEmpty()) {
                System.out.println("There are no users to display");
                return;
            }
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void saveUser() {
        try {
            scanner.nextLine();
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            if (name.equals("") || email.equals("")) {
                System.out.println("The name and email must not be empty");
                return;
            }
            boolean userExists = users.stream().anyMatch(user -> name.equals(user.getName()) || email.equals(user.getEmail()));
            if (userExists) {
                System.out.println("There is already a user with the name / email specified");
                return;
            }
            users.add(User.builder(name, email)
                  .id(++userId)
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
        System.out.print("Do you want to introduce your " + property + "? (y/n) ");
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.next().trim()) : Optional.empty();
    }

    private static Optional<Boolean> getHasDrivingLicense() {
        scanner.nextLine();
        System.out.print("Do you want to give details about driving licence? (y/n) ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Do you have a driving licence? (y/n) ");
            return Optional.of(scanner.nextLine().trim().equalsIgnoreCase("y"));
        }
        return Optional.empty();
    }

    private static Optional<String[]> getHobbies() {
        scanner.nextLine();
        System.out.print("Do you want to introduce your hobbies? (y/n) ");
        return scanner.nextLine().trim().equalsIgnoreCase("y") ? Optional.of(scanner.nextLine().trim().split(", ")) : Optional.empty();
    }

    private static void displayUser() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            System.out.println("User found: " + filteredUser.get());
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
            System.out.println("User " + user.getName() + " was updated successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void updateProperty(String property, Consumer<String> setter) {
        System.out.print("Enter new " + property + " value: ");
        String propertyValue = scanner.nextLine().trim();
        if (!propertyValue.equals("")) {
            setter.accept(propertyValue);
        }
    }

    private static void deleteUser() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            users.remove(filteredUser.get());
            System.out.println("User " + filteredUser.get().getName() + " was deleted successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static Optional<User> getUserByName() {
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        if (name.equals("")) {
            System.out.println("The name must not be empty");
            return Optional.empty();
        }
        Optional<User> filteredUser = users.stream().filter(user -> name.equals(user.getName())).findFirst();
        if (filteredUser.isEmpty()) {
            System.out.println("There is no user named " + name + " in the application");
            return Optional.empty();
        }
        return filteredUser;
    }

    private static void manageMessages() {
        do {
            System.out.println("-------------------------");
            System.out.println("1. Show all messages");
            System.out.println("2. Send a message");
            System.out.println("3. Go back");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: displayAllMessages(); break;
                case 2: sendMessage(); break;
                case 3: showMenu(); return;
                default: System.out.println("Invalid option " + option); break;
            }
        } while (true);
    }

    private static void displayAllMessages() {
        try {
            System.out.println();
            List<Message> messages = new ArrayList<>();
            Iterator<User> userIterator = users.iterator();
            while (userIterator.hasNext()) {
                messages.addAll(userIterator.next().getMessages());
            }
            if (messages.isEmpty()) {
                System.out.println("There are no messages to display");
                return;
            }
            Iterator<Message> messageIterator = messages.iterator();
            while (messageIterator.hasNext()) {
                System.out.println(messageIterator.next());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static void sendMessage() {
        try {
            Optional<User> filteredUser = getUserByName();
            if (filteredUser.isEmpty()) return;
            System.out.print("Write your message here: ");
            String text = scanner.nextLine().trim();
            if (text.equals("")) {
                System.out.println("The message must not be empty");
                return;
            }
            Message message = Message.builder().id(++messageId).text(text).build();
            User user = filteredUser.get();
            List<Message> messages = user.getMessages();
            messages.add(message);
            user.setMessages(messages);
            System.out.println("Message sent to " + user.getName() + " successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
