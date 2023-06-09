package com.project.socialMedia.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.socialMedia.exception.AppUserNotFoundException;
import com.project.socialMedia.model.user.AppUser;
import com.project.socialMedia.model.user.AuthUser;
import com.project.socialMedia.service.AppUserService;
import com.project.socialMedia.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final AppUserService appUserService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil,
                     AppUserService appUserService){
        this.jwtUtil = jwtUtil;
        this.appUserService = appUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if(jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String email = jwtUtil.validateTokenAndRetrieveClaim(jwt);

                    Optional<AppUser> optionalUser = appUserService.getByEmail(email);
                    AuthUser authUser = new AuthUser(optionalUser.orElseThrow(
                            () -> new AppUserNotFoundException(email)));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(),
                                    authUser.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
