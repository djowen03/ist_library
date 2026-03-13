package com.example.ist_library.service;

import com.example.ist_library.model.Books;
import com.example.ist_library.repository.BooksRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;

public class GetBookServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private BookService bookService;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    public void getBooksTestSucess() {

        ArrayList<Books> books = new ArrayList<>();
        books.add(Books.builder()
                .bookName("TestBook1")
                .author("TestAuthor1")
                .publicationYear("2022")
                .build());
        books.add(Books.builder()
                .bookName("TestBook2")
                .author("TestAuthor2")
                .publicationYear("2023")
                .build());

        Page<Books> page = new PageImpl<>(books, pageable, books.size());

        when(booksRepository.findAll(pageable)).thenReturn(page);

        Page<Books> result = bookService.getBooks(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.getContent().size() > 0);
        assertEquals("TestBook1", result.getContent().get(0).getBookName());

        verify(booksRepository, times(1)).findAll(pageable);


    }

}
