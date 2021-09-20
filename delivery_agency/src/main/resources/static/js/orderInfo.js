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
                let row = '<tr class="text"><td>' + getTimeFormat(result[key].changedAt) +
                    '</td><td>' + getTimeFormat(result[key].sentAt) + '</td><td>' + result[key].comment +
                    '</td><td>' + result[key].worker.name + '</td><td>' + result[key].worker.surname +
                    '</td></tr>';
                $('#orderHistory').append(row);
            }
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
            let row = '<tr class="text"><td>' + result.recipient.name +
                '</td><td>' + result.recipient.surname + '</td><td>' +
                getTimeFormat(result.sendingTime) + '</td><td>' + result.price +
                '</td><td>' + result.state.state + '</td><td>' + result.departurePoint.location + '</td><td>' +
                result.currentLocation.location + '</td><td>' + result.destinationPlace.location +
                '</td><td>' + result.parcelParameters.width + '</td><td>' + result.parcelParameters.height +
                '</td><td>' + result.parcelParameters.weight + '</td><td>' + result.parcelParameters.length +
                '</td></tr>';
            $('#orderInfo').append(row);
        }
    });
}


function getTimeFormat(time) {
    let date = new Date(time);

    date.setDate(date.getDate() + 20);

    return ('0' + date.getDate()).slice(-2) + '.'
        + ('0' + (date.getMonth() + 1)).slice(-2) + '.'
        + date.getFullYear() + ' ' + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':' + ('0' + date.getSeconds()).slice(-2);
}

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('sessionTime')).getSeconds()
    if (Math.abs((new Date()).getSeconds() - sessionTimeMinutes) > 30) {
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
        swal({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "error",
        });
    });
}