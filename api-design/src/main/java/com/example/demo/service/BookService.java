package com.example.demo.service;


import com.example.demo.controller.BookController;
import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public ResponseEntity<?> addBook(BookRequestDto bookRequestDto) {
        Optional<Book> optionalBook = bookRepository.findByTitleIgnoreCase(bookRequestDto.getTitle());
        if(optionalBook.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book already exists with title" + bookRequestDto.getTitle());
        }

        Book book = new Book();
        book.setAuthor(bookRequestDto.getAuthor());
        book.setTitle(bookRequestDto.getTitle());

        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book saved");
    }

    public ResponseEntity<?> getBooks() {
        List<Book> books = bookRepository.findAll();
        if(books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No books");
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    public ResponseEntity<?> deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book no found with id : " + id);
        }

        bookRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<?> editBook(Long id, BookRequestDto bookRequestDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book no found with id : " + id);
        }

        Book book = optionalBook.get();
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());

        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }


    public ResponseEntity<Page<Book>> getBooksByAuthor(String author, int page, String sort) {
        if(author == null){
            throw new RuntimeException("author needed in query params");
        }

        if(!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc")){
            throw new RuntimeException("invalid sort query params");
        }

        Sort sorting = sort.equalsIgnoreCase("desc")
                ? Sort.by("title").descending()
                : Sort.by("title").ascending();

        Pageable pageable = PageRequest.of(page, 10, sorting);
        Page<Book> books;
        books = bookRepository.findByAuthorIgnoreCase(author, pageable);

        return ResponseEntity.ok(books);

    }

    public ResponseEntity<?> getBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow();

        EntityModel<Book> model = EntityModel.of(
                book,
                linkTo(methodOn(BookController.class).getBook(id)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks()).withRel("all-books"),
                linkTo(methodOn(BookController.class).deleteBook(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }
}
