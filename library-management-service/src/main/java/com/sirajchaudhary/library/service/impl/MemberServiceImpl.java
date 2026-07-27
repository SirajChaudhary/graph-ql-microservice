package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.request.MemberInput;
import com.sirajchaudhary.library.entity.Member;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.MemberRepository;
import com.sirajchaudhary.library.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public Member getMember(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Override
    public List<Member> getMembers() {
        return repository.findAll();
    }

    @Override
    public Member createMember(MemberInput input) {

        log.info("Creating member: {} {}", input.getFirstName(), input.getLastName());

        if (repository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException(
                    "Member with email '" + input.getEmail() + "' already exists.");
        }

        Member member = Member.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .build();

        return repository.save(member);
    }

    @Override
    public Member updateMember(Long id, MemberInput input) {

        Member member = getMember(id);

        member.setFirstName(input.getFirstName());
        member.setLastName(input.getLastName());
        member.setEmail(input.getEmail());

        return repository.save(member);
    }

    @Override
    public boolean deleteMember(Long id) {

        repository.deleteById(id);

        return true;
    }
}