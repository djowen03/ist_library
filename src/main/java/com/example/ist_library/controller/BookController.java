package com.example.ist_library.controller;

import com.example.ist_library.dto.PaginationResponse;
import com.example.ist_library.dto.ResponseHandler;
import com.example.ist_library.dto.request.BookRequestDTO;
import com.example.ist_library.model.Books;
import com.example.ist_library.service.BookService;
import com.example.ist_library.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    KafkaService kafkaService;

    @GetMapping("")
    public ResponseEntity<Object> getBookList(
            @RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size
    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Books> data = bookService.getBooks(pageable);
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .page(page+1)
                .size(size)
                .totalData(data.getTotalElements())
                .totalPage(data.getTotalPages())
                .build();

        return ResponseHandler.generateResponseWithPage("Success", HttpStatus.OK, data.getContent(), paginationResponse);
    }

    @PostMapping("add")
    public ResponseEntity<Object> insertBook(@Valid @RequestBody BookRequestDTO request) throws JsonProcessingException {
        kafkaService.sendMessage(request);

        return ResponseHandler.generateResponse("Accepted", HttpStatus.ACCEPTED, "");
    }
}
