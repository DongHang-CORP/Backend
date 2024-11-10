package org.example.food.user.dto;

import lombok.RequiredArgsConstructor;
import org.example.food.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return "null";
    }

    @Override
    public String getUsername() {

        return user.getUsername();
    }

    public String getNickname() {
        return user.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public Long getUserId() {
        return user.getId();
    }
}
