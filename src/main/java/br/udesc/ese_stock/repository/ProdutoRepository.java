package br.udesc.ese_stock.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.udesc.ese_stock.model.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, UUID>{
	
	List<ProdutoModel> findAllByProdutoIdIn(List<UUID> produtoIds);
}
