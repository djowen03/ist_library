package com.example.ist_library.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus code,
                                                          Object responseObj
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code.value());
        map.put("message", message);
        map.put("timestamp", LocalDateTime.now());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, code);
    }

    public static ResponseEntity<Object> generateResponseWithPage(String message, HttpStatus code,
                                                                  Object responseObj, PaginationResponse paginationResponse
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code.value());
        map.put("message", message);
        map.put("timestamp", LocalDateTime.now());
        map.put("data", responseObj);
        map.put("pagination" , paginationResponse);

        return new ResponseEntity<Object>(map, code);
    }
}
