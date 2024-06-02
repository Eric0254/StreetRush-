$(document).ready(function() {
    $.ajax({
        url: '/api/produtos',
        type: 'GET',
        dataType: 'json',
        success: function(produtos) {
            let productList = $('#product-list');
            produtos.forEach(produto => {
                let card = `
                    <div class="col-md-4">
                        <div class="card">
                            <img src="/api/produtos/img/${produto.imagemPrincipal}" class="card-img-top" alt="${produto.nome}">
                            <div class="card-body">
                                <h5 class="card-title">${produto.nome}</h5>
                                <p class="card-text">Preço: ${produto.preco}</p>
                                <a href="PgDetalhes.html?id=${produto.id}" class="btn btn-primary">Ver detalhes</a>

                            </div>
                        </div>
                    </div>
                `;
                productList.append(card);
            });
        },
        error: function (error) {
            console.log('Erro ao buscar produtos:', error);
        }
    });
});
