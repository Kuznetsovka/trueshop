package com.kuznetsovka.trueshop.controller;

import com.kuznetsovka.trueshop.domain.Product;
import com.kuznetsovka.trueshop.domain.User;
import com.kuznetsovka.trueshop.dto.EntityNotFoundResponse;
import com.kuznetsovka.trueshop.dto.ProductDto;
import com.kuznetsovka.trueshop.dto.UserDto;
import com.kuznetsovka.trueshop.exception.EntityNotFoundException;
import com.kuznetsovka.trueshop.service.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String getById(Model model, @PathVariable Long id) {
        User byId = userService.getById (id);
        model.addAttribute("user", byId == null ? new User() : byId);
        return "user";
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new UserDto());
        return "user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/new")
    public String saveUser(UserDto dto, Model model){
        if(userService.save(dto)){
            return "redirect:/users";
        }
        else {
            model.addAttribute("user", dto);
            return "user";
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal){
        if(principal == null){
            throw new RuntimeException("You are not authorize");
        }
        User user = userService.findByName(principal.getName());
        UserDto dto = userService.getByName (principal.getName());
        model.addAttribute("user", dto);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updateProfileUser(UserDto dto, Model model, Principal principal){
        if(principal == null
                || !Objects.equals(principal.getName(), dto.getName())){
            throw new RuntimeException("You are not authorize");
        }
        if(dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())){
            model.addAttribute("user", dto);
            return "/users/profile";
        }

        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }

        // http://localhost:8090/app/users/update?id=3 - GET
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/update")
    public String getFormUpdateProduct(Model model,@RequestParam(name = "id") long id){
        UserDto byId = userService.findById(id);
        model.addAttribute("user",
                byId == null ? new UserDto(): byId);
        return "update-user";
    }

    @PostAuthorize("isAuthenticated() and #name == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String name){
        System.out.println("Called method getRoles");
        User byName = userService.findByName(name);
        return byName.getRole().name();
    }

     //http://localhost:8090/app/users/update - POST
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProduct(UserDto updateUser){
        userService.save (updateUser);
        return "redirect:/users/" + userService.getId (updateUser);
    }

    @ExceptionHandler
    public ResponseEntity<EntityNotFoundResponse> handleException(EntityNotFoundException ex){
        EntityNotFoundResponse response = new EntityNotFoundResponse();
        response.setEntityName(ex.getEntityName());
        response.setEntityId(ex.getEntityId());
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
