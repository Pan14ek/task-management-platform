package com.taskmanagement.auth.api;

import com.taskmanagement.auth.dto.LoginRequest;
import com.taskmanagement.auth.dto.LoginResponse;
import com.taskmanagement.auth.dto.LogoutResponse;
import com.taskmanagement.auth.dto.RefreshRequest;
import com.taskmanagement.auth.dto.RefreshTokenResponse;
import com.taskmanagement.auth.dto.RegisterRequest;
import com.taskmanagement.auth.dto.RegisterResponse;
import com.taskmanagement.auth.dto.UserInfoResponse;
import com.taskmanagement.auth.dto.ValidateResponse;
import com.taskmanagement.auth.service.AuthService;
import com.taskmanagement.auth.service.model.CreatedRefreshToken;
import com.taskmanagement.auth.service.model.GetUser;
import com.taskmanagement.auth.service.model.NewUser;
import com.taskmanagement.auth.service.model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller that handles user authentication, registration,
 * token validation, and user session management.
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private static final String BEARER_PREFIX = "Bearer ";
  private static final String AUTHORIZATION = "Authorization";
  private final AuthService authService;

  /**
   * Registers a new user.
   *
   * @param registerRequest contains the username and password for registration
   * @return the registered user's ID and username
   */
  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> registerNewUser(@RequestBody
                                                          RegisterRequest registerRequest) {
    NewUser newUser = new NewUser(registerRequest.username(), registerRequest.password());

    User registeredUser = authService.registerUser(newUser);

    return ResponseEntity.ok(
        new RegisterResponse(registeredUser.uuid(), registeredUser.username()));
  }

  /**
   * Authenticates a user and generates JWT access and refresh tokens.
   *
   * @param loginRequest contains the username and password
   * @return the access token and refresh token for the user
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginRequest) {
    User user = authService.getUser(new GetUser(loginRequest.username(), loginRequest.password()));

    CreatedRefreshToken refreshToken = authService.createRefreshToken(user);

    return ResponseEntity.ok(
        new LoginResponse(refreshToken.jwtToken(), refreshToken.refreshToken()));
  }

  /**
   * Refreshes the expired access token using a valid refresh token.
   *
   * @param refreshRequest contains the refresh token
   * @return a new access token and a new refresh token
   */
  @PostMapping("/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @RequestBody RefreshRequest refreshRequest) {
    CreatedRefreshToken refreshToken =
        authService.refreshAccessToken(refreshRequest.refreshToken());

    return ResponseEntity.ok(
        new RefreshTokenResponse(refreshToken.jwtToken(), refreshToken.refreshToken()));
  }

  /**
   * Logs out the user by invalidating the refresh token.
   *
   * @param token the Authorization header containing the access token
   * @return a response confirming successful logout
   */
  @PreAuthorize("isAuthenticated()")
  @PostMapping("/logout")
  public ResponseEntity<LogoutResponse> logout(
      @RequestHeader(value = AUTHORIZATION, required = false) String token) {
    if (token == null || !token.startsWith(BEARER_PREFIX)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new LogoutResponse("Missing or invalid Authorization header"));
    }

    authService.logoutUser(extractToken(token));

    return ResponseEntity.ok(new LogoutResponse("User logged out successfully"));
  }

  /**
   * Retrieves the authenticated user's information.
   *
   * @param token the Authorization header containing the access token
   * @return the user's ID and username
   */
  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public ResponseEntity<UserInfoResponse> getUserInfo(
      @RequestHeader(value = AUTHORIZATION, required = false) String token) {
    if (token == null || !token.startsWith(BEARER_PREFIX)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new UserInfoResponse(null, null));
    }

    User userByToken = authService.getUserByToken(extractToken(token));

    return ResponseEntity.ok(new UserInfoResponse(userByToken.uuid(), userByToken.username()));
  }

  /**
   * Validates the given JWT access token.
   *
   * @param token the Authorization header containing the access token
   * @return a response indicating whether the token is valid or not
   */
  @GetMapping("/validate")
  public ResponseEntity<ValidateResponse> validateToken(
      @RequestHeader(value = AUTHORIZATION, required = false) String token) {
    if (token == null || !token.startsWith(BEARER_PREFIX)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ValidateResponse("Missing or invalid Authorization header", false));
    }

    boolean isValidToken = authService.validateToken(extractToken(token));

    if (isValidToken) {
      return ResponseEntity.ok(new ValidateResponse("The token is valid", true));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new ValidateResponse("The token is invalid", false));
  }

  /**
   * Extracts the JWT token from the Authorization header.
   *
   * @param token the full Authorization header value
   * @return the extracted JWT token or null if the token is missing or invalid
   */
  private String extractToken(String token) {
    if (token == null || !token.startsWith(BEARER_PREFIX)) {
      return null;
    }

    return token.replace(BEARER_PREFIX, "").trim();
  }

}
