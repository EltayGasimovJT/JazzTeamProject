jQuery('document').ready(function () {
    if (localStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }

    if (localStorage.getItem('workersToken') !== null) {
        insertWorkerInfo();
        insertLogoutButton();
    }
})

const backgroundModal = document.querySelector('.backGround-modal');
const modal = document.querySelector('.modal-redirect');
const openModel = document.querySelector('.open-modal');

let allStates;
let order;
let currentWorkerRole;

backgroundModal.addEventListener('click', () => {
    backgroundModal.style.visibility = 'hidden';
})

modal.addEventListener('click', (event) => {
    event.stopPropagation();
})

$("#trackOrderForm").submit(function (event) {
    checkSession();
    event.preventDefault();
    let $form = $(this),
        orderNumber = $form.find("input[name='orderNumber']").val(),
        url = $form.attr("action");
    let geting = $.get(url, {orderNumber: orderNumber}, 'application/json');
    geting.done(function (data) {
        order = data
        initStates()
        backgroundModal.style.visibility = 'visible';
    }).fail(function () {
        swal({
            title: "Ошибка ввода",
            text: "Данного заказа не существует",
            icon: "error",
        });

    });
});

function initStates() {
    $.ajax({
        url: `/users/getStatesByRole`,
        type: 'GET',
        contentType: 'application/json',
        data: {orderNumber: order.orderTrackNumber},
        beforeSend: function (xhr) {
            let jwtToken = localStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
        success: function (result) {
            setupOrderStates(result)
        },
        error: function (exception) {
            swal();
            swal({
                title: 'Что-то пошло не так',
                text: `${exception.responseJSON.message}`,
                icon: 'error'
            })
        }
    });
}

function setupOrderStates(states) {
    var r = [], j = -1;
    for (let key = 0, size = states.length; key < size; key++) {
        r[++j] = '<option>';
        r[++j] = states[key];
        r[++j] = '</option>';
    }
    $('#states').append(r.join(''));
}


$('#changeOrderState').submit(function (event) {
    checkSession();
    event.preventDefault();
    let $form = $(this),
        state = $form.find("input[name='newState']").val();
    $.ajax({
        url: `/changeOrderState`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            orderNumber: `${order.orderTrackNumber}`,
            orderState: `${state}`
        }),
        beforeSend: function (xhr) {
            let jwtToken = localStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
        success: function () {
            swal({
                title: "Статус успешно обновлен",
                text: `Для заказа с номером #${order.orderTrackNumber} был успешно установлен новый статус - ${state}`,
                icon: 'success'
            })
        },
        error: function (exception) {
            swal();
            swal({
                title: 'Что-то пошло не так',
                text: `${exception.responseJSON.message}`,
                icon: 'error'
            })
        }
    });
})

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

function checkSession() {
    let sessionTimeMinutes = new Date(localStorage.getItem('workerSession')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 1) {
        localStorage.removeItem('workersToken');
        localStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        localStorage.setItem('workerSession', (new Date()).toString())
    }
}