

package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {
    long contarEstudiantes();

    List<UsuarioEntity> listarSoloEstudiantes();
    UsuarioEntity registrarUsuario(UsuarioEntity usuario);
    //Aun no implementado
    UsuarioEntity buscarPorCodigo(String codigo);
    UsuarioEntity buscarPorCorreoInstitucional(String correo);
}
