package com.marcusfromsweden.plantdoctor.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class SecurityTestUtil {

    public static void setAuthenticatedUser(String username, List<String> roles) {
        var authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    //        SecurityTestUtil.setAuthenticatedUser("testuser", List.of("ROLE_USER"));

    public static void clearContext() {
        SecurityContextHolder.clearContext();
    }
}
