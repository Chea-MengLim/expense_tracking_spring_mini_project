package org.example.kps_group_01_spring_mini_project.repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.config.UUIDTypeHandler;
import org.example.kps_group_01_spring_mini_project.model.Otp;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.ForgetPasswordRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface UserRepository {
    @Select("""
           SELECT * FROM users WHERE email = #{email}
           """)
    @Results(id = "userMap", value = {
            @Result(property = "userId", column = "user_id", javaType = UUID.class, typeHandler = UUIDTypeHandler.class),
            @Result(property = "profileImage", column = "profile_image")
    })
    User findByEmail(String email);

    @Select("""
        INSERT INTO users VALUES (default, #{user.email}, #{user.password}, #{user.profileImage})
        RETURNING *
    """)
    @ResultMap("userMap")
    User register(@Param("user") UserRequest userRequest);

    @Insert("""
        INSERT INTO otps VALUES (default, #{otp}, #{issued}, #{expiration}, #{verify}, #{userId})
    """)
    void insertUserOtp(String otp, Timestamp issued, Timestamp expiration, Boolean verify, UUID userId);


    @Select("""
        UPDATE otps
        SET verify = #{b}
        WHERE otp_code = #{otp}
    """)
    void verifyUserOtpByOtpCode(String otp, boolean b);


    @Select("""
        SELECT * FROM users WHERE email = #{email}
    """)
    @ResultMap("userMap")
    User getUserByEmail(String email);

    @Select("""
        SELECT * FROM otps WHERE user_id = #{id} ORDER By issued_at DESC LIMIT 1
    """)
    @Results(id = "otpMap", value = {
            @Result(property = "otpCode", column = "otp_code"),
            @Result(property = "issuedAt", column = "issued_at"),
    })
    Otp getOtpByUserId(@Param("id") UUID id);

    @Select("""
        SELECT email FROM users;
    """)
    List<String> getAllEmail();

    @Select("""
        SELECT * FROM otps WHERE otp_code = #{otp}
    """)
    @ResultMap("otpMap")
    Otp findOtpByOtpCode(String otp);

    @Select(("""
        UPDATE users SET password = #{user.password} WHERE email = #{email}
    """))
    void updatePassword(String email, @Param("user") ForgetPasswordRequest forgetPasswordRequest);
}
