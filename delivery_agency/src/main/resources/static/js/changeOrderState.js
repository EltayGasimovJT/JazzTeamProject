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
let order;
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
        error: function () {
            Swal.fire({
                title: "Что-то пошло не так",
                icon: "info",
                text: `Не удалось установить, состояния заказов из хранилища`,
                showConfirmButton: false,
                timer: 5000
            })
        }
    });
}

function setupOrderStates(states) {
    jQuery('#states').html('');
    let row = '<option>' + states +
        '</option>';
    $('#states').append(undefined).append(row);
}

function getAllOrders() {
    $.ajax({
        url: `/orders`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            for (let key = 0, size = result.length; key < size; key++) {
                let row = '<tr class="text" style="font-weight: normal"><td>' + result[key].orderTrackNumber +
                    '</td><td class="orderNum">' + result[key].recipient.name + '</td><td>' +
                    result[key].recipient.surname + '</td><td>' + getTimeFormat(result[key].sendingTime) +
                    '</td><td>' + result[key].price + '</td><td>' + result[key].state.state + '</td><td>' +
                    result[key].departurePoint.location + '</td><td>' + result[key].currentLocation.location +
                    '</td><td>' + result[key].destinationPlace.location + '</td><td class="icons-location">' + addEditPanel() + insertCancelButton() + '</td></tr>';
                $('#ordersBody').append(row);
            }
            findTableForSort('orders');

            $(".change-state-button").click(function (event) {
                    checkSession();
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    let geting = $.get(`/orders/findByTrackNumber`, {orderNumber: orderId}, 'application/json');
                    geting.done(function (data) {
                        order = data
                        initStates(orderId)
                        backgroundModal.style.visibility = 'visible';
                    }).fail(function (exception) {
                        Swal.fire({
                            title: "Не удалось изменить состояние",
                            text: exception.responseJSON.message,
                            icon: "info",
                            showConfirmButton: false,
                            timer: 5000
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
                        Swal.fire({
                            title: "Заказ был отменен",
                            icon: 'info'
                        }).then((willDelete) => {
                            if (willDelete.isConfirmed) {
                                window.location.reload();
                            }
                        })
                    }).fail(function (exception) {
                        Swal.fire({
                            title: "Не удалось отменить заказ",
                            text: exception.responseJSON.message,
                            icon: "info",
                            showConfirmButton: false,
                            timer: 5000
                        });

                    });
                }
            )
        },
        error: function () {
            Swal.fire({
                title: "Не удалось загрузить список заказов",
                text: "В базе данных больше нет заказов",
                icon: "info",
                showConfirmButton: false,
                timer: 5000
            });
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
            Swal.fire({
                title: "Статус успешно обновлен",
                text: `Для заказа с номером #${order.orderTrackNumber} был успешно установлен новый статус - ${state}`,
                icon: 'success',
                showConfirmButton: false,
                timer: 7000
            }).then(() => {
                window.location.reload();
            })
        },
        error: function (exception) {
            Swal.fire({
                title: 'Нет прав',
                text: `${exception.responseJSON.message}`,
                icon: 'info',
                showConfirmButton: false,
                timer: 5000
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
            showConfirmButton: false,
            timer: 5000
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
    return '<img src="icons/cross.png" alt="Отменить заказ" class="cancel-button icon-location" title="Отменить заказ" aria-placeholder="Отменить заказ">';
}

function addEditPanel() {
    return '<img src="icons/pen.png" alt="Изменить состояние заказа" class="change-state-button icon-location" title="Изменить состояние заказа" aria-placeholder="Редактировать">';
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

function getTimeFormat(time) {
    return moment(time).format("YYYY-MM-DD HH:mm:ss z");
}

function findTableForSort(tableId) {
    let table = document.getElementById(tableId);
    table.addEventListener('click', (e) => {
        const element = e.target;
        if (element.nodeName !== 'TH') {
            return;
        }
        const index = element.cellIndex;
        const type = element.getAttribute('datatype');
        sortTable(index, table, type)
    })
}

const sortTable = function (index, table, type) {
    const tbody = table.querySelector('tbody');

    const compare = function (rowA, rowB) {
        const rowDataA = rowA.cells[index].innerHTML;
        const rowDataB = rowB.cells[index].innerHTML;
        switch (type) {
            case 'integer': {
                return rowDataA - rowDataB;
                break;
            }
            case 'date': {
                if (moment(rowDataA).isBefore(rowDataB)) {
                    return 1;
                } else if (moment(rowDataA).isAfter(rowDataB)) {
                    return -1;
                } else return 0;
                break;
            }
            case 'text': {
                if (rowDataA < rowDataB) {
                    return -1;
                } else if (rowDataA > rowDataB) {
                    return 1;
                } else return 0;
                break;
            }
            case 'double':{
                return rowDataA - rowDataB;
                break;
            }
            default:
                break;
        }
    }

    let rows = [].slice.call(tbody.rows);

    rows.sort(compare);

    table.removeChild(tbody);

    for (let i = 0; i < rows.length; i++) {
        tbody.appendChild(rows[i]);
    }

    table.appendChild(tbody);
}