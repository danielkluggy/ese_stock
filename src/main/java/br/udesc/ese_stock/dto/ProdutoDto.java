package br.udesc.ese_stock.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProdutoDto {
	@NotBlank
	@Size(max = 150, message = "O nome inserido excede o valor permitido para o campo.")
	private String nome;

	@NotBlank
	@Size(max = 150, message = "A descrição inserida excede o valor permitido para o campo.")
	private String descricao;

	@Min(value = 0, message = "Valores negativos não são suportados para o campo")
	private double preco;

	@Digits(integer = 10, fraction = 0, message = "O valor deve ser um número inteiro com no máximo 10 dígitos")
	private Integer quantidade;
  
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
