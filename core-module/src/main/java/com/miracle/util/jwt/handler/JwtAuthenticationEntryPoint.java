package com.miracle.util.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.dto.ErrorMessage;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        ErrorMessage errorMessage =
                ErrorMessage.builder().errorCode(999).errorMessage("error!!!").build();
        try {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            writer.write(objectMapper.writeValueAsString(errorMessage));
        } catch (Exception e) {
            log.error("응답 메시지 작성 실패", e);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
        response.getWriter().write(objectMapper.writeValueAsString(errorMessage));
    }
}
