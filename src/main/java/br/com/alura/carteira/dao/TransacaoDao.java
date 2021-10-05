package br.com.alura.carteira.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.carteira.model.Usuario;

@Repository
public class TransacaoDao {

	@Autowired
	private EntityManager manager;

	public void cadastrar(Usuario usuario) {
		manager.persist(usuario);
	}

	public List<Usuario> listar() {
		return manager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
	}
}
