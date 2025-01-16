package com.example.demo.service;

import com.example.demo.domain.Camping;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CampingService {
    public Camping findById(Long id);  // 반환 타입을 Optional에서 Camping으로 변경

    public Camping getCampingDetail(Long campingId);

    public Page<Camping> getAllCamping(int page, int size);

    public List<Camping> getTmpCamping();

}
