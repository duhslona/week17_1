package my.backend.dbexample.repository;

import my.backend.dbexample.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAllBooks();

    Book findBookById(Long id);
}
