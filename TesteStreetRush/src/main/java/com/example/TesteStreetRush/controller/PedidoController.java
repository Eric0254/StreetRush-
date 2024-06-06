package com.example.TesteStreetRush.controller;

import com.example.TesteStreetRush.model.Pedido;
import com.example.TesteStreetRush.repository.ItemPedidoRepository;
import com.example.TesteStreetRush.repository.PedidoRepository;
import com.example.TesteStreetRush.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.criarPedido(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPedido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o pedido.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoService.listarPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> listarPedidoPorId(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.buscarPedidoPorId(id);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
