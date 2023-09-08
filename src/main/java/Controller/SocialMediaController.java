package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
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
    
    public SocialMediaController(){
        accountService = new AccountService();
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
     * Post request that handles JSON data for registration, endpoint "/register"
     * @param Context context
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount != null){
            context.json(mObjectMapper.writeValueAsString(addedAccount));
        }else{
            context.status(400);
        }
    }

    /**
     * Post request that handles JSON data for login, endpoint "/login"
     * @param Context context
     */
    private void postLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mObjectMapper = new ObjectMapper();
        Account account = mObjectMapper.readValue(context.body(), Account.class);
        Account existingAccount = accountService.getAccount(account);
        if(existingAccount != null){
            context.json(mObjectMapper.writeValueAsString(existingAccount));
        }else{
            context.status(401);
        }
    }

}