package co.edu.ufps.ayd.convocatoria.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import co.edu.ufps.ayd.convocatoria.security.filter.JwtAuthenticationFilter;
import co.edu.ufps.ayd.convocatoria.security.filter.JwtAuthorizationFilter;
import co.edu.ufps.ayd.convocatoria.security.jwt.JwtUtil;
import co.edu.ufps.ayd.convocatoria.service.interfaces.UsuarioInterface;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    
    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private UsuarioInterface usuarioService;

    @Autowired
    private JwtAuthorizationFilter authorizationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager)
            throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");

        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/usuario/guardarEvaluador", "/usuario/guardarProponente", "/usuario/reestablecerContrasenia").permitAll();
                    auth.requestMatchers("/convocatoria/**", "/materia/**", "/profesor/**", "/semillero/**", "/propuesta/**", "/usuario/listarEvaluadores",
                                            "/usuario/inhabilitarEvaluador", "/propuesta/asignarEvaluador").hasAnyAuthority("ROL_ADMIN", "ROL_PROPONENTE");
                    auth.requestMatchers("/materia/listarActivas", "/profesor/listarActivos", "/semillero/listarActivos", "/propuesta/guardarArchivo"
                                            ,"/propuesta/registrar", "/propuesta/buscarRegistradas").hasAuthority("ROL_PROPONENTE");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder)
            throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }
}
