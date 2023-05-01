package com.EMOS.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.EMOS.DataTransferObject.AuthCredentialsRequest;
import com.EMOS.Util.JwtUtil;
import com.EMOS.domain.User;

import io.jsonwebtoken.ExpiredJwtException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("login")
	public ResponseEntity <?> login (@RequestBody AuthCredentialsRequest request) {
        try {
            Authentication authenticate = authenticationManager
                .authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                    )
                );

            User user = (User) authenticate.getPrincipal();
            
           user.setPassword(null);

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    jwtUtil.generateToken(user)
                )
                .body(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
	
	@GetMapping("/validate")
	public ResponseEntity<?> validateToken (@RequestParam String token, @AuthenticationPrincipal User user){
		try {
					Boolean isTokenValid = jwtUtil.validateToken(token, user);
					return ResponseEntity.ok(isTokenValid);
		}
		catch(ExpiredJwtException e) {
					return ResponseEntity.ok(false);
		}
	}}
	/*@GetMapping("/role")
	public ResponseEntity<?> getUserRole(@AuthenticationPrincipal User user) {
		String role = user.getRole();
		return ResponseEntity.ok(role);
	}
	
}*/
