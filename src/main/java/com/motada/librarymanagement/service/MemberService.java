
package com.motada.librarymanagement.service;


import com.motada.librarymanagement.entity.Member;
import com.motada.librarymanagement.model.INPage;
import com.motada.librarymanagement.model.SearchRequest;
import com.motada.librarymanagement.model.exceptions.CreateException;
import com.motada.librarymanagement.model.exceptions.DeleteException;
import com.motada.librarymanagement.model.exceptions.SearchException;
import com.motada.librarymanagement.model.exceptions.UpdateException;
import com.motada.librarymanagement.model.request.MemberCreateRequest;
import com.motada.librarymanagement.model.response.*;
import com.motada.librarymanagement.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService extends BaseService{


    private final MemberRepository memberRepository;



    @Transactional(rollbackFor = CreateException.class)
    public GenericResponse<String> addMember(MemberCreateRequest memberCreateRequest) throws CreateException {

        try {
            Member member = new Member();
            member.setEmail(memberCreateRequest.getEmail());
            member.setName(memberCreateRequest.getName());
            member.setPhone(memberCreateRequest.getPhone());
            member.setMembershipDate(currentTimestamp());
            member.setCreatedOn(currentTimestamp());
            member.setUpdatedOn(currentTimestamp());
            member = memberRepository.save(member);
            return new GenericResponse<>(0L,"Success",String.valueOf(member.getId()));
        }catch (Exception e){
            logError(e);
            throw new CreateException("Error creating member record");
        }

    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public GenericResponse<MemberListResponse> getAllMembers(SearchRequest searchRequest) {

        Pageable pageable = PageRequest.of(searchRequest.getPageNumber(), searchRequest.getPageSize(), parseToValidSort(searchRequest.getSortColumn(), searchRequest.getSortDirection()));

        String title = "";
        if(searchRequest.getSearchFilter()!=null){
            title = searchRequest.getSearchFilter();
        }

        Page<MemberListItem> memberListItemPage = memberRepository.findByName(title,pageable);

        INPage page = new INPage(memberListItemPage.getSize(), memberListItemPage.getTotalElements(), memberListItemPage.getTotalPages(),
                memberListItemPage.getNumber());

        MemberListResponse memberListResponse = new MemberListResponse();
        memberListResponse.setMemberItemList(memberListItemPage.getContent());
        memberListResponse.setPage(page);

        return new GenericResponse<>(0L , "Sucess" , memberListResponse);
    }


    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    @Transactional(rollbackFor = UpdateException.class)
    public GenericResponse<String> updateMember(Long id, MemberCreateRequest memberCreateRequest) throws SearchException, UpdateException {

        if (memberRepository.findById(id).isEmpty()) {
            throw new SearchException("Cannot update: Member not found with ID: " + id);
        }

        try{
            Member member = memberRepository.findById(id).get();
            member.setEmail(memberCreateRequest.getEmail());
            member.setName(memberCreateRequest.getName());
            member.setPhone(memberCreateRequest.getPhone());
            member.setMembershipDate(currentTimestamp());
            member.setUpdatedOn(currentTimestamp());
            member = memberRepository.save(member);
            return new GenericResponse<>(0L,"Success",String.valueOf(member.getId()));
        }catch (Exception e){
            throw new UpdateException("Error updating member : reason " + e.getMessage());
        }


    }


    @Transactional(rollbackFor = DeleteException.class)
    public GenericResponse<String> deleteMember(Long id) throws SearchException, DeleteException {
        if (memberRepository.findById(id).isEmpty()) {
            throw new SearchException("Cannot delete: Member not found with ID: " + id);
        }
        try{
            Member member = memberRepository.findById(id).get();

            member.setDeleted(true);
            member.setUpdatedOn(currentTimestamp());
            member = memberRepository.save(member);
            return new GenericResponse<>(0L,"Success",String.valueOf(member.getId()));
        }catch (Exception e){
            logError(e);
            throw new DeleteException("Error deleting book record");
        }
    }

    protected Sort parseToValidSort(String columnName, String direction) {

        String sortColum;

        if (columnName != null) {
            switch (columnName) {
                case "name":
                    sortColum = columnName;
                    break;
                case "email":
                    sortColum = columnName;
                    break;
                case "phone":
                    sortColum = columnName;
                    break;
                case "membershipDate":
                    sortColum = columnName;
                    break;
                default:
                    sortColum = "membershipDate";
            }
        } else {
            sortColum = "membershipDate";
        }

        if (direction != null && direction.equals("asc")) {
            return Sort.by(sortColum).ascending();
        } else {
            return Sort.by(sortColum).descending();
        }

    }
}
