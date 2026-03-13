package com.example.ist_library.service;

import com.example.ist_library.dto.request.BookRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaService {

    @Autowired
    BookService bookService;

    @Autowired
    ObjectMapper mapper;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC_NAME= "library";

    public KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(BookRequestDTO message) throws JsonProcessingException {
        String jsonMessage = mapper.writeValueAsString(message);
        kafkaTemplate.send(TOPIC_NAME, jsonMessage);
        System.out.println("Send message for insert book: " +  jsonMessage);
    }
    
    @KafkaListener(topics = TOPIC_NAME, groupId = "library-group-id")
    public void consumeMessage(String message) throws JsonProcessingException {
        System.out.println("Received message for insert book: " + message);
        BookRequestDTO jsonRequest = mapper.readValue(message, BookRequestDTO.class);
        bookService.insertBook(jsonRequest);
    }


}