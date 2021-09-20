document.querySelector('.create-order-btn').addEventListener('click', (event) => {
    window.location.href = "/createOrder.html";
});
document.querySelector('.change-order-state-btn').addEventListener('click', (event) => {
    window.location.href = "/changeOrderStatePage.html";
});

init()
jQuery('document').ready(function () {
    if (localStorage.getItem('workersToken') !== null) {
        insertLogoutButton();
    }
})



function init() {
    if (localStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }
}

function insertLogoutButton() {
    let logoutButtonDiv = document.getElementById("logoutButtonToInsert");
    logoutButtonDiv.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    const hiddenButton = document.querySelector('.logout-button-margin');
    hiddenButton.addEventListener(
        'click', () => {
            localStorage.removeItem('workersToken');
            window.location.href = `/homePage.html`;
        }
    )
}