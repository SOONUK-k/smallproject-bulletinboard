package com.example.bulletinboard.likes.entity;

import com.example.bulletinboard.entity.BaseEntity;
import com.example.bulletinboard.member.entity.Member;
import com.example.bulletinboard.member.entity.MemberLikes;
import com.example.bulletinboard.question.entity.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Likes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "LIKES_ID")
    private Long id;

    private int likesCount;

    //    Likes는 question으로의 양방향 연관관계가 필요하지 않다
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Setter
    private Question question;


    @OneToMany(mappedBy ="likes")
    private List<MemberLikes> memberLikesList = new ArrayList<>();

    public void addMemberLikesList(MemberLikes memberLikes) {
        memberLikesList.add(memberLikes);
        memberLikes.getMember();
    }



}
