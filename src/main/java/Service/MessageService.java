package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import Model.Account;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) {
        Message messageInTable = messageDAO.getMessageById(message.getMessage_id());
        if (messageInTable == null) {
            messageDAO.addMessage(message);
            return message;
        } else {
            return null;
        }
    }

    public Message updateMessage(String message_text, int id) {
        Message message = messageDAO.updateMessage(message_text, id);
        return message;
    }

    public Message getMessagebyIdMessage(int id){
        Message message = messageDAO.getMessageById(id);
        if (message != null) {
            return message;
        } else {
            return null;
        }
    }

    public Message deleteMessage(int id){
        Message message = messageDAO.deleteMessage(id);
        return message;
    }

    public List<Message> getMessagesByUser(int id){
        List<Message> messages = messageDAO.getMessagesByUser(id);
        return messages;
    }
}
