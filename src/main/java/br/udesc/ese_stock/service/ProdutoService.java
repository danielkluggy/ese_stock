package br.udesc.ese_stock.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.udesc.ese_stock.dto.VendaDto;
import br.udesc.ese_stock.model.ProdutoModel;
import br.udesc.ese_stock.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public ProdutoModel save(ProdutoModel product) {
		return this.produtoRepository.save(product);
	}

	public Page<ProdutoModel> findAll(Pageable pageable) {
		return this.produtoRepository.findAll(pageable);
	}

	public Optional<ProdutoModel> findById(UUID id) {
		return this.produtoRepository.findById(id);
	}

	public void delete(ProdutoModel productModel) {
		this.produtoRepository.delete(productModel);
	}
	
	public List<ProdutoModel> findAll(List<UUID> idProdutos){
		return produtoRepository.findAllByProdutoIdIn(idProdutos);
	}

	@Transactional
    public boolean processarVenda(VendaDto produtosVendidos) {
        for (VendaDto.ProdutoVendaDto produtoVenda : produtosVendidos.getProdutosVendidos()) {
            Optional<ProdutoModel> optionalItem = produtoRepository.findById(produtoVenda.getProdutoId());
            if (optionalItem.isPresent()) {
                ProdutoModel produto = optionalItem.get();
                if (produto.getQuantidade() < produtoVenda.getQuantidadeVendida()) {
                    return false;
                }
            } else {
                throw new EntityNotFoundException("Produto com ID " + produtoVenda.getProdutoId() + " não encontrado.");
            }
        }

        for (VendaDto.ProdutoVendaDto produtoVenda : produtosVendidos.getProdutosVendidos()) {
            ProdutoModel produto = produtoRepository.findById(produtoVenda.getProdutoId()).get();
            produto.setQuantidade(produto.getQuantidade() - produtoVenda.getQuantidadeVendida());
            produtoRepository.save(produto);
        }

        return true;
    }
	
}
