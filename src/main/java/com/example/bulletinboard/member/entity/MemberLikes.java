package com.example.bulletinboard.member.entity;

import com.example.bulletinboard.likes.entity.Likes;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MemberLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "LIKES_ID")
    private Likes likes;

    //딱히 안 필요한듯
    public static MemberLikes createMemberLikes(Member member, Likes likes) {
        MemberLikes memberLikes = new MemberLikes();
        memberLikes.createMemberLikes(member, likes);

        //변경 필요
        return null;
    }


}
