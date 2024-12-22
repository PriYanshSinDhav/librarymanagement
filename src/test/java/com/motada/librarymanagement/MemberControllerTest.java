package com.motada.librarymanagement;


import com.motada.librarymanagement.entity.Book;
import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.DeleteException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.request.BookCreateRequest;
import com.motada.librarymanagement.model.request.MemberCreateRequest;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.repo.MemberRepository;
import com.motada.librarymanagement.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void testAddMember() throws CreateException {




        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setEmail("priyansh@gmail.com");
        memberCreateRequest.setName("Priyansh");
        memberCreateRequest.setPhone("9225453243");


        GenericResponse<String> genericResponse = memberService.addMember(memberCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());
        Member member = memberRepository.findById(Long.valueOf(genericResponse.getResponse())).get();

        assertNotNull(member.getId());
        assertEquals("priyansh@gmail.com", member.getEmail());

        assertNotNull(member.getId(), "Member ID should not be null after saving");
        assertEquals("Priyansh", member.getName());
        assertEquals("9225453243", member.getPhone());
        assertFalse(member.isDeleted() );
        assertNotNull(member.getCreatedOn(), "Member createdOn timestamp should not be null");
        assertNotNull(member.getUpdatedOn(), "Member updatedOn timestamp should not be null");
    }



    @Test
    public void testDeleteBook() throws CreateException, SearchException, DeleteException {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setEmail("priyansh@gmail.com");
        memberCreateRequest.setName("Priyansh");
        memberCreateRequest.setPhone("9225453243");

        GenericResponse<String> genericResponse = memberService.addMember(memberCreateRequest);


        assertEquals(0L, genericResponse.getResponseCode());


        GenericResponse<String> genericResponse1 =  memberService.deleteMember(Long.valueOf(genericResponse.getResponse()));
        assertEquals(0L, genericResponse1.getResponseCode());
        Optional<Member> foundBook = memberRepository.findById(Long.valueOf(genericResponse1.getResponse()));

        assertTrue(foundBook.isPresent());
        assertTrue(foundBook.get().isDeleted());
    }



}
