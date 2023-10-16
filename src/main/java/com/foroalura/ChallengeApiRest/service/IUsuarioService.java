package com.foroalura.ChallengeApiRest.service;

import com.foroalura.ChallengeApiRest.model.Usuario;


import java.util.List;

public interface IUsuarioService {

    //Metodo para traer a todas los usuarios de la Base de Datos
    public List<Usuario> getUsuarios();

    //Metodo para dar de alta a un usuario
    public void saveUsuario(Usuario user);

    //Metodo para guardar varios usuarios

    public void saveUsuarios(List<Usuario> listaUsers);

    //Metodo para borrar a una usuario
    public void deleteUsuario(long id);

    //Metodo para traer a una solo usuario
    public Usuario findUsuario(long id);

    public boolean usuarioConPermiso(String username, Long id);

    //Metodo para modificar los datos de un usuario, debe recibir el objeto completo.

    void editUsuario2(Usuario usu);
}


