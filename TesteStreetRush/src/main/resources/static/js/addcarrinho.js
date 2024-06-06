// cart.js
$(document).ready(function() {
    let params = new URLSearchParams(window.location.search);
    let productId = params.get('id');

    $.ajax({
        url: '/api/produtos/' + productId,
        type: 'GET',
        dataType: 'json',
        success: function(produto) {
            let productDetails = $('#product-details');

            productDetails.find('.btn.btn-primary').click(function() {
                var cart = JSON.parse(localStorage.getItem('cart')) || [];
                var product = {
                    id: produto.id,
                    nome: produto.nome,
                    preco: produto.preco,
                    imagem: produto.imagemPrincipal,
                    quantidade: 1
                };
                cart.push(product);
                localStorage.setItem('cart', JSON.stringify(cart));
                alert("Produto adicionado ao carrinho!");
            });
        },
        error: function (error) {
            console.log('Erro ao buscar detalhes do produto:', error);
        }
    });
});
