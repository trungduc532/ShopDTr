package com.shopdtr.web.backend.security;

import com.shopdtr.web.backend.entity.Role;
import com.shopdtr.web.backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ShopDtrUserDetails implements UserDetails {

    public ShopDtrUserDetails(User user) {
        this.user = user;
    }

    private User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isEnable();
    }

    public void setFirstName(final String firstName){
        user.setFirstName(firstName);
    }

    public void setLastName(final String lastName) {
        user.setLastName(lastName);
    }

    /**
     * Get full name of user
     * @return
     */
    public String getFullname() {
        return this.user.getFirstName() + " " + this.user.getLastName();
    }
}
