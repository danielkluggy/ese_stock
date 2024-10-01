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
}
