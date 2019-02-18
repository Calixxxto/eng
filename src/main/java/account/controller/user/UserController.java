package account.controller.user;

import account.service.UserService;
import data.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
    UserService userService;

    @RequestMapping(value = {"/api/user"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User createUser(@RequestBody User user, HttpServletResponse response) {
        return userService.createUser(user);
    }

    @RequestMapping(value = {"/api/user/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUser(@PathVariable("id") Long userId, HttpServletResponse response) {
        return userService.getUser(userId);

    } 

    @RequestMapping(value = {"/api/user/{id}"}, method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User updateUser(@PathVariable("id") Long userId, @RequestBody User user, HttpServletResponse response) {
        return userService.updateUser(userId, user);
    }

}
