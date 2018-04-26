package by.htp.library.dao;

import by.htp.library.entity.Book;
import by.htp.library.entity.helper.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface BookRepository extends
        CrudRepository<Book, Integer>,
        PagingAndSortingRepository<Book, Integer>,
        QueryByExampleExecutor<Book>{

    List<Book> findByGenre(Genre genre);
    Page<Book> findByGenre(Genre genre, Pageable pageable);
    int countBookByGenre(Genre genre);
}
