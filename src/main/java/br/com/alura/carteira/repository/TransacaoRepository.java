package br.com.alura.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.carteira.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {}
