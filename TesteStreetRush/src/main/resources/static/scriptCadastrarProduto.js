document.addEventListener("DOMContentLoaded", function() {
    const imageInput = document.getElementById("productImages");
    const imagePreviewContainer = document.getElementById("imagePreview");
    const mainImageSelect = document.getElementById("mainImageSelect");

    imageInput.addEventListener("change", function(event) {
        imagePreviewContainer.innerHTML = ""; // Limpa a pré-visualização antes de adicionar novas imagens
        mainImageSelect.innerHTML = ""; // Limpa as opções existentes

        const files = event.target.files; // Obtém os arquivos selecionados

        if (files) {
            for (const file of files) {
                const reader = new FileReader(); // Cria um objeto FileReader para ler os dados da imagem
                reader.onload = function (e) {
                    const imageElement = document.createElement("img"); // Cria um elemento <img> para exibir a imagem
                    imageElement.src = e.target.result; // Define o src da imagem como o resultado da leitura do arquivo
                    imageElement.classList.add("preview-image"); // Adiciona uma classe para estilização opcional
                    imagePreviewContainer.appendChild(imageElement); // Adiciona a imagem à pré-visualização

                    const option = document.createElement("option");
                    option.value = file.name; // Valor da opção é o nome do arquivo
                    option.text = file.name; // Texto da opção é o nome do arquivo
                    mainImageSelect.appendChild(option); // Adiciona a opção ao select
                };
                reader.readAsDataURL(file); // Lê os dados da imagem como uma URL
            }
        }
    });
});

function cancel() {
    window.location.href = "/produtos"; // Redireciona para a lista de produtos
}
