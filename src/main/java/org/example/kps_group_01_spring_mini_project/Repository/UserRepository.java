package org.example.kps_group_01_spring_mini_project.Repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.Config.UUIDTypeHandler;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Otp;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.User;
import org.example.kps_group_01_spring_mini_project.Util.OtpUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        SELECT * FROM otps WHERE user_id = #{userId};
    """)
    @Results(id = "otpMap", value = {
            @Result(property = "otpCode", column = "otp_code"),
            @Result(property = "issuedAt", column = "issued_at"),
    })
    Otp findOtpByUserId(@Param("userId") UUID userId);

    @Select("""
        UPDATE otps
        SET verify = #{b}
        WHERE user_id = #{id}
    """)
    void verifyUserOtp(@Param("id") UUID id, boolean b);

    @Select("""
        UPDATE otps
        SET otp_code = #{otp}, issued_at = #{issues}
        WHERE user_id = #{userId};
    """)
    void resendVerify(UUID userId, String otp, LocalDateTime issues);

    @Select("""
        SELECT * FROM users WHERE email = #{email}
    """)
    @ResultMap("userMap")
    User getUserByEmail(String email);

    @Select("""
        SELECT * FROM otps WHERE user_id = #{id}
    """)
    @ResultMap("otpMap")
    Otp getOtpByUserId(@Param("id") UUID id);
}
