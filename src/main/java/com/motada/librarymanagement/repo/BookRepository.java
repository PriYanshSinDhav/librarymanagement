
package com.motada.librarymanagement.repo;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.model.response.BookListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "select new com.motada.librarymanagement.model.response.BookListItem(o.author,o.genre,o.title,o.publishedYear) from Book o where o.deleted = false and  (LOWER(o.title)) like LOWER(CONCAT('%',?1, '%')) or (LOWER(o.genre)) like LOWER(CONCAT('%',?1, '%')) ")
    Page<BookListItem> findByTitle(String title, Pageable pageable);
}
