package com.labtrack.service;

import com.labtrack.domain.Usuario;
import com.labtrack.repository.UsuarioRepository;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Busca en la tabla usuario el registro con el correo pasado por parámetro
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreoAndActivoTrue(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        //LabTrack solo tiene un rol por usuario (tipoUsuario), a diferencia de Tienda
        Set<SimpleGrantedAuthority> roles = Set.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario())
        );

        return new User(usuario.getCorreo(), usuario.getPassword(), roles);
    }
}
