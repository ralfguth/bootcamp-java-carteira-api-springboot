package br.com.alura.carteira.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.alura.carteira.model.Transacao;

@Repository
public class UsuarioDao {

	@Autowired
	private EntityManager manager;

	public void cadastrar(Transacao transacao) {
		manager.persist(transacao);
	}

	public List<Transacao> listar() {
		return manager.createQuery("SELECT t FROM Transacao t", Transacao.class).getResultList();
	}
}
