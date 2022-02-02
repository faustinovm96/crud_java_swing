/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema.modelo.dao;

import sistema.modelo.domain.Usuario;

/**
 *
 * @author Faustino
 */
public interface IUsuarioDao {
    public Usuario login(String usuario, String clave);
}
