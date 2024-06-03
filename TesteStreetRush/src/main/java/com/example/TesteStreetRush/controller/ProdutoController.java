package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.Produto;
import com.example.TesteStreetRush.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    private static final String UPLOAD_DIR = "C:\\Users\\Micro\\OneDrive\\Documentos\\GitHub\\StreetRush-\\TesteStreetRush\\src\\main\\resources\\static\\img\\";

    @GetMapping
    public ResponseEntity<List<Produto>> listProducts() {
        List<Produto> products = produtoService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProductById(@PathVariable("id") Long id) {
        Produto produto = produtoService.getProductById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Produto>> getAllProducts(){
        List<Produto> products = produtoService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> requestBody){
        String novoStatus = requestBody.get("status");
        try{
            produtoService.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o status do produto.");
        }
    }

    @GetMapping("/novo")
    public ResponseEntity<Resource> showAddProductForm() {
        Resource resource = new ClassPathResource("static/cadastrarProduto.html");
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@ModelAttribute Produto produto, @RequestParam("files") MultipartFile[] files, @RequestParam("imagemPrincipal") String imagemPrincipal) {
        try {
            List<String> images = uploadImages(files);
            produto.setImagens(images);
            produto.setImagemPrincipal(imagemPrincipal);
            produto.setStatus("Ativo");
            produtoService.addProduct(produto);


            return ResponseEntity.status(HttpStatus.FOUND).location(ServletUriComponentsBuilder.fromPath("/listaProdutos.html").build().toUri()).build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao adicionar o produto.");
        }
    }

    private List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> imagens = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename(); // Usar o nome original do arquivo
                String filePath = UPLOAD_DIR + "\\" + fileName;
                Files.write(Paths.get(filePath), file.getBytes()); // Salva o arquivo
                imagens.add(fileName); // Adiciona o nome do arquivo à lista de imagens
            }
        }
        return imagens;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") Long id,
            @ModelAttribute Produto produto,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam("imagemPrincipal") String imagemPrincipal) {
        try {
            Produto existingProduct = produtoService.getProductById(id);
            if (existingProduct == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
            }

            existingProduct.setNome(produto.getNome());
            existingProduct.setAvaliacao(produto.getAvaliacao());
            existingProduct.setDescricao(produto.getDescricao());
            existingProduct.setPreco(produto.getPreco());
            existingProduct.setQtdEstoque(produto.getQtdEstoque());
            existingProduct.setImagemPrincipal(imagemPrincipal);

            if (files != null && files.length > 0) {
                List<String> images = uploadImages(files);
                existingProduct.setImagens(images);
            }

            produtoService.updateProduct(existingProduct);
            return ResponseEntity.ok().body("Produto atualizado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o produto.");
        }
    }

    @GetMapping("/img/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = new FileSystemResource("C:\\Users\\Micro\\OneDrive\\Documentos\\GitHub\\StreetRush-\\TesteStreetRush\\src\\main\\resources\\static\\img\\" + filename);
        return ResponseEntity.ok().body(file);
    }
}
