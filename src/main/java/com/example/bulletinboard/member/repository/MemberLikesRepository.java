package com.example.bulletinboard.member.repository;

import com.example.bulletinboard.member.entity.MemberLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikesRepository extends JpaRepository<MemberLikes, Long> {
}
