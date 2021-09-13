const createOrderBtn = document.querySelector('.create-order-btn');
const changeOrderStateBtn = document.querySelector('.change-order-state-btn');

init()
jQuery('document').ready(function () {
    if (localStorage.getItem('workersToken') !== null) {
        insertLogoutButton();
    }
})

createOrderBtn.addEventListener('click', (event) => {
    window.location.href = "/createOrder.html";
})

changeOrderStateBtn.addEventListener('click', (event) => {
    window.location.href = "/changeOrderStatePage.html";
})

function init() {
    if (localStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }
}

function insertLogoutButton() {
    let table = document.getElementById("logoutButtonToInsert");
    table.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    const hiddenButton = document.querySelector('.logout-button-margin');
    hiddenButton.addEventListener(
        'click', () => {
            localStorage.removeItem('workersToken');
            window.location.href = `/homePage.html`;
        }
    )
}