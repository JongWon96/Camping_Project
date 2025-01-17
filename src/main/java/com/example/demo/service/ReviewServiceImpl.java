package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.domain.Review;
import com.example.demo.persistence.CampingRepository;
import com.example.demo.persistence.MemberRepository;
import com.example.demo.persistence.ReviewRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

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
    // 후기 저장
    @Override
    public Review saveReview(Long memberId, Long campingId, String content, Integer rate, MultipartFile imgFile, Integer danger) throws IOException {
        // 회원과 캠핑장 정보 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        Camping camping = campingRepository.findById(campingId).orElseThrow(() -> new RuntimeException("Camping not found"));

        // 이미지 파일 처리
        String imgPath = null;
        if (imgFile != null && !imgFile.isEmpty()) {
            imgPath = saveImage(imgFile);  // 이미지를 서버에 저장하고 경로 반환
        }

        // danger 값이 null이면 0으로 설정 (기본값)
        if (danger == null) {
            danger = 0;  // 기본값 0으로 설정
        }

        // Review 객체 생성
        Review review = Review.builder()
                .member(member)
                .camping(camping)
                .content(content)
                .rate(rate)
                .img(imgPath)  // 이미지 경로 설정
                .reviewdate(new Date())  // 현재 날짜로 설정
                .danger(danger)  // danger 값 설정
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
    //리뷰 보기
    public List<Review> getReviewsByCampingId(Long campingId) {
        // 특정 캠핑장에 대한 후기를 불러오는 로직
        return reviewRepository.findByCamping_Id(campingId);
    }

    // 기존 이미지 삭제 메소드
    public void deleteReviewImage(Review review) {
        if (review.getImg() != null && !review.getImg().isEmpty()) {
            File imageFile = new File(review.getImg());
            if (imageFile.exists()) {
                imageFile.delete();  // 이미지 파일 삭제
            }
        }
    }


    @Override
    public void updateReview(Long reviewId, String content, Integer rate, MultipartFile imgFile ,Integer danger) throws IOException {
        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당아이디를 찾을 수 없습니다"));

        // 수정된 내용 반영
        review.setContent(content);
        review.setRate(rate);
        review.setDanger(danger); // danger 값 설정


        // 새 이미지 파일이 있을 경우 처리
        if (imgFile != null && !imgFile.isEmpty()) {
            // 기존 이미지 삭제 (옵션: 기존 이미지도 삭제하려면 아래 메서드를 호출)
            if (review.getImg() != null && !review.getImg().isEmpty()) {
                deleteImageFile(review.getImg()); // 기존 이미지 삭제 메서드 (필요시 구현)
            }

            // 새 이미지 파일 저장
            String imgPath = saveImage(imgFile); // 새 이미지를 저장하고 경로 반환
            review.setImg(imgPath); // 이미지 경로를 리뷰에 설정
        }

        // 수정된 리뷰 저장
        reviewRepository.save(review);
    }



    private void deleteImageFile(String imgPath) throws IOException {
        // 기존 이미지 파일을 삭제하는 로직 (파일 시스템에서)
        Path path = Paths.get(uploadDir, imgPath); // 경로를 기반으로 파일을 찾음
        Files.deleteIfExists(path); // 파일이 존재하면 삭제
    }


    @Override
    public void deleteReview(Long reviewId) throws IOException {
        // 리뷰 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰 ID를 찾을 수 없습니다"));

        // 이미지 파일이 존재하면 삭제
        if (review.getImg() != null && !review.getImg().isEmpty()) {
            deleteImageFile(review.getImg()); // 이미지 삭제 메서드 호출
        }

        // 리뷰 삭제
        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId).orElseThrow(() -> new RuntimeException("Review not found"));	}

    @Override
    public void dangerReview(Review review) {
        reviewRepository.save(review);

    }





	

}