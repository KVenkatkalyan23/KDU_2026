package com.example.demo.service;


import com.example.demo.controller.BookController;
import com.example.demo.dto.BookRequestDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {


    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

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

        logger.info("New book added: Title='{}', Author='{}'",
                book.getTitle(),
                book.getAuthor());

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
        try {
            Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("No book found with : " + id));
            EntityModel<Book> model = EntityModel.of(
                    book,
                    linkTo(methodOn(BookController.class).getBook(id)).withSelfRel(),
                    linkTo(methodOn(BookController.class).getBooks()).withRel("all-books"),
                    linkTo(methodOn(BookController.class).deleteBook(id)).withRel("delete")
            );
            return ResponseEntity.ok(model);
        }catch (Exception e){
            logger.error("Error occurred while searching for book with title '{}'",
                    id, e);
            throw e;
        }
    }
}
