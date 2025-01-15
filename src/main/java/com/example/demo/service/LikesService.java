package com.example.demo.service;

import com.example.demo.domain.Likes;

import java.util.List;

public interface LikesService {
    List<Likes> getLikesByMember(String memberId);

    void removeLike(String memberId, Long campingId);
}
