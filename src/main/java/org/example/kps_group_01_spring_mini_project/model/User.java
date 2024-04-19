package org.example.kps_group_01_spring_mini_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {
    private UUID userId;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String profileImage;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @JsonIgnore
    @Override
    public String getUsername() {return email;}

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {return true;}

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {return true;}

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @JsonIgnore
    @Override
    public boolean isEnabled() {return true;}
}
