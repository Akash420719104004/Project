package com.lms.project.configure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.project.Service.Authservice;
import com.lms.project.dto.DecodeTokenDto;
import com.lms.project.dto.UserContextDto;
import com.lms.project.dto.UserContextHolder;
import com.lms.project.model.User;
import com.lms.project.repository.UserRepository;
import com.lms.project.utils.Constants;
import com.lms.project.utils.JWTUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.*;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtCustomUserDetailsService jwtUserDetailsService;
    @Autowired
    JWTUtils jwtTokenUtil;
    @Value("${jwt.secret}")
    private String pk;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Authservice authservice;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (shouldSkipFilter(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String jwtToken = extractJwtToken(request.getHeader("Authorization"));
            if (jwtToken != null) {
                processJwtToken(jwtToken, request);
            } else {
                throw new ServletException("JWT Token does not begin with Bearer String");
            }
            filterChain.doFilter(request, response);
        } catch (ServletException servletException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*"); // Set the appropriate origin or allow all for testing
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("statusCode", response.getStatus());
            errorResponse.put("statusMessage", servletException.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (Exception exception) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setHeader("Access-Control-Allow-Origin", "*"); // Set the appropriate origin or allow all for testing
            Map<String, Object> exceptionResponse = new HashMap<>();
            exceptionResponse.put("statusCode", response.getStatus());
            exceptionResponse.put("statusMessage", exception.getMessage());
            response.getWriter().write(new ObjectMapper().writeValueAsString(exceptionResponse));
        }
    }
    private boolean shouldSkipFilter(HttpServletRequest request) {
        return checker(request) || StringUtils.isBlank(request.getHeader(Constants.AUTHORIZATION));
    }
    private String extractJwtToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
    private void processJwtToken(String jwtToken, HttpServletRequest request) throws ServletException {
        try {
            String username = getUsernameFromJwtToken(jwtToken);
//            String username = jwtTokenUtil.getEmail(jwtToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Optional<User> user = Optional.ofNullable(authservice.getUser(username));
                if (user != null) {
                    UserDetails userDetails = createUserDetails(username, user.get().getPassword());
                    if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(jwtToken, userDetails))) {
                        authenticateWithJwtToken(userDetails, request);
                    } else {
                        throw new ServletException("Access Token is invalid");
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            logger.warn("Access Token has expired: " + e.getMessage());
            throw new ServletException("Access Token has expired");
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }
    private String getUsernameFromJwtToken(String jwtToken) throws JsonProcessingException {
      //  DecodeTokenDto dto = extractTokenDto(jwtToken);
        DecodeTokenDto dto = extractTokenDto(jwtToken);
        if (dto.getSub() != null) {
            Optional<User> user = userRepository.findByEmail(dto.getSub());
            user.ifPresent(u -> {
                UserContextDto userDTO = new UserContextDto();
                userDTO.setId(u.getId());
                userDTO.setUserName(u.getUserName());
                userDTO.setEmail(u.getEmail());
                userDTO.setMobileNumber(u.getMobile());
                userDTO.setDob(String.valueOf(u.getDob()));
                userDTO.setUserType(u.getUserType());
                UserContextHolder.setUserDto(userDTO);
            });
            return dto.getSub();
        }
        return null;
    }
    private DecodeTokenDto extractTokenDto(String jwtToken) throws JsonProcessingException {
        String[] split = jwtToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(split[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        return objectMapper.readValue(payload, DecodeTokenDto.class);
    }
    private UserDetails createUserDetails(String username, String password) {
        return new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
    }
    private void authenticateWithJwtToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = jwtTokenUtil.getAuthentication(userDetails);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    private boolean checker(HttpServletRequest request) {
        String uri = request.getRequestURI();
        List<String> startWithPaths = List.of(
                "/actuator/health",
                "/v3/api-docs",
                "/configuration",
                "/swagger-ui/index.html",
                "/swagger-ui/swagger-ui.css",
                "/swagger-ui/index.css",
                "/swagger-ui/swagger-initializer.js",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/favicon-16x16.png",
                Constants.USER_SIGNUP,
                "/favicon.ico",
                Constants.LOGIN,
                Constants.REFRESH,
                "/getHi",
                "/save",
                "/get",
                "/update"
        );
        List<String> exactMatchPaths = List.of(
                "/v3/api-docs/**",
                "/user/save",
                "/v3/api-docs/swagger-config"
        );
        for (String path : startWithPaths) {
            if (uri.startsWith(path)) {
                return true;
            }
        }
        for (String path : exactMatchPaths) {
            if (uri.equalsIgnoreCase(path)) {
                return true;
            }
        }
        return false;
    }
}