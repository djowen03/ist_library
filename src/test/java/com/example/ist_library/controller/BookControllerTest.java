package com.example.ist_library.controller;


import com.example.ist_library.dto.request.BookRequestDTO;
import com.example.ist_library.model.Books;
import com.example.ist_library.service.BookService;
import com.example.ist_library.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private BookController bookController;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    public void getBookControllerTestSuccess(){

        Books book1 = Books.builder()
                .bookName("TestBook1")
                .author("TestAuthor1")
                .publicationYear("2022")
                .build();
        Books book2 = Books.builder()
                .bookName("TestBook2")
                .author("TestAuthor2")
                .publicationYear("2023")
                .build();

        Page<Books> page = new PageImpl<>(Arrays.asList(book1, book2));;

        when(bookService.getBooks(pageable)).thenReturn(page);

        ResponseEntity<Object> response = bookController.getBookList(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void insertBookControllerTestSuccess() throws JsonProcessingException {
        BookRequestDTO validRequest = BookRequestDTO.builder()
                .bookName("Test Book")
                .author("Test")
                .publicationYear("2022")
                .build();

        doNothing().when(kafkaService).sendMessage(validRequest);

        ResponseEntity<Object> response = bookController.insertBook(validRequest);

        verify(kafkaService, times(1)).sendMessage(validRequest);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
