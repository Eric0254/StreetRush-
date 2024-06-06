package com.example.TesteStreetRush.service;

import com.example.TesteStreetRush.model.Pedido;
import com.example.TesteStreetRush.model.ItemPedido;
import com.example.TesteStreetRush.repository.ItemPedidoRepository;
import com.example.TesteStreetRush.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("Aguardando pagamento");

        // Gerar número sequencial
        Long ultimoPedidoId = pedidoRepository.count() + 1; // Simples incremento para fins de exemplo
        pedido.setNumeroSequencial(ultimoPedidoId);

        Pedido savedPedido = pedidoRepository.save(pedido);

        for (ItemPedido item : pedido.getItens()) {
            item.setPedido(savedPedido);
            itemPedidoRepository.save(item);
        }

        return savedPedido;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElse(null);
    }
}
