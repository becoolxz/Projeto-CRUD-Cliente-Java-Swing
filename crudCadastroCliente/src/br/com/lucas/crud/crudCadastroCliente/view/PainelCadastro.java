/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucas.crud.crudCadastroCliente.view;

import br.com.lucas.crud.crudCadastroCliente.modelo.Pessoa;
import br.com.lucas.crud.crudCadastroCliente.util.ConexaoUtil;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tiano
 */
public class PainelCadastro extends JPanel implements ActionListener {

    JTable tabela = null;
    JScrollPane barraRolagem = null;
    DefaultTableModel modelo = new DefaultTableModel();

    JTextField campoNome = new JTextField(15);
    JTextField campoEmail = new JTextField(15);
    JTextField campoPesquisa = new JTextField(15);

    JButton botaoCadastro = null;
    JButton botaoPesquisar = null;
    JButton botaoUpdate = null;
    JButton botaoDelete = null;
    JButton botaoNovo = null;
    JButton botaoResetTable = null;

    Integer idSelecionado = 0;

    JComboBox combo = new JComboBox();

    public PainelCadastro(JFrame janela) {

        Container caixa = janela.getContentPane();
        caixa.setLayout(new BoxLayout(caixa, BoxLayout.Y_AXIS));

        /* ---------------- Pesquisa ------------------------- */
        JPanel painelPesq = new JPanel();
        botaoResetTable = new JButton("Atualizar Tabela");
        botaoResetTable.addActionListener(this);
        painelPesq.add(new JLabel("Pesquisa :"));
        botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.addActionListener(this);
        combo.addItem("Nome");
        combo.addItem("Email");
        painelPesq.add(campoPesquisa);
        painelPesq.add(new JLabel("Por: "));
        painelPesq.add(combo);
        painelPesq.add(botaoPesquisar);
        painelPesq.add(botaoResetTable);

        /* ------------------  Tabela -------------------------- */
        JPanel painelTabela = new JPanel();
        tabela = new JTable(modelo) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        ;
        }; 
	      
