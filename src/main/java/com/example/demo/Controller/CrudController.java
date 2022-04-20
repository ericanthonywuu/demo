package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Model.UserRepository;
import com.example.demo.Request.CrudRequest;
import com.example.demo.Response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class CrudController {
    // TODO: Find way to prevent importing user repository in all controller!
    private final UserRepository UserRepo;

    public CrudController(UserRepository UserRepo) {
        this.UserRepo = UserRepo;
    }

    /**
     * Get user data
     *
     * @return {ResponseEntity<Object>}
     */
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser() {
        try {
            List<User> userData = UserRepo.findAll();

            return Response.generateResponse("User Data", userData, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: change this to global handler
            log.error("failed execute query", e);
            return Response.generateResponse("Fail to execute data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get user data by Id
     *
     * @param id {Integer}
     * @return {ResponseEntity<Object>}
     */
    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserById(@PathVariable("id") Integer id) {
        try {
            Optional<User> userData = UserRepo.findById(id);

            if (!userData.isPresent()) {
                return Response.generateResponse("User Data with id: " + id + " is not found", HttpStatus.NOT_FOUND);
            }

            return Response.generateResponse("User Data with id: " + id, userData, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: change this to global handler
            log.error("failed execute query", e);
            return Response.generateResponse("Fail to execute data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Inserting user data
     *
     * @param request {CrudRequest}
     * @return {ResponseEntity<Object>}
     */
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity<Object> insertUser(@RequestBody CrudRequest request) {
        try {
            // validating request to prevent null data
            if (request.username == null || request.password == null) {
                return Response.generateResponse("username and password is required", HttpStatus.BAD_REQUEST);
            }

            // TODO: add another validation such username must have more than 6 character
            // ...

            // ... and saving data to database
            UserRepo.save(new User(request.username, request.password));

            // ... returning response created
            return Response.generateResponse("User created!", HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: change this to global handler
            log.error("failed execute query", e);
            return Response.generateResponse("Fail to execute data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updating user data by Id
     *
     * @param request {CrudRequest}
     * @return {ResponseEntity<Object>}
     */
    @RequestMapping(path = "/user", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateUser(@RequestBody CrudRequest request) {
        try {
            // validating request to prevent null data
            if (request.id == null || request.username == null || request.password == null) {
                return Response.generateResponse("username and password is required", HttpStatus.BAD_REQUEST);
            }

            Optional<User> userData = UserRepo.findById(request.id);

            if (!userData.isPresent()) {
                return Response.generateResponse("User Data with id: " + request.id + " is not found", HttpStatus.NOT_FOUND);
            }

            User userModel = userData.get();
            userModel.setPassword(request.password);
            userModel.setUsername(request.username);

            UserRepo.save(userModel);

            return Response.generateResponse("User with id: " + request.id + " has been updated", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: change this to global handler
            log.error("failed execute query", e);
            return Response.generateResponse("Fail to execute data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deleting user data by id
     *
     * @param id {Integer}
     * @return {ResponseEntity<Object>}
     */
    @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        try {
            // validating request to prevent null data
            if (id == null) {
                return Response.generateResponse("id is required", HttpStatus.BAD_REQUEST);
            }

            Optional<User> userData = UserRepo.findById(id);

            if (!userData.isPresent()) {
                return Response.generateResponse("User Data with id: " + id + " is not found", HttpStatus.NOT_FOUND);
            }

            UserRepo.deleteById(id);

            return Response.generateResponse("User with id: " + id + " has been deleted", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: change this to global handler
            log.error("failed execute query", e);
            return Response.generateResponse("Fail to execute data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
