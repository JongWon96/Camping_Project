package com.example.demo.service;

import java.util.List;

public interface CampingService {

    // 도 목록 가져오기
    List<String> getAllDonm();

    // 특정 도의 시군구 목록 가져오기
    List<String> getSigungunmByDonm(String donm);
	
}
