jQuery('document').ready(function () {
    let order = getIdFromUrl();
    getOrderHistories(order.orderId);
    getOrder(order.orderId);
    $('#orderId').append(order.orderNumber);
    if (sessionStorage.getItem('clientPhone') !== null) {
        addBackToOrderListButton();
        insertClientInfo()
        insertLogoutButton()
    }
})
let backToTheClientsOrdersButton = document.getElementById('backToTheClientsOrdersButton');

function getIdFromUrl() {
    return window
        .location
        .search
        .replace('?', '')
        .split('&')
        .reduce(
            function (p, e) {
                let a = e.split('=');
                p[decodeURIComponent(a[0])] = decodeURIComponent(a[1]);
                return p;
            },
            {}
        );
}

function getOrderHistories(idFromUrl) {
    $.ajax({
        url: `/orders/findHistory/${idFromUrl}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            for (let key = 0, size = result.length; key < size; key++) {
                let row = '<tr class="text" style="font-weight: normal"><td>' + getTimeFormat(result[key].changedAt) +
                    '</td><td>' + getTimeFormat(result[key].sentAt) + '</td><td>' + result[key].comment +
                    '</td><td>' + result[key].worker.name + '</td><td>' + result[key].worker.surname +
                    '</td></tr>';
                $('#orderHistoryBody').append(row);
            }
            findTableForSort("orderHistory")
        }
    });
}

function addBackToOrderListButton() {
    let backToTheClientsOrdersDiv = document.getElementById("backToTheClientsOrdersButton");
    backToTheClientsOrdersDiv.innerHTML = '<button class="customBtn buttonText hiddenButton"  type="button">'
        + '<span class="glyphicon glyphicon-pencil"></span>На страницу заказов</button>';
    document.querySelector('.hiddenButton').addEventListener(
        'click', () => {
            checkSession()
            window.location.href = `/clientsOrders.html`;
        }
    );
}

function getOrder(idFromUrl) {
    $.ajax({
        url: `/orders/${idFromUrl}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            let row = '<tr class="text" style="font-weight: normal"><td>' + result.recipient.name +
                '</td><td>' + result.recipient.surname + '</td><td>' +
                getTimeFormat(result.sendingTime) + '</td><td>' + result.price +
                '</td><td>' + result.state.state + '</td><td>' + result.departurePoint.location + '</td><td>' +
                result.currentLocation.location + '</td><td>' + result.destinationPlace.location +
                '</td><td>' + result.parcelParameters.weight + '</td><td>' + result.parcelParameters.height +
                '</td><td>' + result.parcelParameters.width + '</td><td>' + result.parcelParameters.length +
                '</td></tr>';
            $('#orderInfoBody').append(row);
            findTableForSort('orderInfo')
        }
    });
}


function getTimeFormat(time) {
    return moment(time).format("YYYY-MM-DD HH:mm:ss z");
}

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('sessionTime')).getHours()
    if (Math.abs((new Date()).getHours() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('clientPhone');
        sessionStorage.removeItem('sessionTime');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('sessionTime', (new Date()).toString())
    }
}

function insertLogoutButton() {
    let logoutButtonDiv = document.getElementById("logoutButtonToInsert");
    logoutButtonDiv.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    document.querySelector('.logout-button-margin').addEventListener(
        'click', () => {
            sessionStorage.removeItem('clientPhone');
            sessionStorage.removeItem('sessionTime');
            window.location.href = `/homePage.html`;
        }
    )
}

function insertClientInfo() {
    let name = document.getElementById("client-name-nav");
    let surname = document.getElementById("client-surname-nav");
    $.get(`/clients/findByPhoneNumber`,
        {phoneNumber: sessionStorage.getItem('clientPhone')},
        'application/json')
        .done(function (data) {
            name.innerHTML = `Имя: ${data.name}`
            surname.innerHTML = `Фамилия: ${data.surname}`
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

function findTableForSort(tableId) {
    let table = document.getElementById(tableId);
    table.addEventListener('click', (e) => {
        const element = e.target;
        if (element.nodeName !== 'TH') {
            return;
        }
        const index = element.cellIndex;
        const type = element.getAttribute('data-type');
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
            case 'double': {
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