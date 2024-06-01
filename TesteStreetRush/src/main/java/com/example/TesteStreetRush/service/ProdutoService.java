package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.Produto;
import com.example.TesteStreetRush.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto addProduct(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> getAllProducts() {
        return produtoRepository.findAll();
    }

    public Produto getProductById(long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto updateProduct(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void atualizarStatus(Long id, String novoStatus){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o id: " + id));
        produto.setStatus(novoStatus);
        produtoRepository.save(produto);
    }

    public void deleteProduct(long id) {
        produtoRepository.deleteById(id);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
