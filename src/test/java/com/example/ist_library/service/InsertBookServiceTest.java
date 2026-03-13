package com.example.ist_library.service;

import com.example.ist_library.dto.request.BookRequestDTO;
import com.example.ist_library.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class InsertBookServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void insertBookTestSuccess() {

        BookRequestDTO request = BookRequestDTO.builder()
                .bookName("TestBook1")
                .author("TestAuthor1")
                .publicationYear("2021")
                .build();

        bookService.insertBook(request);

        verify(booksRepository, times(1)).save(argThat(book ->
                "TestBook1".equals(book.getBookName()) &&
                        "TestAuthor1".equals(book.getAuthor()) &&
                        "2021".equals(book.getPublicationYear())
        ));
    }

}
