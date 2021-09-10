const createOrderBtn = document.querySelector('.create-order-btn');

init()
jQuery('document').ready(function () {
    if (localStorage.getItem('workersToken') !== null) {
        insertLogoutButton();
    }
})

createOrderBtn.addEventListener('click', (event) => {
    window.location.href = "http://localhost:8081/createOrder.html";
})

function init() {
    if (localStorage.getItem('workersToken') === null) {
        window.location.href = "http://localhost:8081/homePage.html";
    }
}

function insertLogoutButton() {
    let table = document.getElementById("logoutButtonToInsert");
    table.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    const hiddenButton = document.querySelector('.logout-button-margin');
    hiddenButton.addEventListener(
        'click', () => {
            localStorage.removeItem('workersToken');
            window.location.href = `http://localhost:8081/homePage.html`;
        }
    )
}