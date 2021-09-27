const createOrderBtn = document.querySelector('.create-order-btn');
const changeOrderStateBtn = document.querySelector('.change-order-state-btn');

init()
jQuery('document').ready(function () {
    if (sessionStorage.getItem('workersToken') !== null) {
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
    if (sessionStorage.getItem('workersToken') === null) {
        Swal.fire({
            icon: 'info',
            title: "У вас нет доступа к этой странице. Пожалуйста пройдите аутентификацию",
            showConfirmButton: false,
            timer: 2000
        }).then(() => {
            window.location.href = "/homePage.html";
        })
    }
}

function insertLogoutButton() {
    let logoutButtonDiv = document.getElementById("logoutButtonToInsert");
    logoutButtonDiv.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    document.querySelector('.logout-button-margin').addEventListener(
        'click', () => {
            sessionStorage.removeItem('workersToken');
            sessionStorage.removeItem('workerSession');
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
            let jwtToken = sessionStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
    }).done(function (data) {
        name.innerHTML = `Имя: ${data.name}`
        surname.innerHTML = `Фамилия: ${data.surname}`
        if (data.role === "ROLE_ADMIN") {
            roles.innerHTML = `Роль: Администратор`
        }
        if (data.role === "ROLE_WAREHOUSE_WORKER") {
            roles.innerHTML = `Роль: Работник промежуточного склада`
        }
        if (data.role === "ROLE_PROCESSING_POINT_WORKER") {
            roles.innerHTML = `Роль: Работник пункта отправки/выдачи`
        }
    }).fail(function () {
        Swal.fire({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "info",
        });
    });
}

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('workerSession')).getHours()
    if ((new Date().getHours() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('workersToken');
        sessionStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('workerSession', (new Date()).toString())
    }
}