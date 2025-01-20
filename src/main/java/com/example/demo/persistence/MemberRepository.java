package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Member;

import jakarta.transaction.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 사용자 ID와 비밀번호로 사용자 정보 찾기, findBy 뒤에 붙는 컬럼명은 첫글자가 대문자이어야 함
    Member findByMemberIdAndPassword(String memberId, String password);

    // 비밀번호 변경
    @Transactional  // 커밋, 롤백 처리
	@Modifying  // @Query와 UPDATE 사용할때 사용하는 어노테이션임
    @Query(value = "UPDATE member SET password=:password WHERE member_id=:memberId", nativeQuery = true)
    void changePassword(@Param("memberId") String memberId, @Param("password") String password);

    // 사용자 이름으로 검색 (부분 일치)
    List<Member> findMemberByNameContaining(String name);

    @Query(value="select * from member WHERE name LIKE %:name%", nativeQuery=true)
    public List<Member> getMemberList(@Param("name") String name);

    @Query(value="select * from member WHERE member_id=:memberId", nativeQuery=true)
    public Member findByMemberId(@Param("memberId") String memberId);

    @Query(value="select * from member WHERE name=:name AND phone=:phone", nativeQuery=true)
    public Member findByNameAndPhone(@Param("name") String uname, @Param("phone") String phone);

    @Query(value="select * from member WHERE member_id=:memberId AND name=:name AND phone=:phone", nativeQuery=true)
    public Member findByMemberIdAndNameAndPhone(@Param("memberId") String memberId, @Param("name") String name, @Param("phone") String phone);
 
}