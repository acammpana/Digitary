package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;
import models.UserStore;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.Util;
import views.html.users.create;
import views.html.users.edit;
import views.html.users.index;
import views.html.users.show;

import javax.inject.Inject;
import java.util.List;

public class UserController   extends Controller{

    @Inject
    FormFactory formFactory;

    // Index: get all users
    public Result index(){
        List<User> users = User.find.all();
        return ok(index.render(users));
    }

    // to create a user
    public Result createUser(){
        Form<User> createForm = formFactory.form(User.class);
        return ok(create.render(createForm));
    }

    // to save a user
    public Result save(){
        // bind form from request and get that request
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if(userForm.hasErrors()){
            flash("danger","Please correct the form below");
            return badRequest(create.render(userForm));
        }
        User user = userForm.get();
        user.save();
        flash("success","User saved successfully");
        return redirect(routes.UserController.index());
    }

    // to edit a user
    public Result edit(Integer id){
        //User user = User.findById(id);
        User user = User.find.byId(id);
        if(user==null) {
            return notFound("User not found");
        }
        Form<User> userForm = formFactory.form(User.class).fill(user);
        return ok(edit.render(userForm));
    }

    // to update a user
    public Result updateUser(){
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if(userForm.hasErrors()){
            flash("danger","Please correct the form below");
            return badRequest(edit.render(userForm));
        }
        User user = userForm.get();
        User oldUser = User.find.byId(user.getId());
        if(oldUser == null ){
            return notFound("User not found");
        }
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
        oldUser.setAddress1(user.getAddress1());
        oldUser.setAddress2(user.getAddress2());
        oldUser.setTown(user.getTown());
        oldUser.setPostcode(user.getPostcode());
        oldUser.setCountry(user.getCountry());
        oldUser.setPhone(user.getPhone());

        //oldUser.save();
        //or
        oldUser.update();
        flash("success","User updated succefully");
        return redirect(routes.UserController.index());
    }

    // to delete a user
    public Result destroy(Integer id){
        User user = User.find.byId(id);
        if(user == null ){
            return notFound("User not found");
        }

        user.delete();
        flash("success", "User has been deleted successfully");
        return redirect(routes.UserController.index());

    }

    // for user details
    public Result show(Integer id){
        //User user = User.findById(id);
        User user = User.find.byId(id);
        if(user == null ){
            return notFound("User not found");
        }
        return ok(show.render(user));
    }

    public Result create() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Util.createResponse("Expecting Json data", false));
        }
        User user = UserStore.getInstance().addUser(Json.fromJson(json, User.class));
        JsonNode jsonObject = Json.toJson(user);


        user.save();

        return created(Util.createResponse(jsonObject, true));
    }

    public Result update() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest(Util.createResponse("Expecting Json data", false));
        }
        User user = UserStore.getInstance().updateUser(Json.fromJson(json, User.class));
        if (user == null) {
            return notFound(Util.createResponse("User not found", false));
        }

        JsonNode jsonObject = Json.toJson(user);
        return ok(Util.createResponse(jsonObject, true));
    }

    public Result retrieve(int id) {
        User user = User.find.byId(id);
        JsonNode jsonObjects = Json.toJson(User.find.byId(id));
        System.out.println(jsonObjects.toString());

        return ok(Util.createResponse(jsonObjects, true));
    }

    public Result listUsers() {
        List<User> result = User.find.all();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonData = mapper.convertValue(result, JsonNode.class);
        return ok(Util.createResponse(jsonData, true));
    }

    public Result delete(int id) {
        if (!UserStore.getInstance().deleteUser(id)) {
            return notFound(Util.createResponse("User with id:" + id + " not found", false));
        }
        return ok(Util.createResponse("User with id:" + id + " deleted", true));
    }
}
