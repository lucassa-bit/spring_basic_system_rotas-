package com.AEDsistema.relatorioMVC.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTValidationFilter extends BasicAuthenticationFilter {
	
	public JWTValidationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	public static final String HEADER_ATRIBUTO = "Authorization";
	public static final String ATRIBUTO_PREFIXO = "Bearer ";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String atributo = request.getHeader(HEADER_ATRIBUTO);
		if (atributo == null || !atributo.startsWith(ATRIBUTO_PREFIXO)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = atributo.replace(ATRIBUTO_PREFIXO, "");
		
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String login = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_SENHA))
				.build()
				.verify(token)
				.getSubject();
		
		if (login == null) return null;
        
		return new UsernamePasswordAuthenticationToken(login, "", new ArrayList<>());
	}

}

