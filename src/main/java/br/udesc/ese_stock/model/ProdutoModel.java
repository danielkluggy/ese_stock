package br.udesc.ese_stock.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "produto")
public class ProdutoModel {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID produtoId;

	@Column(length = 255)
	private String nome;
	
	@Column(length = 255)
	private String descricao;
	
	@Column(precision = 10)
	private double preco;
	
	@Column
	private Integer quantidade;

	@Column
	private Integer valor;
}
