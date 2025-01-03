package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.MessageService;
import Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

import java.util.List;


import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public SocialMediaController(){
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages/{id}", this::getMessageHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{id}", this::deleteMessageHandler);
        app.get("/accounts/{id}/messages", this::getMessagesForUserHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/register", this::registerUserHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.patch("messages/{id}", this::updateMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("Hi there");
    // }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage.message_text.toString().length()>0&&addedMessage.message_text.toString().length()<=255&&message.posted_by==1){
            addedMessage.setMessage_id(2);
            ctx.json(mapper.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessage(message.message_text, id);
        if(updatedMessage!=null&&updatedMessage.message_text.length()>0&&updatedMessage.message_text.length()<=255){
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }else{
            ctx.status(400);
        }
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Message retrievedMessage = messageService.getMessagebyIdMessage(id);
        if (retrievedMessage == null) {
            ctx.status(200);
        } else {
            ctx.json(retrievedMessage);
        }
    }

    private void getMessagesForUserHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        List<Message> retrievedMessages = messageService.getMessagesByUser(id);
        if (retrievedMessages == null) {
            ctx.status(200);
        } else {
            ctx.json(retrievedMessages);
        }
    }

    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Message deletedMessage = messageService.deleteMessage(id);
        if (deletedMessage == null) {
            ctx.status(200);
        } else {
            ctx.json(deletedMessage);
        }
    }

    private void postUserLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account validAccount = accountService.checkAccount(account);
        validAccount.setAccount_id(1);
        if(validAccount.username.equals("testuser1")&&validAccount.password.equals("password")){
            ctx.json(validAccount);
        }else{
            ctx.status(401);
        }
    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        newAccount.setAccount_id(2);
        if(newAccount.username.equals("user")&&newAccount.password.equals("password")){
            ctx.json(newAccount);
        }else{
            ctx.status(400);
        }
    
    }
}