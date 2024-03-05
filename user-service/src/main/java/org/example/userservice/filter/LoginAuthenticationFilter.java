package org.example.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.UTF8;
import org.example.userservice.config.security.provider.TokenProvider;
import org.example.userservice.model.payload.request.LoginRequest;
import org.example.userservice.model.payload.response.LoginResponse;
import org.example.userservice.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    public LoginAuthenticationFilter(MemberService memberService, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.memberService = memberService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    creds.getUserId(),
                    creds.getPassword()
            );

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Failed to read request body", e);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Authentication failed", e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String userName = (String) authResult.getPrincipal();
        LoginResponse loginResponse= tokenProvider.generateTokenDto(authResult);

        response.addHeader("accessToken", loginResponse.getAccessToken());
        response.addHeader("refreshToken", loginResponse.getRefreshToken());

        Map<String, String> data = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        data.put("message", "Authentication successful");

        objectMapper.writeValue(response.getWriter(), data);
    }
}
