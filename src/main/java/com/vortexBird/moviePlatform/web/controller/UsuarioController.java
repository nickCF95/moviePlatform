package com.vortexBird.moviePlatform.web.controller;

import com.vortexBird.moviePlatform.domain.Factura;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.domain.Usuario;
import com.vortexBird.moviePlatform.domain.dto.TentativaCompraDTO;
import com.vortexBird.moviePlatform.domain.dto.UsuarioPwdRecoveryDTO;
import com.vortexBird.moviePlatform.domain.dto.UsuarioRecoveryDTO;
import com.vortexBird.moviePlatform.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vortexBird.moviePlatform.web.utils.SecurityUtils.validateUser;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAll() {
        return new ResponseEntity<>(usuarioService.obtenerTodos(), HttpStatus.OK);
    }

    @GetMapping("/{nickname}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> getUser(@PathVariable("nickname") String nickname) {
        return usuarioService.obtenerUsuario(nickname)
                .map(Usuario -> new ResponseEntity<>(Usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/anyMatchField/{keyword}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getByAnyMatchField(@PathVariable("keyword") String keyword) {
        return usuarioService.obtenerPorCualquierCoincidencia(keyword)
                .map(usuarios -> new ResponseEntity<>(usuarios, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/purchases/{nickname}")
    public ResponseEntity<List<Factura>> getPurchasesOfTheUser(@PathVariable("nickname") String nickname) {
        return usuarioService.obtenerComprasRealizadasPorUsuario(nickname)
                .map(facturas -> new ResponseEntity<>(facturas, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/changePwd")
    public ResponseEntity changePassword(@RequestBody UsuarioPwdRecoveryDTO usuarioPwdRecoveryDTO) {
        if(usuarioService.changePwd(usuarioPwdRecoveryDTO)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/enable/{nickname}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity enable(@PathVariable("nickname") String nickname) {
        if(usuarioService.inhabilitar(nickname)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/disable/{nickname}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity disable(@PathVariable("nickname") String nickname) {
        if(usuarioService.inhabilitar(nickname)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/buyTickets")
    public ResponseEntity buyTickets(@RequestBody TentativaCompraDTO tentativaCompraDTO){
        validateUser(tentativaCompraDTO.getNickname());
        usuarioService.comprarEntradas(tentativaCompraDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/recoveryPwd")
    public ResponseEntity recoveryPassword(@RequestBody UsuarioRecoveryDTO usuarioRecoveryDTO){
        usuarioService.sendPasswordRecoveryEmail(usuarioRecoveryDTO);
        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody Usuario usuario) {
        usuarioService.registerUser(usuario);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update")
    public ResponseEntity update(@RequestBody Usuario usuario){
        if(usuarioService.update(usuario)){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }
    
    @DeleteMapping("/delete/{nickname}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable("nickname") String nickname) {
        if (usuarioService.delete(nickname)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
