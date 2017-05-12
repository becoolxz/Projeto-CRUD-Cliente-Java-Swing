/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucas.crud.crudCadastroCliente.view;

import javax.swing.JFrame;

/**
 *
 * @author tiano
 */
public class CrudCliente {
    
    
    public static void main(String[] args) {
      
      JFrame janela = new JFrame("CRUD DE CLIENTES ");
      janela.setBounds(500, 50, 700, 650);
      janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      PainelCadastro painelCadastro = new PainelCadastro(janela);
      janela.add(painelCadastro);
      janela.setVisible(true);

    }
}
