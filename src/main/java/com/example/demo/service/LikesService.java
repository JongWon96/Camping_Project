package com.example.demo.service;

import com.example.demo.domain.Likes;

import java.util.List;

public interface LikesService {
    List<Likes> getLikesByMember(String memberId);

    void saveLike(Long memberId, Long campingId);

    void removeLike(String memberId, Long campingId);

    void removeLike(Long memberId, Long campingId);

    boolean isLiked(Long memberId, Long campingId);
}
