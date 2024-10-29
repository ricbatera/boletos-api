package br.com.consultdg.boletos_api.api.controllers;


import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.consultdg.boletos_api.domain.model.Permissoes;
import br.com.consultdg.boletos_api.domain.model.Usuario;
import br.com.consultdg.boletos_api.domain.repository.UsuarioRepository;
import br.com.consultdg.boletos_api.domain.to.LoginRequest;
import br.com.consultdg.boletos_api.domain.to.LoginResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin
@RestController
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    private final JwtEncoder jwtEncoder;
    
    public LoginController(JwtEncoder jwtEncoder){
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> postMethodName(@RequestBody LoginRequest request) {
        Optional<Usuario> user = userRepo.findByEmail(request.email());
        if(user.isEmpty() || !user.get().isLoginCorreto(request, passEncoder)){
           throw new BadCredentialsException("E-mail ou senha inválidos!");
        }

        var now = Instant.now();
        var expiresIn = 3000L;

        var userScope = user.get().getPermissoes()
                            .stream()
                            .map(Permissoes::getPermissao)
                            .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                        .issuer("ProcessInvoiceBackend")
                        .subject(user.get().getNome())
                        .expiresAt(now.plusSeconds(expiresIn))
                        .issuedAt(now)
                        .claim("scope", userScope)
                        .build();
        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(token,expiresIn, user.get().getId().toString(), user.get().getNome(), user.get().getSobrenome()));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleResponseStatusException(BadCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    

}
