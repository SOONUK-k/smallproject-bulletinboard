package com.example.bulletinboard.member.service;

import com.example.bulletinboard.exception.BusinessLogicException;
import com.example.bulletinboard.exception.ExceptionCode;
import com.example.bulletinboard.member.entity.Member;
import com.example.bulletinboard.member.repository.MemberRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    public MemberService(MemberRepository memberRepository,
                         ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    //==복붙==//
    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        Member savedMember = memberRepository.save(member);

        // 추가된 부분
//        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));
        Optional.ofNullable(member.getMemberStatus())
                .ifPresent(memberStatus -> findMember.setMemberStatus(memberStatus));

        return memberRepository.save(findMember);
    }

    @Transactional(readOnly = true)
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size,
                Sort.by("memberId").descending()));
    }

    public void deleteMember(Long memberId) {
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);
    }
    @Transactional(readOnly = true)
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember =
                memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    //==복붙==//
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////


//#1. Optional 처리에 대한 고민 필요
    //BusinessLogic Exception 추가해야함

    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isEmpty())
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }

    public void verifyExistsId(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty())
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }

    public void verifyMemberIsMember(Long memberId) {
        Member foundMember = memberRepository.findById(memberId).get();
        if (foundMember.getMemberLevel()== Member.MemberLevel.MEMBER_ADMIN){
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER_STATUS);
        }
    }

    public void verifyMemberIsAdmin(Long memberId) {
        Member foundMember = memberRepository.findById(memberId).get();
        if (foundMember.getMemberLevel()!= Member.MemberLevel.MEMBER_ADMIN){
            throw new BusinessLogicException(ExceptionCode.INVALID_MEMBER_STATUS);
        }
    }
}
