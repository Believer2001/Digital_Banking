package enset.ma.digital_banking_jee_angular.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor // pour l'injection des dependence vu que  Autowired est deprecieted
public class SecurityController {


    private AuthenticationManager authenticationManager;

    private JwtEncoder jwtEncoder;

    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication)
    {
        return  authentication;
    }

    @PostMapping("/login")
    public Map<String,String> login(String username,String password)
    {
     Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
             username,password));

        Instant instant =Instant.now();

     String  scope= authentication.getAuthorities().stream()
                        .map(a->a.getAuthority() )
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimSet= JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters =
                JwtEncoderParameters.from(
                        JwsHeader.with(MacAlgorithm.HS512).build(),
                        jwtClaimSet
                );
        String jwt =jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
     return  Map.of("access_token",jwt);
    }
}
