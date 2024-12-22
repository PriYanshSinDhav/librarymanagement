
package com.motada.librarymanagement.repo;

import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.response.MemberListItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "select new com.motada.librarymanagement.model.response.MemberListItem(o.name,o.email,o.phone,o.membershipDate,o.createdOn ) from Member o where o.deleted = false and  (LOWER(o.name)) like LOWER(CONCAT('%',?1, '%')) ")
    Page<MemberListItem> findByName(String title, Pageable pageable);
}
