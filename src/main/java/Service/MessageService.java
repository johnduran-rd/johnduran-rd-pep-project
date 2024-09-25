package Service;

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

    private boolean messageIsValid(Message message){
        if (message.getMessage_text().length()>=255 || message.getMessage_text().isBlank()) {
            return false;
        }
        return true;
    }


}
