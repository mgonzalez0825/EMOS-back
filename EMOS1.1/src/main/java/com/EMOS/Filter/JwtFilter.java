package com.EMOS.Filter;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.EMOS.Repository.UserRepository;
import com.EMOS.Util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(!StringUtils.hasText(header) || StringUtils.hasText(header) && !header.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String token = header.split(" ")[1].trim();
	
		//For this method a bunch of stuff was changed to static(JwtUtil; SecretKey, ExtractUser, ExtractClaim, ExtractAllClaim). If error occurs check here.
		UserDetails userDetails = userRepo.findByUsername(JwtUtil.extractUsername(token)).orElse(null);
		
		if ( !jwtUtil.validateToken(token, userDetails)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		UsernamePasswordAuthenticationToken
			authentication= new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());
		
		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request));
		
		
		//this is the authenticator
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);		
	}
	
}
