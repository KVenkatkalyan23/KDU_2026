package com.example.demo.controller;


import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid BookRequestDto bookRequestDto){
        return bookService.addBook(bookRequestDto);
    }

    @GetMapping("/books")
    public ResponseEntity<Page<Book>> getBooksByAuthor(
            @RequestParam(required = true) String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sort){

        return bookService.getBooksByAuthor(author,page,sort);
    }

    @GetMapping()
    public ResponseEntity<?> getBooks(){
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Long id){
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        return bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editBook(@PathVariable Long id,@RequestBody @Valid BookRequestDto bookRequestDto){
        return bookService.editBook(id,bookRequestDto);
    }
}
