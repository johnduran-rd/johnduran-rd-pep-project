package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public Message addMessage(Message message){
        if (messageIsValid(message)) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int message_id){
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteMessageById(int message_id){
        return messageDAO.deleteMessageById(message_id);
    }

    public Message updateMessage(int message_id, String message_text){
        if (!(message_text.isBlank() || message_text.length()>=255)) {            
            return messageDAO.updateMessageText(message_id, message_text);
        }
        return null;
    }

    public List<Message> getMessagesByUser(int user_id){
        return messageDAO.getMessageByUser(user_id);
    }

    private boolean messageIsValid(Message message){
        if (message.getMessage_text().length()>=255 || message.getMessage_text().isBlank()) {
            return false;
        }
        return true;
    }


}
