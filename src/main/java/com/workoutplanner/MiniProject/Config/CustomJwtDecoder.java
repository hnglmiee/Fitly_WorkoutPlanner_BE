package com.workoutplanner.MiniProject.Config;

import com.nimbusds.jose.JOSEException;
import com.workoutplanner.MiniProject.Payload.Request.InstrospectTokenRequest;
import com.workoutplanner.MiniProject.Services.Implementations.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;


//  JwtDecoder làm đúng 2 việc chính:
//  1. Xác minh nguồn gốc của token:
//      Nếu token được tạo từ nguồn khác (không có cùng signerKey) → Signature sẽ sai → token bị từ chối (401 Unauthorized).

//  2. Xác minh thời gian sống của token: Nếu token đã hết hạn → từ chối luôn.

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            // Tạo một đối tượng request để gửi vào AuthenticationService.
            var request = new InstrospectTokenRequest(token);
            // Gọi AuthenticationService introspection (kiểm tra token có hợp lệ ko).
            var response = authenticationService.introspect(request);

            // Nếu token bị logout, sai chữ ký, hoặc hết hạn → ném ra JwtException.
            if (!response.isValid()) {
                throw new JwtException("Token invalid");
            }
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        // Nếu chưa khởi tạo decoder, tạo mới để chuẩn bị giải mã JWT
        if (Objects.isNull(nimbusJwtDecoder)) {
            // Tạo secret key từ chuỗi signerKey
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        // Giải mã token hợp lệ
        // Lấy ra toàn bộ thông tin trong token
        return nimbusJwtDecoder.decode(token);
    }
}
