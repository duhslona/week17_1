package my.backend.dbexample.repository;

import my.backend.dbexample.model.Book;
import my.backend.dbexample.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {
        String SQL_findAllBooks = "select * from books;";

        return executeGetQuery(SQL_findAllBooks);
    }

    @Override
    public Book findBookById(Long id) {
        String SQL_findBookById = "select * from books where id = " + id + ";";

        List<Book> result = executeGetQuery(SQL_findBookById);
        return result.isEmpty() ? null : result.get(0);
    }

    private List<Book> executeGetQuery(String query) {
        List<Book> result = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private Book convertRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("Name");

        return new Book(id, name);
    }
}
