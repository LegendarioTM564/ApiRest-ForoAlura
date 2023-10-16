package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.auth.AuthResponse;
import com.foroalura.ChallengeApiRest.auth.LoginRequest;
import com.foroalura.ChallengeApiRest.auth.RegisterRequest;

import java.util.List;

public interface IAuthService {

    public AuthResponse login(LoginRequest request);

    public AuthResponse register(RegisterRequest request);

}
