package com.edutech.progressive.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;   
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UserDetailsService userDetailsService,
                            JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = null;
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && !authHeader.trim().isEmpty()) {
            String val = authHeader.trim();
            if (val.regionMatches(true, 0, "Bearer ", 0, "Bearer ".length())) {
                token = val.substring("Bearer ".length()).trim();
            } else {
                token = val;
            }
        }

        if (token == null || token.isEmpty()) {
            String headerToken = request.getHeader("token");
            if (headerToken != null && !headerToken.trim().isEmpty()) {
                token = headerToken.trim();
            }
        }

        if (token == null || token.isEmpty()) {
            String paramToken = request.getParameter("token");
            if (paramToken != null && !paramToken.trim().isEmpty()) {
                token = paramToken.trim();
            }
        }

        try {
            if (token != null && !token.isEmpty()) {
                String username = null;
                try {
                    username = jwtUtil.extractUsername(token);
                } catch (Exception ignored) {
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        } catch (Exception ignored) {
        }

        chain.doFilter(request, response);
    }
}
