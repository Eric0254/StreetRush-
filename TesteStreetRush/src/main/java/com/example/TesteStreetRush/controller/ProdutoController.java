package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.Produto;
import com.example.TesteStreetRush.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    private static final String UPLOAD_DIR = "C:\\Users\\wende\\OneDrive\\Documentos\\GitHub\\StreetRush-\\TesteStreetRush\\src\\main\\resources\\img";

    @GetMapping
    public ResponseEntity<Resource> listProducts() {
        Resource resource = new ClassPathResource("static/cadastrarProduto.html");
        return ResponseEntity.ok().body(resource);
    }

    @GetMapping("/novo")
    public ResponseEntity<Resource> showAddProductForm() {
        Resource resource = new ClassPathResource("static/cadastrarProduto.html");
        return ResponseEntity.ok().body(resource);
    }

    @PostMapping
    public String addProduct(@ModelAttribute Produto produto, @RequestParam("files") MultipartFile[] files, @RequestParam("imagemPrincipal") String imagemPrincipal) {
        try {
            List<String> images = uploadImages(files);
            produto.setImagens(images);
            produto.setImagemPrincipal(imagemPrincipal);
            produto.setStatus("Ativo");
            produtoService.addProduct(produto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/produtos";
    }

    private List<String> uploadImages(MultipartFile[] files) throws IOException {
        List<String> imagens = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename(); // Gera um nome único para o arquivo
                String filePath = UPLOAD_DIR + "\\" + fileName;
                Files.write(Paths.get(filePath), file.getBytes()); // Salva o arquivo
                imagens.add(fileName); // Adiciona o nome do arquivo à lista de imagens
            }
        }
        return imagens;
    }
}
