package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import models.UserRepository;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.Messages;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static play.libs.Json.toJson;

public class UserJPAController extends Controller {

    private final FormFactory formFactory;
    private final UserRepository userRepository;
    private final HttpExecutionContext ec;

    @Inject
    public UserJPAController(FormFactory formFactory, UserRepository userRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.userRepository = userRepository;
        this.ec = ec;
    }
    public CompletionStage<Result> getAllUsers(){
        System.out.println("### getAllUsers");
        /*return userRepository.list().thenApplyAsync(
                userStream -> ok(toJson(userStream.collect(Collectors.toList())))
                , ec.current());
*/
        return userRepository.list().thenApplyAsync(userStream -> {
            return ok(toJson(userStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> add() {
        JsonNode jsonNode = request().body().asJson();
        Form<User> userForm = formFactory.form(User.class).bind(jsonNode);
        Messages messages = Http.Context.current().messages();

        if (userForm.hasErrors()) {
            return supplyAsync(() -> {
                ObjectNode result = Json.newObject();
                result.set("message", userForm.errorsAsJson());
                return badRequest(result);
            });
        } else {
            return userRepository.create(userForm.get())
                    .thenApplyAsync(user -> user.isPresent() ?
                            ok(MessageFormat.format(messages.at("user.ok"), user.get().getId() ,"inserted")):
                            badRequest( MessageFormat.format( messages.at("user.alreadyExists"), user.get().getId())));
        }
    }


    public CompletionStage<Result> getUser(int id){
        return userRepository.get(id).thenApplyAsync(user -> {
            if (user.isPresent()) {
                return ok(toJson(user.get()));
            } else {
                return badRequest("User with id " + id + " not found");
            }
        }, ec.current());
    }

    public CompletionStage<Result> get(Integer id){
        Messages messages = Http.Context.current().messages();
        return userRepository.get(id)
                .thenApplyAsync(user ->
                                user.isPresent() ? ok(toJson(user.get())) : badRequest(MessageFormat.format("user.notFound", id))
                        , ec.current());
    }



}
