package com.example.demo.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Response {
    /**
     * Default response for returning message only
     * @param message {String}
     * @return {ResponseEntity<Object>}
     */
    public static ResponseEntity<Object> generateResponse(String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", null);

        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

    /**
     * Generate response for message and custom HttpStatus
     * @param message {String}
     * @param status {HttpStatus}
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", null);

        return new ResponseEntity<Object>(map, status);
    }

    /**
     * Generate response for message, data and custom HttpStatus
     * @param message {String}
     * @param responseObj {Object}
     * @param status {HttpStatus}
     * @return ResponseEntity<Object>
     */
    public static ResponseEntity<Object> generateResponse(String message, Object responseObj, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }
}
