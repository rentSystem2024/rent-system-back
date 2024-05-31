package com.rentcar.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentcar.back.entity.UserEntity;

// Estate 데이터베이스의 user 테이블의 작업을 위한 리포지토리 ( 인터페이스로 작업 )
// JPA 레포지토리를 쓰고있기 때문에 JpaRepository<클래스,기본키로 지정한 값의 타입> 확장해줘야함
@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByUserId(String userId);
    UserEntity findByNickName(String nickName);
    UserEntity findByUserEmail(String userEmail);
    UserEntity findByUserPassword(String userPassword);
    boolean existsByUserEmail(String userEmail);
    boolean existsByUserId(String userId);
    boolean existsByNickName(String nickName);

    boolean existsByUserIdAndUserEmail(String userId, String userEmail);

    String findUserIdByUserEmail(String userEmail);
    String findUserPwByUserId(String userId);

    // 아이디, 비밀번호 찾기
    // boolean unExistsByUserPassword(String userId);
    // boolean unExistsByUserEmail(String userEmail);

    List<UserEntity> findByOrderByJoinDateDesc();

    // Contains / Containing / IsContaining => LIKE '%word%'
    // StartingWith => LIKE 'word%'
    // EndingWith => LIKE '%word'
    List<UserEntity> findByUserIdContainsOrderByJoinDateDesc(String userId);



}
