getAllOrders();
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

});

function initStates(orderNumber) {
    $.ajax({
        url: `/users/getStatesByRole`,
        type: 'GET',
        contentType: 'application/json',
        data: {orderNumber: orderNumber},
        beforeSend: function (xhr) {
            let jwtToken = sessionStorage.getItem('workersToken');
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
    let row = '<option>' + states +
        '</option>';
    $('#states').append(undefined).append(row);
}

function getAllOrders() {
    let phoneNumber = sessionStorage.getItem('clientPhone');
    $.ajax({
        url: `/orders`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            for (let key = 0, size = result.length; key < size; key++) {
                let row = '<tr class="text"><td>' + result[key].orderTrackNumber +
                    '</td><td class="orderNum">' + result[key].recipient.name + '</td><td>' +
                    result[key].recipient.surname + '</td><td>' + getTimeFormat(result[key].sendingTime) +
                    '</td><td>' + result[key].price + '</td><td>' + result[key].state.state + '</td><td>' +
                    result[key].departurePoint.location + '</td><td>' + result[key].currentLocation.location +
                    '</td><td>' + result[key].destinationPlace.location + '</td><td>' + addChangeStateButton() +
                    '</td><td>' + insertCancelButton() + '</td></tr>';
                $('#orders').append(row);
            }

            $(".change-state-button").click(function (event) {
                    checkSession();
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    let geting = $.get(`/orders/findByTrackNumber`, {orderNumber: orderId}, 'application/json');
                    geting.done(function (data) {
                        order = data
                        initStates(orderId)
                        backgroundModal.style.visibility = 'visible';
                    }).fail(function () {
                        swal({
                            title: "Ошибка ввода",
                            text: "Данного заказа не существует",
                            icon: "error",
                        });

                    });
                }
            )

            $(".cancel-button").click(function (event) {
                    checkSession();
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    $.ajax({
                        type: 'DELETE',
                        url: `/orders/deleteByTrackNumber/${orderId}`,
                        contentType: 'application/json; charset=utf-8',
                        beforeSend: function (xhr) {
                            let jwtToken = sessionStorage.getItem('workersToken');
                            if (jwtToken !== null) {
                                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
                            }
                        },
                    }).done(function () {
                        swal({
                            title: "Заказ был успешно отменен",
                            icon: 'success'
                        })
                        window.location.href = "/changeOrderState.html";
                    }).fail(function () {
                        swal({
                            title: "Ошибка ввода",
                            text: "Данного заказа не существует",
                            icon: "error",
                        });

                    });
                }
            )
        },
        error: function (exception) {
            alert(exception.message);
        }
    });
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

function insertCancelButton() {
    return `<button id="cancelOrderBtn" type="submit" class="btn btn-primary open-modal cancel-button">
            Отменить заказ
        </button>`;
}

function addChangeStateButton() {
    return '<button class="btn btn-primary change-state-button" type="button">'
        + '<span class="glyphicon glyphicon-pencil"></span>Изменить состояние</button>';
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

function getTimeFormat(time) {
    let date = new Date(time);

    date.setDate(date.getDate() + 20);

    return ('0' + date.getDate()).slice(-2) + '.'
        + ('0' + (date.getMonth() + 1)).slice(-2) + '.'
        + date.getFullYear() + ' ' + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':' + ('0' + date.getSeconds()).slice(-2);
}