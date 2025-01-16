package com.example.demo.controller;

import com.example.demo.domain.Camping;
import com.example.demo.domain.Member;
import com.example.demo.service.CampingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RecommendationController {

    @Autowired
    CampingService campingService;

    @GetMapping("/campings")
    public String getCampingRecommendations(
            HttpSession session,
            @RequestParam(value = "numRecommendations", defaultValue = "9") int numRecommendations,
            @RequestParam(value = "reviewWeight", defaultValue = "0.5") double reviewWeight,
            @RequestParam(value = "reservationWeight", defaultValue = "0.3") double reservationWeight,
            @RequestParam(value = "wishlistWeight", defaultValue = "0.2") double wishlistWeight, Model model) {

        // 세션에서 로그인 유저 확인
        Member loginUser = (Member) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        if (loginUser == null) {
            System.out.println("로그인 안되어있음");
            return  "Camping/cammpinglist";
        }

        int userId = Math.toIntExact(loginUser.getId()); // Member 객체에서 사용자 ID 추출

        try {
            // Python 스크립트 실행
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python",
                    "E:/Student/MachineLearning/recommend.py",
                    String.valueOf(userId),
                    String.valueOf(numRecommendations),
                    String.valueOf(reviewWeight),
                    String.valueOf(reservationWeight),
                    String.valueOf(wishlistWeight)
            );
            Process process = processBuilder.start();

            // Python 스크립트 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.out.println("Python 스크립트 실행 오류: " + exitCode);
                return  "Camping/landingPage";
            }

            // Python 출력값 처리
            String result = output.toString();
            System.out.println("Python Output: " + result);

            // 결과가 JSON이 아니라고 가정하고 처리
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> response = new HashMap<>();
            if (result.startsWith("[")) {
                // 리스트 형식일 경우
                List<?> parsedList = objectMapper.readValue(result, List.class);
                response.put("recommendations", parsedList);
            } else {
                // 단순 문자열일 경우
                response.put("message", result);
            }

            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher recommend = pattern.matcher(result);

            if (recommend.find()) {
                // 매칭된 그룹 출력
                String extracted = recommend.group(1);
                System.out.println("추출된 숫자 리스트: " + extracted);
            } else {
                System.out.println("매칭된 숫자 리스트를 찾을 수 없습니다.");
            }

            String[] recommendIds = recommend.group(1).split(", ");

            System.out.println("recommendIds: " + Arrays.toString(recommendIds));

            List<Camping> tmpPlaces = new ArrayList<>();

            for (String id : recommendIds){
                Camping tmp = campingService.findById(Long.valueOf(id));
                tmpPlaces.add(tmp);
            }

            List<Camping> firstPlace = new ArrayList<>();
            List<Camping> secondPlace = new ArrayList<>();
            List<Camping> thirdPlace = new ArrayList<>();

            for (int a = 0; a < 9; a++) {
                if (a < 3) {
                    firstPlace.add(tmpPlaces.get(a));
                } else if (a < 6) {
                    secondPlace.add(tmpPlaces.get(a));
                } else {
                    thirdPlace.add(tmpPlaces.get(a));
                }
            }

            model.addAttribute("firstPlace", firstPlace);
            model.addAttribute("secondPlace", secondPlace);
            model.addAttribute("thirdPlace", thirdPlace);


            return "Camping/Recommend";

        } catch (Exception e) {
            e.printStackTrace();
            return "Camping/landingPage";
        }



    }
}
