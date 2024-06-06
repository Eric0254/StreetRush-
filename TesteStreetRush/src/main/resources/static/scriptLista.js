document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
});

const user = JSON.parse(localStorage.getItem('user'));
const userCargo = user ? user.cargo : null;

const itensPorPagina = 10;
let paginaAtual = 1;
let produtos = [];

function fetchProducts() {
    fetch('/api/produtos/lista')
        .then(response => response.json())
        .then(data => {
            produtos = data;
            produtos.sort((a, b) => b.id - a.id);

            const tableBody = document.getElementById('productTableBody');
            tableBody.innerHTML = '';

            const startIndex = (paginaAtual - 1) * itensPorPagina;
            const endIndex = startIndex + itensPorPagina;
            const produtosPagina = produtos.slice(startIndex, endIndex);

            produtosPagina.forEach(prod => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${prod.id}</td>
                    <td>${prod.nome}</td>
                    <td>${prod.qtdEstoque}</td>
                    <td>R$ ${prod.preco}</td>
                    <td>${prod.status}</td>
                    <td><button onclick="abrirNovaJanela('${prod.id}', '${prod.nome}', '${prod.avaliacao}', '${prod.descricao}', '${prod.preco}', '${prod.qtdEstoque}', '${prod.status}', '${prod.imagemPrincipal}')">Editar</button></td>
                    <td><button onclick="confirmStatusUpdate('${prod.id}', '${prod.status}')">Ativar/Inativar</button></td>
                    <td><a href="PgDetalhes.html?id=${prod.id}" class="btn btn-dark ${userCargo !== 'Administrador' ? 'disabled' : ''}">Visualizar</a></td>
                `;
                tableBody.appendChild(row);
            });

            mostrarIndicadorPagina();
            mostrarBotoesPaginacao();

        })
        .catch(error => console.error('Error fetching products:', error));
}

function mostrarIndicadorPagina() {
    const totalPaginas = Math.ceil(produtos.length / itensPorPagina);
    const indicadorPagina = document.getElementById('indicadorPagina');
    indicadorPagina.textContent = `Página ${paginaAtual} de ${totalPaginas}`;
}

function mostrarBotoesPaginacao() {
    const botaoAnterior = document.getElementById('botaoAnterior');
    const botaoProximo = document.getElementById('botaoProximo');

    if (paginaAtual === 1) {
        botaoAnterior.style.display = 'none';
    } else {
        botaoAnterior.style.display = 'block';
    }

    if (paginaAtual >= Math.ceil(produtos.length / itensPorPagina)) {
        botaoProximo.style.display = 'none';
    } else {
        botaoProximo.style.display = 'block';
    }
}

function filterTable() {
    const input = document.getElementById("searchInput");
    const filter = input.value.toUpperCase();
    const table = document.getElementById("userTable");
    const tr = table.getElementsByTagName("tr");

    for (let i = 1; i < tr.length; i++) {
        const td = tr[i].getElementsByTagName("td")[1]; // Index 1 corresponde à coluna "Nome"
        if (td) {
            const txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function abrirNovaJanela(id, nome, avaliacao, descricao, preco, qtdEstoque, status, imagemPrincipal) {
    const url = `editarProduto.html?id=${encodeURIComponent(id)}`;
    window.open(url, "_blank");
}

function confirmStatusUpdate(productId, currentStatus) {
    if (userCargo === 'Administrador') {
        const confirmation = confirm("Tem certeza que deseja ativar/inativar este produto?");
        if (confirmation) {
            const newStatus = currentStatus === 'ativo' ? 'inativo' : 'ativo';
            fetch(`/api/produtos/${productId}/status`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ status: newStatus })
            })
                .then(response => {
                    if (response.ok) {
                        fetchProducts();
                    } else {
                        alert('Erro ao atualizar o status do produto');
                    }
                })
                .catch(error => console.error('Error updating product status:', error));
        }
    } else {
        alert('Ação não permitida: você não tem permissão para ativar/inativar produtos.');
    }
}

function irParaPagina(pagina) {
    if (pagina === paginaAtual || pagina < 1) {
        return;
    }

    paginaAtual = pagina;
    fetchProducts();
}

document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    const addButton = document.querySelector('#userTable th:last-child button');
    if (userCargo !== 'Administrador') {
        addButton.remove();
    }
});
