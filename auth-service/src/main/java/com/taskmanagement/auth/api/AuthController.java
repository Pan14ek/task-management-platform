package com.taskmanagement.auth.api;

import com.taskmanagement.auth.dto.RegisterRequest;
import com.taskmanagement.auth.dto.RegisterResponse;
import com.taskmanagement.auth.service.AuthService;
import com.taskmanagement.auth.service.model.NewUser;
import com.taskmanagement.auth.service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

}
