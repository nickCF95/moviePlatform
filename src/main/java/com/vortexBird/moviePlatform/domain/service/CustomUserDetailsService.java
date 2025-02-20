package com.vortexBird.moviePlatform.domain.service;

import com.vortexBird.moviePlatform.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UsuarioService usuarioService;  // Your JPA repository for User

    @Autowired
    public CustomUserDetailsService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user from DB (you must implement findByUsername in your repository)
        Usuario usuario = usuarioService.obtenerUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario No encontrado"));

        // Convert roles from your Role entity to GrantedAuthority objects
        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                usuario.getAlias(),
                usuario.getContrasena(),
                usuario.getHabilitado(),      // enabled
                true,                  // accountNonExpired
                true,                  // credentialsNonExpired
                true,                  // accountNonLocked
                authorities
        );
    }
}
