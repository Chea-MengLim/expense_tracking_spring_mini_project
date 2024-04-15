package org.example.kps_group_01_spring_mini_project.Repository;

import org.apache.ibatis.annotations.*;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.User;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper
public interface UserRepository {
    @Select("""
           SELECT * FROM users WHERE email = #{email}
           """)
    @Results(id = "userMap", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "profileImage", column = "profile_image")
    })
    UserDetails findByEmail(String email);

    @Select("""
        INSERT INTO users VALUES (default, #{user.email}, #{user.password}, #{user.profileImage})
        RETURNING *
    """)
    @ResultMap("userMap")
    User register(@Param("user") UserRequest userRequest);
}