	      tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tabela.rowAtPoint(evt.getPoint());
                int col = tabela.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    idSelecionado = (Integer) tabela.getModel().getValueAt(tabela.getSelectedRow(), 0);
                    campoNome.setText((String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 1));
                    campoEmail.setText((String) tabela.getModel().getValueAt(tabela.getSelectedRow(), 2));
                    botaoCadastro.setVisible(false);
                    botaoUpdate.setVisible(true);
                    botaoNovo.setVisible(true);
                }
            }
        });

        barraRolagem = new JScrollPane(tabela);
        painelTabela.add(barraRolagem);
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Email");
        listarClientesDAO(modelo);

        /* ---------------  CRUD  ----------------- */
        // Cadastro
        JPanel painelCampos = new JPanel(new GridLayout(2, 2));

        JPanel painelBotao = new JPanel();

        botaoNovo = new JButton("Voltar Cadastro");

        painelCampos.add(new JLabel("Nome: "));
        painelCampos.add(campoNome);
        painelCampos.add(new JLabel("Email: "));
        painelCampos.add(campoEmail);

        botaoNovo.addActionListener(this);
        botaoNovo.setVisible(false);
        painelBotao.add(botaoNovo);
        botaoCadastro = new JButton("Cadastrar");
        botaoCadastro.addActionListener(this);
        painelBotao.add(botaoCadastro);

        // Update
        botaoUpdate = new JButton("Atualizar DADOS");
        botaoUpdate.addActionListener(this);
        botaoUpdate.setVisible(false);
        painelBotao.add(botaoUpdate);

        // Delete
        botaoDelete = new JButton("Deletar Cliente");
        botaoDelete.addActionListener(this);
        painelBotao.add(botaoDelete);

        /* ------------ Atribuindo os paineis --------- */
        caixa.add(painelPesq);
        caixa.add(painelTabela);
        caixa.add(painelCampos);
        caixa.add(painelBotao);

    }

    private void cadastrarClienteDAO(Pessoa pessoa) {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO CLIENTE(nome, email) VALUES (?,?)");
            PreparedStatement stm = conexao.prepareStatement(sql.toString());
            stm.setString(1, pessoa.getNome());
            stm.setString(2, pessoa.getEmail());
            stm.execute();

            JOptionPane.showMessageDialog(null, "Inserido : \n Nome: " + pessoa.getNome() + " \n Email: " + pessoa.getEmail());

        } catch (ClassNotFoundException e) {
            System.out.println("Não encontrou o driver conenector java !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro no comando de SQL !");
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void listarClientesDAO(DefaultTableModel modelo) {;
        modelo.setNumRows(0);
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE");
            Statement stm = conexao.createStatement();
            ResultSet resultSet = stm.executeQuery(sql.toString());
            while (resultSet.next()) {
                modelo.addRow(new Object[]{resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("email")});
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Não encontrou o driver conenector java !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro no comando de SQL !");
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Pessoa buscarPessoaDAO(String campoBusca) {
        Pessoa pessoa = null;
        modelo.setNumRows(0);
        System.out.println(campoBusca);
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM CLIENTE WHERE ");
            if (combo.getSelectedItem().equals("Nome")) {
                sql.append("NOME LIKE ?");
            } else if (combo.getSelectedItem().equals("Email")) {
                sql.append("EMAIL LIKE ?");
            }

            PreparedStatement stm = conexao.prepareStatement(sql.toString());
            stm.setString(1, "%" + campoBusca + "%");

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                pessoa = new Pessoa(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
                modelo.addRow(new Object[]{pessoa.getId(), pessoa.getNome(), pessoa.getEmail()});
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Não encontrou o driver conenector java !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro no comando de SQL !");
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pessoa;
    }

    private void atualizarDadosPessoaDAO(Pessoa pessoa) {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CLIENTE SET NOME = ?, EMAIL = ?");
            sql.append(" WHERE ID = ?");
            PreparedStatement stm = conexao.prepareStatement(sql.toString());
            stm.setString(1, pessoa.getNome());
            stm.setString(2, pessoa.getEmail());
            stm.setInt(3, pessoa.getId());
            stm.execute();

            JOptionPane.showMessageDialog(null, "Atualizado : \n Nome: " + pessoa.getNome() + " \n Email: " + pessoa.getEmail());

        } catch (ClassNotFoundException e) {
            System.out.println("Não encontrou o driver conenector java !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro no comando de SQL !");
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deletarPessoaDAO(Pessoa pessoa) {
        Connection conexao = null;
        try {
            conexao = ConexaoUtil.getConexao();
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM CLIENTE");
            sql.append(" WHERE ID = ?");
            PreparedStatement stm = conexao.prepareStatement(sql.toString());
            stm.setInt(1, pessoa.getId());
            stm.execute();

            JOptionPane.showMessageDialog(null, " Cliente deletado com Sucesso!");

        } catch (ClassNotFoundException e) {
            System.out.println("Não encontrou o driver conenector java !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro no comando de SQL !");
            e.printStackTrace();
        } finally {
            try {
                conexao.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Pessoa pessoa;

        if (e.getSource() == botaoCadastro) {
            pessoa = new Pessoa(campoNome.getText(), campoEmail.getText());
            cadastrarClienteDAO(pessoa);
            campoNome.setText("");
            campoEmail.setText("");
            listarClientesDAO(modelo);
        
        } else if (e.getSource() == botaoPesquisar) {
            if (campoPesquisa.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Insira Algo na pesquisa");
            } else {
                buscarPessoaDAO(campoPesquisa.getText());
            }
        } else if (e.getSource() == botaoResetTable) {
            listarClientesDAO(modelo);
            idSelecionado = 0;
            campoNome.setText("");
            campoEmail.setText("");
            botaoNovo.setVisible(false);
            botaoCadastro.setVisible(true);
            botaoUpdate.setVisible(false);

        } else if (e.getSource() == botaoUpdate) {
            pessoa = new Pessoa(idSelecionado, campoNome.getText(), campoEmail.getText());
            atualizarDadosPessoaDAO(pessoa);
            listarClientesDAO(modelo);
        
        } else if (e.getSource() == botaoDelete) {
            pessoa = new Pessoa(idSelecionado);
            deletarPessoaDAO(pessoa);
            campoNome.setText("");
            campoEmail.setText("");
            listarClientesDAO(modelo);
       
        } else if (e.getSource() == botaoNovo) {
            idSelecionado = 0;
            campoNome.setText("");
            campoEmail.setText("");
            botaoNovo.setVisible(false);
            botaoCadastro.setVisible(true);
            botaoUpdate.setVisible(false);
            tabela.getSelectionModel().clearSelection();
        }
    }

}
