package br.udesc.ese_stock.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.udesc.ese_stock.dto.ProdutoDto;
import br.udesc.ese_stock.dto.VendaDto;
import br.udesc.ese_stock.exception.ObjectNotFoundException;
import br.udesc.ese_stock.model.ProdutoModel;
import br.udesc.ese_stock.service.ProdutoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@PostMapping
	public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProdutoDto productDto){
		ProdutoModel productModel = new ProdutoModel();
		BeanUtils.copyProperties(productDto, productModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.produtoService.save(productModel));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoModel>> getAllProduct(@PageableDefault(
																page = 0, 
																size = 10, 
																sort = "descricao", 
																direction = Sort.Direction.ASC)
																Pageable peageable
																){
		return ResponseEntity.status(HttpStatus.OK).body(this.produtoService.findAll(peageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
		Optional<ProdutoModel> productModelOptional = this.produtoService.findById(id);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessageProductNotFound());
		}
		return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) throws ObjectNotFoundException {
		Optional<ProdutoModel> productModelOptional = this.produtoService.findById(id);
		if(!productModelOptional.isPresent()) {
			throw new ObjectNotFoundException(getMessageProductNotFound());
		}
		this.produtoService.delete(productModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(getMessageProductDeleted());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
												@RequestBody @Valid ProdutoDto produtDto){
		
		Optional<ProdutoModel> productModelOptional = this.produtoService.findById(id);
		if(!productModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessageProductNotFound());
		}
		ProdutoModel productModel = new ProdutoModel();
		BeanUtils.copyProperties(produtDto, productModel);
		productModel.setProdutoId(productModelOptional.get().getProdutoId());
		return ResponseEntity.status(HttpStatus.OK).body(this.produtoService.save(productModel));
	}

	@PostMapping("/vender")
	public ResponseEntity<Object> sellProducts(@RequestBody VendaDto vendaDto){

		try {
            boolean vendaSucesso = produtoService.processarVenda(vendaDto);

            if (vendaSucesso) {
                return ResponseEntity.status(HttpStatus.OK).body(getMessageSaleProcessedSuccessfully());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getMessageProductQuantityWrong());
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
	}
	
	protected String getMessageProductNotFound() {
		return "Produto não encontrado!";
	}
	
	protected String getMessageProductDeleted() {
		return "Produto deletado com sucesso!";
	}

	protected String getMessageSaleProcessedSuccessfully(){
		return "Venda processada com sucesso!";
	}

	protected String getMessageProductQuantityWrong(){
		return "Quantidade insuficiente no estoque para um ou mais itens!";
	}
	
}
