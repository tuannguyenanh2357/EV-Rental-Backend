package com.example.Buoi_1.service;

import com.example.Buoi_1.dto.request.AuthenticationRequest;
import com.example.Buoi_1.dto.request.IntrospectRequest;
import com.example.Buoi_1.dto.response.AuthenticationResponse;
import com.example.Buoi_1.dto.response.IntrospectResponse;
import com.example.Buoi_1.exception.AppException;
import com.example.Buoi_1.exception.ErrorCode;
import com.example.Buoi_1.repository.UserRepository;
import com.example.Buoi_1.entity.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        String username = request.getUsername() == null ? null : request.getUsername().trim();
        String rawPassword = request.getPassword();

        if (username == null || username.isBlank() || rawPassword == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        var user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        String storedPassword = user.getPassword();
        boolean isBcryptHash = storedPassword != null && storedPassword.startsWith("$2");

        boolean authenticated = storedPassword != null && passwordEncoder.matches(rawPassword, storedPassword);

        log.info("Login attempt user='{}', bcryptStored={}, bcryptMatch={}", username, isBcryptHash, authenticated);

        // Support legacy plain-text passwords once, then migrate them to BCrypt.
        if (!authenticated && storedPassword != null && rawPassword.equals(storedPassword)) {
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
            authenticated = true;
            log.info("Migrated plain-text password to BCrypt for user='{}'", username);
        }

        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);
        return AuthenticationResponse.builder()

                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        String userRole = user.getRole();
        if (userRole == null || userRole.isBlank()) {
            userRole = "customer";
        }

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("tuan")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", userRole)
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
}
