package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.TokenUsuario;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.domain.dto.UsuarioPwdRecoveryDTO;
import com.vortexBird.moviePlatform.domain.service.TokenUsuarioService;
import com.vortexBird.moviePlatform.domain.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.NoResultException;
import java.util.Date;

@RestController
@RequestMapping("/public/token/")
public class TokenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private TokenUsuarioService tokenUsuarioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        TokenUsuario confirmationToken;
        try{
            confirmationToken = tokenUsuarioService.obtenerPorToken(token).orElseThrow(NoResultException::new);
        } catch (NoResultException e){
            return ResponseEntity.notFound().build();
        }
        if (confirmationToken == null) {
            return ResponseEntity.badRequest().body("Token invalido");
        }
        if (confirmationToken.getFechaExpiracion().before(new Date())) {
            return ResponseEntity.badRequest().body("Token expirado");
        }
        Usuario usuario = confirmationToken.getUsuario();
        usuario.setHabilitado(true);
        LOGGER.info("Usuario obtenido: " + usuario);
        usuarioService.update(usuario);
        tokenUsuarioService.delete(confirmationToken.getId());
        return ResponseEntity.ok("Usuario Confirmado");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> recoveryPassword(@RequestParam("token") String token, @RequestBody UsuarioPwdRecoveryDTO usuarioPwdRecoveryDTO) {
        TokenUsuario recoveryPwdToken;
        try{
            recoveryPwdToken = tokenUsuarioService.obtenerPorToken(token).orElseThrow(NoResultException::new);
        } catch (NoResultException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        if (recoveryPwdToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        if (recoveryPwdToken.getFechaExpiracion().before(new Date())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        if (usuarioPwdRecoveryDTO.getAlias().isEmpty()){
            usuarioPwdRecoveryDTO.setAlias(recoveryPwdToken.getUsuario().getAlias());
        }
        usuarioService.changePwd(usuarioPwdRecoveryDTO);
        tokenUsuarioService.delete(recoveryPwdToken.getId());
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
