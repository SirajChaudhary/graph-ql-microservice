package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.request.MemberInput;
import com.sirajchaudhary.library.entity.Member;

import java.util.List;

public interface MemberService {

    Member getMember(Long id);

    List<Member> getMembers();

    Member createMember(MemberInput input);

    Member updateMember(Long id, MemberInput input);

    boolean deleteMember(Long id);

}