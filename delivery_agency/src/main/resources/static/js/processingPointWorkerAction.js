const createOrderBtn = document.querySelector('.create-order-btn');
const changeOrderStateBtn = document.querySelector('.change-order-state-btn');

init()
jQuery('document').ready(function () {
    if (localStorage.getItem('workersToken') !== null) {
        insertWorkerInfo();
        insertLogoutButton();
    }
})

createOrderBtn.addEventListener('click', (event) => {
    checkSession();
    window.location.href = "/createOrder.html";
})

changeOrderStateBtn.addEventListener('click', (event) => {
    checkSession();
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
            localStorage.removeItem('workerSession');
            window.location.href = `/homePage.html`;
        }
    )
}

function insertWorkerInfo() {
    let name = document.getElementById("worker-name-nav");
    let surname = document.getElementById("worker-surname-nav");
    let roles = document.getElementById("worker-role-nav");
    $.ajax({
        type: 'GET',
        url: `/users/getCurrentWorker`,
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            let jwtToken = localStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
    }).done(function (data) {
        name.innerHTML = `Имя: ${data.name}`
        surname.innerHTML = `Фамилия: ${data.surname}`
        roles.innerHTML = `Роль: ${data.role}`
    }).fail(function () {
        swal({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "error",
        });
    });
}

function checkSession(){
    let sessionTimeMinutes = new Date(localStorage.getItem('workerSession')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 4) {
        localStorage.removeItem('workersToken');
        localStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        localStorage.setItem('workerSession', (new Date()).toString())
    }
}