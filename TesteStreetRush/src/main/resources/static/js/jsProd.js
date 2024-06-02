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
    <div class="card h-100 shadow-sm">
        <a href="PgDetalhes.html?id=${produto.id}" class="text-decoration-none text-dark">
            <img src="/api/produtos/img/${produto.imagemPrincipal}" class="card-img-top" alt="${produto.nome}">
            <div class="card-body d-flex flex-column">
                <h5 class="card-title">${produto.nome}</h5>
                <p class="card-text mt-auto">Preço: ${produto.preco}</p>
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <small class="text-muted">Código: ${produto.id}</small>
                    <button class="btn btn-primary">Ver mais</button>
                </div>
            </div>
        </a>
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
