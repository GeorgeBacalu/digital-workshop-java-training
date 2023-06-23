package clean.code.design_patterns.requirements._1_social_media_user_profile.service;

import clean.code.design_patterns.requirements._1_social_media_user_profile.entity.Message;
import clean.code.design_patterns.requirements._1_social_media_user_profile.entity.User;
import clean.code.design_patterns.requirements._1_social_media_user_profile.utils.EntityIdGenerator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final Scanner scanner;
    private final Map<Integer, User> users;
    private final EntityIdGenerator entityIdGenerator;
    private final UserService userService;

    public void manageMessages() {
        while (true) {
            log.info("-------------------------");
            log.info("1. Show all messages");
            log.info("2. Send a message");
            log.info("3. Go back");
            log.info("Select an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: displayAllMessages(); break;
                case 2:
                    log.info("Enter the id of the user to send the message to: ");
                    sendMessage(scanner.nextInt());
                    break;
                case 3: return;
                default: log.info("Invalid option {}", option);
            }
        }
    }

    private void displayAllMessages() {
        try {
            log.info("");
            List<Message> messages = new ArrayList<>();
            Iterator<Map.Entry<Integer, User>> userIterator = users.entrySet().iterator();
            while (userIterator.hasNext()) {
                List<Message> userMessages = userIterator.next().getValue().getMessages();
                if (userMessages != null) {
                    messages.addAll(userMessages);
                }
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
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }

    private void sendMessage(Integer userId) {
        try {
            Optional<User> filteredUser = userService.getUserById(userId);
            if (filteredUser.isEmpty()) return;
            log.info("Write your message here: ");
            String text = scanner.nextLine().trim();
            if (text.equals("")) {
                log.info("The message must not be empty");
                return;
            }
            Message message = Message.builder().id(entityIdGenerator.getNextMessageId()).text(text).build();
            User user = filteredUser.get();
            List<Message> messages = user.getMessages();
            if (messages == null) {
                messages = new ArrayList<>();
            }
            messages.add(message);
            user.setMessages(messages);
            log.info("Message sent to {} successfully", user.getName());
        } catch (Exception exception) {
            log.error("Unexpected error occurred: {}", exception.getMessage());
        }
    }
}
