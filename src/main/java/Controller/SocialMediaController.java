package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getAllAccountMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Post request that handles JSON data for registration, endpoint /register
     * @param Context context
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            // context.json(mObjectMapper.writeValueAsString(addedAccount));
            context.json(addedAccount);
        }else{
            context.status(400);
        }
    }

    /**
     * Post request that handles JSON data for login, endpoint /login
     * @param Context context
     */
    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(context.body(), Account.class);
        Account existingAccount = accountService.getAccount(account);
        if(existingAccount != null){
            // context.json(mObjectMapper.writeValueAsString(existingAccount));
            context.json(existingAccount);
        }else{
            context.status(401);
        }
    }
    /**
     * Handles a message at endpoint /messages
     * @param context
     * @throws JsonProcessingException
     */
    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Message message = mObjectMapper.readValue(context.body(), Message.class);
        Message createdMessage = messageService.addMessage(message);
        if(createdMessage != null){
            // context.json(mObjectMapper.writeValueAsString(createdMessage));
            context.json(createdMessage);
        }else{
            context.status(400);
        }
    }

    /**
     * Get all messages from database, including if empty
     * @param Context context
     * @throws JsonProcessingException
     */
    private void getMessagesHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getAllMessages());
    }

    /**
     * Gets a specific message from database, based on id at /messages/{message_id} endpoint
     * @param Context context
     * @throws JsonProcessingException
     */
    private void getMessageByIDHandler(Context context) throws JsonProcessingException{
        Message m = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if(m != null){
            context.json(m);
        }else{
            context.json("");
        }
    }

    /**
     * Deletes a message from database, based on id at /messages/{message_id} endpoint
     * @param Context context
     * @throws JsonProcessingException
     */
    private void deleteMessageByIDHandler(Context context) throws JsonProcessingException{
        Message m = messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));
        if(m != null){
            context.json(m);
        }else{
            context.json("");
        }
    }

    /**
     * Updates a message in database, based on id at /messages/{message_id} endpoint
     * @param Context context
     * @throws JsonProcessingException
     */
    private void patchMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper(); //Jackson ObjectMapper
        Message m = mObjectMapper.readValue(context.body(), Message.class); //Read relevant JSON value
        int id = Integer.parseInt(context.pathParam("message_id")); //get paramaterized endpoint value
        Message updatedMessage = messageService.updateMessage(m.getMessage_text(), id); //send relevant data over
        if(updatedMessage != null){
            context.json(updatedMessage);
        }else{
            context.status(400);
        }
    }

    /**
     * Gets all messages of an account
     * @param Context context
     * @throws JsonProcessingException
     */
    private void getAllAccountMessagesHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getAllAccountMessages(Integer.parseInt(context.pathParam("account_id"))));
    }
}