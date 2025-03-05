package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    AccountDAO accountDAO;
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() <= 255) {
            //System.out.println("Message is not blank and not too long");
            return this.messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return this.messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id) {
        return this.messageDAO.getMessageById(message_id);
    }
    
    public Message deleteMessageById(int message_id) {
        if (this.messageDAO.getMessageById(message_id) != null) {
            return this.messageDAO.deleteMessageById(this.messageDAO.getMessageById(message_id));
        } else return null;
    }

    public Message updateMessage(int message_id, Message message) {
        if (this.messageDAO.getMessageById(message_id) != null && message.getMessage_text().length() <= 255 && !message.getMessage_text().isBlank()) {
            //System.out.println("Updated Message: " + message.getMessage_text());
            this.messageDAO.updateMessage(message_id, message);
            return this.messageDAO.getMessageById(message_id);
        }
        
        return null;
    }

    public List<Message> getAccountMessages(int account_id) {
        return this.messageDAO.getAccountMessages(account_id);
    }
}
