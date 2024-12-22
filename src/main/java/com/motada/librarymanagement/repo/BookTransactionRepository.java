
package com.motada.librarymanagement.repo;

import com.motada.librarymanagement.entity.BookTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTransactionRepository extends JpaRepository<BookTransaction, Long> {
}
