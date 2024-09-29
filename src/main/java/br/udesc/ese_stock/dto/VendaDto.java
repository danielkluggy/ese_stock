package br.udesc.ese_stock.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class VendaDto {

	@NotEmpty(message = "Lista de itens não pode ser vazia")
	private List<ProdutoVendaDto> produtosVendidos;

	public List<ProdutoVendaDto> getProdutosVendidos() {
		return produtosVendidos;
	}

	public void setProdutosVendidos(List<ProdutoVendaDto> produtosVendidos) {
		this.produtosVendidos = produtosVendidos;
	}

	public static class ProdutoVendaDto {

		@NotBlank(message = "Necessário informar um UUID")
        private UUID produtoId;

		@Digits(integer = 10, fraction = 0, message = "O valor deve ser um número inteiro com no máximo 10 dígitos")
        private int quantidadeVendida;

        public UUID getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(UUID produtoId) {
            this.produtoId = produtoId;
        }

        public int getQuantidadeVendida() {
            return quantidadeVendida;
        }

        public void setQuantidadeVendida(int quantidade) {
            this.quantidadeVendida = quantidade;
        }
	}

}
