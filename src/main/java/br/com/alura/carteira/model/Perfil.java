package br.com.alura.carteira.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
@Table(name = "perfis")
public class Perfil implements GrantedAuthority {

	private static final long serialVersionUID = -9108271505698750100L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Override
	public String getAuthority() {
		return this.nome;
	}

}
