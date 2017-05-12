/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucas.crud.crudCadastroCliente.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author tiano
 */
public class ConexaoUtil {
    
    private static ResourceBundle configDB = ResourceBundle.getBundle(Constantes.CONEXAO_BD_PROPERTIES);

      public static Connection getConexao() throws SQLException, ClassNotFoundException {
		
	 Class.forName(configDB.getString(Constantes.CONEXAO_DB_DRIVER));
	  return DriverManager.getConnection(configDB.getString(Constantes.CONEXAO_DB_URL), configDB.getString(Constantes.CONEXAO_DB_USER),
            configDB.getString(Constantes.CONEXAO_BD_PASSWORD));
	}
      
      	public static void main(String[] args) {
		  try{
			System.out.println(getConexao()); 
		  }catch(ClassNotFoundException | SQLException e){
			  e.printStackTrace();
		  }
		}
}
