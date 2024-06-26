package com.rentcar.back.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import com.rentcar.back.dto.request.auth.FindPasswordResetRequestDto;
import com.rentcar.back.dto.request.auth.SignUpRequestDto;
import com.rentcar.back.dto.request.user.PatchUserRequestDto;
import com.rentcar.back.dto.request.user.PutEmailModifyRequestDto;
import com.rentcar.back.dto.request.user.PutPwModifyRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="user")
@Table(name="user") 
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserEntity {    
    
    @Id 
    private String userId; 
    private String nickName;      
    private String userPassword;
    private String userEmail;
    private String userRole;
    private String joinPath;
    private String joinDate;      

    public UserEntity(SignUpRequestDto dto) {

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String joinDate = simpleDateFormat.format(now);

        this.userId = dto.getUserId();
        this.nickName = dto.getNickName();      
        this.userPassword = dto.getUserPassword();
        this.userEmail = dto.getUserEmail();
        this.userRole = "ROLE_USER";
        this.joinPath = "HOME";     
        this.joinDate = joinDate;
    }
    
    public UserEntity (String userId, String nickName, String userPassword, String userEmail, String userRole, String joinPath) {

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String joinDate = simpleDateFormat.format(now);
        
        this.userId = userId;
        this.nickName = nickName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.joinPath = joinPath;
        this.joinDate = joinDate;
    }

    public void update(PatchUserRequestDto dto){
        this.nickName = dto.getNickName();
    }

    public void findPassword(FindPasswordResetRequestDto dto){
        this.userPassword = dto.getUserPassword();
    }

    public void findModify(PutPwModifyRequestDto dto){
        this.userPassword = dto.getUserPassword();
    }

    public void emailModify(PutEmailModifyRequestDto dto){
        this.userEmail = dto.getUserEmail();
    }
}

