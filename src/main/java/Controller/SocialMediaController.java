package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
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

    public SocialMediaController() {
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
        app.post("/register", this::getRegisterUserHandler);
        app.post("login", this::getLoginHandler);
        app.post("/messages", this::postCreateMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAccountMessagesHandler);
        app.get("/accounts", this::getAllAccountsHandler);

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
     * Handler for user registration.
     * @param context
     */
    private void getRegisterUserHandler(Context context) throws JsonProcessingException{
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(context.body(), Account.class);
        Account regAccount = accountService.registerAccount(account);
        if(regAccount==null){
            context.status(400);
        }else{
            context.json(map.writeValueAsString(regAccount));
        }
    }

    /**
     * Handler for user login.
     * @param context
     */
    private void getLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(context.body(), Account.class);
        Account logAccount = accountService.login(account);

        if (logAccount == null) {
            context.status(401);
        } else {
            context.json(map.writeValueAsString(logAccount));
        }
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        context.json(messageService.getAllMessages());
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        if (messageService.getMessageById(Integer.parseInt(context.pathParam("message_id"))) == null) {
            context.json(context.body());
        } else {
            context.json(messageService.getMessageById(Integer.parseInt(context.pathParam("message_id"))));
        }
        
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        if (messageService.getMessageById(Integer.parseInt(context.pathParam("message_id"))) == null) {
            context.json(context.body());
        } else {
            context.json(messageService.deletMessageById(Integer.parseInt(context.pathParam("message_id"))));
        }
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException{
        
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void getAccountMessagesHandler(Context context) throws JsonProcessingException{
        
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void getAllAccountsHandler(Context context) throws JsonProcessingException{
        
    }

    /**
     * Handler for message creation.
     * @param context
     */
    private void postCreateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper map = new ObjectMapper();
        Message message = map.readValue(context.body(), Message.class);
        Message newMessage = messageService.addMessage(message);

        if (newMessage == null) {
            context.status(400);
        } else {
            context.json(map.writeValueAsString(newMessage));
        }

    }

}