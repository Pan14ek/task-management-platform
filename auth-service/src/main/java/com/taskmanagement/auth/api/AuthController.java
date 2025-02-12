package com.taskmanagement.auth.api;

import com.taskmanagement.auth.dto.LoginRequest;
import com.taskmanagement.auth.dto.LoginResponse;
import com.taskmanagement.auth.dto.RegisterRequest;
import com.taskmanagement.auth.dto.RegisterResponse;
import com.taskmanagement.auth.dto.ValidateResponse;
import com.taskmanagement.auth.service.AuthService;
import com.taskmanagement.auth.service.model.CreatedRefreshToken;
import com.taskmanagement.auth.service.model.GetUser;
import com.taskmanagement.auth.service.model.NewUser;
import com.taskmanagement.auth.service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/auth/register")
  public ResponseEntity<RegisterResponse> registerNewUser(@RequestBody
                                                          RegisterRequest registerRequest) {
    NewUser newUser = new NewUser(registerRequest.username(), registerRequest.password());

    User registeredUser = authService.registerUser(newUser);

    return ResponseEntity.ok(
        new RegisterResponse(registeredUser.uuid(), registeredUser.username()));
  }

  @PostMapping("/auth/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
    User user = authService.getUser(new GetUser(loginRequest.username(), loginRequest.password()));

    CreatedRefreshToken refreshToken = authService.createRefreshToken(user);

    return ResponseEntity.ok(
        new LoginResponse(refreshToken.jwtToken(), refreshToken.refreshToken()));
  }

  @GetMapping("/auth/validate")
  public ResponseEntity<ValidateResponse> validateToken(
      @RequestHeader(value = "Authorization", required = false) String token) {

    if (token == null || !token.startsWith("Bearer ")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ValidateResponse("Missing or invalid Authorization header", false));
    }

    boolean isValidToken = authService.validateToken(token.replace("Bearer ", ""));

    if (isValidToken) {
      return ResponseEntity.ok(new ValidateResponse("The token is valid", true));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ValidateResponse("The token is invalid", false));
  }

}
