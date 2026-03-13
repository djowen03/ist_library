package com.example.ist_library.service;

import com.example.ist_library.dto.request.BookRequestDTO;
import com.example.ist_library.model.Books;
import com.example.ist_library.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class BookService {

    @Autowired
    BooksRepository booksRepository;

    public Page<Books> getBooks(Pageable pageable){
        Page<Books> booksList = booksRepository.findAll(pageable);
        return booksList;
    }

    public void insertBook(BookRequestDTO request){

        Books book = Books.builder()
                .bookName(request.getBookName())
                .author(request.getAuthor())
                .publicationYear(request.getPublicationYear())
                .createdDate(Date.valueOf(LocalDate.now()))
                .build();

        booksRepository.save(book);
    }
}
