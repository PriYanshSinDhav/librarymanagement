
package com.motada.librarymanagement.repo;

import com.motada.librarymanagement.entity.BookTransaction;
import com.motada.librarymanagement.model.response.TransactionListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {

    @Query(value = "select new com.motada.librarymanagement.model.response.TransactionListItem(o.member.name,o.member.phone,o.book.title,o.transactionDate) from BookTransaction o where " +
            "(LOWER(o.book.title)) like LOWER(CONCAT('%',?1, '%')) or (LOWER(o.member.name)) like LOWER(CONCAT('%',?1, '%'))")
    Page<TransactionListItem> getTransactionsFilterByName(String name, Pageable pageable);
}
