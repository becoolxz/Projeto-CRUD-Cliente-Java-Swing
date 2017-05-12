/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucas.crud.crudCadastroCliente.modelo;

/**
 *
 * @author tiano
 */
public class Pessoa {

   private Integer id;
	private String nome;
	private String email;
	
	public Pessoa() {}

	public Pessoa(String nome, String email) {
		super();
		this.nome = nome;
		this.email = email;
	}
	
	public Pessoa(Integer id){
	   super();
	   this.id = id;
	}
	
	public Pessoa(Integer id, String nome, String email) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return id;
	}

public void setId(Integer id) {
	this.id = id;
}

public void setNome(String nome) {
	this.nome = nome;
}

public void setEmail(String email) {
	this.email = email;
}
    
}
