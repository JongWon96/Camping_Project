package com.example.demo.service;

import com.example.demo.domain.Review;
import com.example.demo.domain.Member;
import com.example.demo.domain.Camping;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.MemberRepository;
import com.example.demo.persistence.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CampingRepository campingRepository;

    @Value("${file.upload-dir}")
    private String uploadDir; // 파일 저장 경로

    // 후기 저장
    @Override
    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, MultipartFile imgFile) throws IOException {
        // 회원과 캠핑장 정보 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new RuntimeException("Camping not found"));

        // 이미지 파일 처리
        String imgPath = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            imgPath = saveImage(imgFile);  // 이미지를 서버에 저장하고 경로 반환
        }

        // Review 객체 생성
        Review review = Review.builder()
                .member(member)
                .camping(camping)
                .content(content)
                .rate(rate)
                .img(imgPath)  // 이미지 경로 설정
                .reviewdate(new Date())  // 현재 날짜로 설정
                .build();

        // 후기 저장
        return reviewRepository.save(review);
    }

    // 이미지를 서버에 저장하고 경로를 반환하는 메서드
    private String saveImage(MultipartFile imgFile) throws IOException {
        // 파일 이름 설정 (현재 시간 기반으로 고유한 이름 생성)
        String fileName = System.currentTimeMillis() + "_" + imgFile.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName); // 업로드 디렉토리 경로 및 파일 이름 생성

        // 디렉토리가 없으면 생성
        Files.createDirectories(path.getParent());
        // 파일 저장
        Files.write(path, imgFile.getBytes());

        // 저장된 파일 경로 반환 (웹에서 접근할 수 있는 경로)
        return "/uploads/" + fileName;  // 예: "/uploads/1632785738319_example.jpg"
    }

    // 부적절한 후기 관리자에게 알림 (예시)
    private void alertAdminOfInappropriateReview(Review review) {
        // 예시: 관리자에게 이메일 발송, 시스템 알림 등
        System.out.println("부적절한 후기 발견! 관리자가 확인해야 합니다. 리뷰 내용: " + review.getContent());
    }

    @Override
    public boolean hasReview(Long memberId, Long campingId) {
        // 해당 캠핑장에 대해 해당 유저가 후기를 작성했는지 체크
        return reviewRepository.existsByMemberIdAndCampingId(memberId, campingId);
    }
}
