
package com.motada.librarymanagement.controller;



import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.SearchRequest;
import com.motada.librarymanagement.model.request.MemberCreateRequest;
import com.motada.librarymanagement.model.response.BookListResponse;
import com.motada.librarymanagement.model.response.GenericResponse;
import com.motada.librarymanagement.model.response.MemberListResponse;
import com.motada.librarymanagement.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public GenericResponse<String> addMember(@RequestBody @Valid MemberCreateRequest member) {
        try{
            return memberService.addMember(member);
        }catch (Exception e){
            return new GenericResponse<>(0L,e.getMessage());
        }
    }

    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping("/all")
    public GenericResponse<MemberListResponse> getAllMembers(@RequestBody SearchRequest searchRequest) {
        return memberService.getAllMembers(searchRequest);
    }

    @GetMapping("/{id}")
    public Member getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PutMapping("/{id}")
    public GenericResponse<String> updateMember(@PathVariable Long id, @RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        try{
            return memberService.updateMember(id,memberCreateRequest);
        }catch (Exception e){
            return new GenericResponse<>(0L,e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public GenericResponse<String> deleteBook(@PathVariable Long id) {
        try{
            return memberService.deleteMember(id);
        }catch (Exception e){
            return new GenericResponse<>(-1L,e.getMessage());
        }
    }
}
