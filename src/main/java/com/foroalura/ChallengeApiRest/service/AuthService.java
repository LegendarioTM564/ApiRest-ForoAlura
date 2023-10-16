package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.auth.AuthResponse;
import com.foroalura.ChallengeApiRest.auth.LoginRequest;
import com.foroalura.ChallengeApiRest.auth.RegisterRequest;
import com.foroalura.ChallengeApiRest.jwt.JwtService;
import com.foroalura.ChallengeApiRest.model.Role;
import com.foroalura.ChallengeApiRest.model.Usuario;
import com.foroalura.ChallengeApiRest.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{

    @Autowired
    IUsuarioRepository iUsuarioRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    IUsuarioService iUsuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        UserDetails user = iUsuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        Usuario user = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .curso(request.getCurso())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .rol((Role.USER))
                .build();
        iUsuarioService.saveUsuario(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }



}
