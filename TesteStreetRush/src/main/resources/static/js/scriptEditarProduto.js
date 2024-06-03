document.addEventListener("DOMContentLoaded", function() {
    const imageInput = document.getElementById("productImages");
    const imagePreviewContainer = document.getElementById("imagePreview");
    const mainImageSelect = document.getElementById("mainImageSelect");
    const form = document.getElementById("editProductForm");

    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');
    const user = JSON.parse(localStorage.getItem('user'));

    if (user && user.cargo !== 'Administrador') {
        restrictFields();
    }


    fetch(`/api/produtos/${productId}`)
        .then(response => response.json())
        .then(product => {
            document.getElementById("productName").value = product.nome;
            document.getElementById("productRating").value = product.avaliacao;
            document.getElementById("productDescription").value = product.descricao;
            document.getElementById("productPrice").value = product.preco;
            document.getElementById("productStock").value = product.qtdEstoque;
            document.getElementById("productId").value = product.id;

            product.imagens.forEach(image => {
                const option = document.createElement("option");
                option.value = image;
                option.text = image;
                mainImageSelect.appendChild(option);

                const imageElement = document.createElement("img");
                imageElement.src = `/img/${image}`;
                imageElement.classList.add("preview-image");
                imagePreviewContainer.appendChild(imageElement);
            });

            mainImageSelect.value = product.imagemPrincipal;
        })
        .catch(error => console.error('Erro ao buscar produto:', error));

    form.addEventListener("submit", function(event) {
        event.preventDefault();

        // Temporariamente habilitar os campos desabilitados para que seus valores sejam capturados
        enableRestrictedFields();

        const formData = new FormData(form);

        // Restaurar o estado desabilitado dos campos
        if (!isAdmin()) {
            restrictFields();
        }

        fetch(`/api/produtos/${productId}`, {
            method: 'PUT',
            body: formData
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "/listaProdutos.html";
                } else {
                    alert('Erro ao atualizar o produto');
                }
            })
            .catch(error => console.error('Erro ao atualizar produto:', error));
    });
});

function restrictFields() {
    const fields = [
        "productName",
        "productRating",
        "productDescription",
        "productPrice",
        "productImages",
    ];
    fields.forEach(fieldId => {
        const field = document.getElementById(fieldId);
        field.disabled = true;
    });
}

function isAdmin() {
    const user = JSON.parse(localStorage.getItem('user'));
    return user && user.cargo === 'Administrador';
}

function enableRestrictedFields() {
    const fields = [
        "productName",
        "productRating",
        "productDescription",
        "productPrice",
        "productImages",
        "mainImageSelect"
    ];
    fields.forEach(fieldId => {
        const field = document.getElementById(fieldId);
        field.disabled = false;
    });
}

function cancel() {
    window.location.href = "/listaProdutos.html";
}
