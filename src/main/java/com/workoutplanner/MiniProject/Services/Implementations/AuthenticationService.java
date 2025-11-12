package com.workoutplanner.MiniProject.Services.Implementations;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.InvalidatedToken;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Payload.Request.AuthenticationRequest;
import com.workoutplanner.MiniProject.Payload.Request.InstrospectTokenRequest;
import com.workoutplanner.MiniProject.Payload.Request.LogoutRequest;
import com.workoutplanner.MiniProject.Payload.Request.RefreshRequest;
import com.workoutplanner.MiniProject.Payload.Response.AuthenticationResponse;
import com.workoutplanner.MiniProject.Payload.Response.IntrospectTokenResponse;
import com.workoutplanner.MiniProject.Repositories.InvalidatedTokenRepository;
import com.workoutplanner.MiniProject.Repositories.RoleRepository;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    @NonFinal
    @Value("${jwt.valid-duration}")
    private long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    private long REFRESH_DURATION;

    @Autowired
    private RoleRepository roleRepository;

    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        // Lay thong tin email cua user
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Match password user input voi password ban dau cua user
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPasswordHash(), user.getPasswordHash());

        if(!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Neu authenticate thanh cong -> Gen token
        var token = generateToken(user);
        return new AuthenticationResponse(token);
    }

    // Method de tao token
    // Nó “đóng gói” thông tin của user (như email, role, thời gian hiệu lực, ...) vào trong token JWT để gửi về cho client sau khi đăng nhập thành công.
    private String generateToken(User user) {

        // Tao Header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // Lay ten Role
        String roleName = user.getRole() != null ? user.getRole().getRoleName() : "USER";

        // Tao Claim
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                // Ai là chủ thể (subject)
                .subject(user.getEmail())
                // Ai phát hành token
                .issuer("workoutplanner")
                // Thời gian phát hành
                .issueTime(new Date())
                // Thời gian hết hạn
                .expirationTime(new Date(
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli() // Hard code
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("role", roleName)
                .build();

        // Tao Payload
        // convert claim set sang json
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // Ki token
        // MACSigner -> Khoa ki = khoa giai ma
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        // Doc thong tin token de lay ra id (JIT) va lay luon expiryTime
        try {
            // sign out can check theo thoi gian refresh token
            // neu ko check thi token se nhan exception -> logout roi nhung van co the lay token do verify
            var signToken = verifiedToken(request.getToken(), true);

            // Lay JWT ID
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            // Luu xuong
            InvalidatedToken invalidatedToken = new InvalidatedToken();
            invalidatedToken.setId(jit);
            invalidatedToken.setExpiryTime(expiryTime);

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException e) {
            System.out.println("Token already expired!");
        }
    }

    private SignedJWT verifiedToken(String token, boolean isRefresh) throws JOSEException, ParseException {

//        Tao verifier de kiem tra chu ki
//        SIGNER_KEY la secret key de ki khi token tao ra
//        MACVerifier xac thuc chu ki
        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

//        Parse Token
//        Bien token thanh 1 object co the truy cap vao duoc
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Lay thoi gian het han
        // isRefresh danh dau dung de refresh token
        Date expirationDate = (isRefresh)
                // Neu dung de refresh token
                // Thoi gian het han = Token luc issue + thoi gian co the refresh (seconds)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime().
                toInstant().plus(REFRESH_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                // Ko thi expDate = thoi gian exp luc issue token
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        // Xac minh chu ky
        // new verified = true -> token chua bi chinh sua
        var verified = signedJWT.verify(verifier);

        // Ktra xem token hop le va chua het han
        if(!verified && expirationDate.after(new Date()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        // Neu da ton tai trong bang -> Loi
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }


    // Kiem tra token co hop le hay ko
    public IntrospectTokenResponse introspect(InstrospectTokenRequest request) throws ParseException, JOSEException {
        // Lay token tu request cua nguoi dung gui len
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifiedToken(token, false);

        } catch (AppException e) {
            isValid = false;
        }
        IntrospectTokenResponse response = new IntrospectTokenResponse();
        response.setValid(isValid);
        return response;
    }

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        // Lay token
        var signJWT = verifiedToken(request.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        // Neu con han thi logout
        InvalidatedToken invalidatedToken = new InvalidatedToken();
        invalidatedToken.setId(jit);
        invalidatedToken.setExpiryTime(expiryTime);
        invalidatedTokenRepository.save(invalidatedToken);

        // Het han thi generate cai moi
        // Lay user
        var email = signJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Gen token
        var token = generateToken(user);
        return new AuthenticationResponse(token);
    }
}