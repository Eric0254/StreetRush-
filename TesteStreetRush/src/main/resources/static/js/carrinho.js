$(document).ready(function() {
    var user = JSON.parse(localStorage.getItem('user'));
    var cliente = JSON.parse(localStorage.getItem('cliente'));

    if (user || cliente) {
        $("#loginItem").hide();
        $("#username").text(user ? user.nome : cliente.nome);
        $("#userItem").show();
    } else {
        $("#loginItem").show();
        $("#userItem").hide();
    }

    $("#loginButton").click(function() {
        window.location.href = "/login.html";
    });

    $("#profileOption").click(function() {
        if (user) {
            window.location.href = "/telaADM.html";
        } else if (cliente) {
            window.location.href = "/Perfil.html";
        }
    });

    $("#logoutOption").click(function() {
        localStorage.removeItem('user');
        localStorage.removeItem('cliente');
        location.reload();
    });

    // Lógica do carrinho
    var cart = JSON.parse(localStorage.getItem('cart')) || [];
    var cartDiv = $('#cart');
    var totalValue = 0;

    var groupedCart = {};
    cart.forEach(function(product) {
        if (groupedCart[product.id]) {
            groupedCart[product.id].quantity += 1;
        } else {
            groupedCart[product.id] = product;
            groupedCart[product.id].quantity = 1;
        }
    });

    Object.values(groupedCart).forEach(function(product, index) {
        var productDiv = `
            <div class="card mb-3" style="max-width: 400px;">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="/api/produtos/img/${product.imagem}" class="card-img" style="max-width: 100px;" alt="${product.nome}">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${product.nome}</h5>
                            <p class="card-text">Preço: ${product.preco}</p>
                            <div class="form-group">
                                <label for="quantity${index}">Qtd:</label>
                                <input type="number" id="quantity${index}" class="form-control" min="1" value="${product.quantity}">
                            </div>
                        </div>
                    </div>
                </div>
                <button type="button" class="close" aria-label="Remover" onclick="removeItem(${index})">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            `;
        cartDiv.append(productDiv);
        totalValue += product.preco * product.quantity;

        $('#quantity' + index).change(function() {
            var newQuantity = $(this).val();
            totalValue = totalValue - product.preco * product.quantity + product.preco * newQuantity;
            product.quantity = newQuantity;
            $('#totalValue').text('Total: R$' + totalValue.toFixed(2));
        });
    });

    $('#totalValue').text('Total: R$' + totalValue.toFixed(2));

    // Verificar login antes de abrir modais de pagamento
    $('.payment-button').click(function() {
        if (user || cliente) {
            var paymentType = $(this).attr('id');
            if (paymentType === 'payPix') {
                $('#pixModal').modal('show');
            } else {
                var paymentTypeText = paymentType === 'payDebit' ? 'débito' : 'crédito';
                $('#paymentModalLabel').text('Pagamento com Cartão de ' + paymentTypeText.charAt(0).toUpperCase() + paymentTypeText.slice(1));
                $('#paymentModal').modal('show');
            }
        } else {
            alert('Você precisa estar logado para acessar as opções de pagamento.');
            window.location.href = "/login.html";
        }
    });

    $('#paymentForm').submit(function(event) {
        event.preventDefault();
        // Aqui você pode adicionar a lógica para processar o pagamento
        alert('Pagamento processado com sucesso!');
        $('#paymentModal').modal('hide');
    });

    $('#confirmPixPayment').click(function() {
        // Aqui você pode adicionar a lógica para processar o pagamento com PIX
        alert('Pagamento com PIX confirmado. Pedido gerado com sucesso!');
        $('#pixModal').modal('hide');
    });
});

function removeItem(index) {
    var cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    location.reload();
}