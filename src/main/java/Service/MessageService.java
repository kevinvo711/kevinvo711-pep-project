package Service;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService{
    private MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO m){
        this.messageDAO = m;
    }

    /**
     * Checks: Message is not empty, is under 255 characters, and should be posted by an existing user in the database
     * @param Message m
     * @return Message object with id
     */
    public Message addMessage(Message m){
        if(m.getMessage_text().length() <= 0 
            || m.getMessage_text().length() >= 255
            || !messageDAO.accountExists(m.getPosted_by())){
                return null;
        }
        return messageDAO.insertMessage(m);
    }
    /**
     * Gets all messages in database
     * @return List<Message>
     */
    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    /**
     * Gets a specific message
     * @param int id
     * @return Message
     */
    public Message getMessage(int id){
        return messageDAO.getMessage(id);
    }

    /**
     * Deletes a specific message
     * @param int id
     * @return deleted Message
     */
    public Message deleteMessage(int id){
        return messageDAO.deleteMessage(id);
    }

    /**
     * Updates message in database
     * @param Message m, containing the message text to be updated ONLY and no other relevant fields
     * @param int id, the message_id in database to be updated
     * @return Message, the updated Message with its id, null otherwise
     */
    public Message updateMessage(Message m, int id){
        if(m.getMessage_text().length() <= 0 
            || m.getMessage_text().length() >= 255
            || messageDAO.getMessage(id) == null){
                return null;
        }
        return messageDAO.updateMessage(m, id);
    }

    /**
     * Gets all messages of a user
     * @param int id
     * @return List<Message> of messages
     */
    public List<Message> getAllAccountMessages(int id){
        return messageDAO.getAllAccountMessagesByID(id);
    }
}