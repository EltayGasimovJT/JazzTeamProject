jQuery('document').ready(function () {
    if (sessionStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }

    if (sessionStorage.getItem('workersToken') !== null) {
        insertWorkerInfo();
        insertLogoutButton();
    }
})

const backgroundModal = document.querySelector('.backGround-modal');
backgroundModal.addEventListener('click', () => {
    backgroundModal.style.visibility = 'hidden';
})

document.querySelector('.modal-redirect').addEventListener('click', (event) => {
    event.stopPropagation();
});
let allStates;
let order;

let currentWorkerRole;


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
            if (sessionStorage.getItem('workersToken') !== null) {
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
    for (let key = 0, size = states.length; key < size; key++) {
        let row = '<option>' + states[key] +
            '</option>';
        $('#states').append(row);
    }
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
            let jwtToken = sessionStorage.getItem('workersToken');
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
        roles.innerHTML = `Роль: ${data.role}`
    }).fail(function () {
        swal({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "error",
        });
    });
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

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('workerSession')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('workersToken');
        sessionStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('workerSession', (new Date()).toString())
    }
}