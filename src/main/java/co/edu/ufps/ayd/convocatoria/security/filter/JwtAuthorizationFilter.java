package co.edu.ufps.ayd.convocatoria.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import co.edu.ufps.ayd.convocatoria.security.jwt.JwtUtil;
import co.edu.ufps.ayd.convocatoria.service.implementations.UsuarioService;
import co.edu.ufps.ayd.convocatoria.service.interfaces.UsuarioInterface;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    
    private JwtUtil jwtUtil;

    private UsuarioInterface usuarioService;

    public JwtAuthorizationFilter(JwtUtil jwtUtils, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtils;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");

        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);

            if(jwtUtil.isTokenValid(token)){
                String username = jwtUtil.getUsernameFromToken(token);
                UserDetails userDetails = usuarioService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
