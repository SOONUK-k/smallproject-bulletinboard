package com.example.bulletinboard.member.entity;

import com.example.bulletinboard.likes.entity.Likes;
import com.example.bulletinboard.question.entity.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    //멤버를 통해서 좋아요 접근? 딱히 안할꺼같은데 지워도 무방할듯
    @OneToMany(mappedBy = "member")
    private List<MemberLikes> memberLikesList = new ArrayList<>();

    //M->Q 접근 => 양방향 연관관계가 필요한거같음
    @OneToMany(mappedBy = "member")
    private List<Question> questionList = new ArrayList<>();

    private Member(String email, String name, String phone, MemberStatus memberStatus, MemberLevel memberLevel) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.memberStatus = memberStatus;
        this.memberLevel = memberLevel;
    }

    public void addQuestions(Question question) {
        questionList.add(question);
        question.setMember(this);
    }

    public static Member makeMember(String email, String name, String phone, MemberStatus memberStatus, MemberLevel memberLevel) {
        Member member = new Member(email, name, phone, memberStatus, memberLevel);

        return member;
    }



    private MemberStatus memberStatus;

    public enum MemberStatus {


        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }

    private MemberLevel memberLevel;

    public enum MemberLevel {


        MEMBER_LEVEL0("준회원"),
        MEMBER_LEVEL1("정회원"),
        MEMBER_LEVEL2("VIP"),
        MEMBER_ADMIN("관리자");

        @Getter
        private String status;

        MemberLevel(String status) {
            this.status = status;
        }
    }



}




