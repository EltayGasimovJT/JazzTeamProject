getUsersOrders();
jQuery('document').ready(function () {
    if (sessionStorage.getItem('clientPhone') !== null) {
        insertClientInfo();
        insertLogoutButton();
    } else if (sessionStorage.getItem('clientPhone') === null && sessionStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }
})

jQuery("#backToTheActionPageBtnId").on('click', function () {
    checkSession();
    window.location.href = `/homePage.html`;
})

function getUsersOrders() {
    let phoneNumber = sessionStorage.getItem('clientPhone');
    $.ajax({
        url: `/clients/ordersByPhoneNumber/${phoneNumber}`,
        type: 'GET',
        contentType: 'application/json',
        data: {phoneNumber: phoneNumber},
        success: function (result) {
            for (let key = 0, size = result.length; key < size; key++) {
                let row = '<tr class="text"><td>' + result[key].orderTrackNumber +
                    '</td><td class="orderNum">' + result[key].recipient.name + '</td><td>' +
                    result[key].recipient.surname + '</td><td>' + getTimeFormat(result[key].sendingTime) +
                    '</td><td>' + result[key].price + '</td><td>' + result[key].state.state + '</td><td>' +
                    result[key].departurePoint.location + '</td><td>' + result[key].currentLocation.location +
                    '</td><td>' + result[key].destinationPlace.location + '</td><td>' + addBackToOrderListButton() +
                    '</td></tr>';
                $('#orders').append(row);
            }

            $(".additionalInfoButton").click(function (event) {
                    checkSession();
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    $.get(`/orders/findByTrackNumber`, {orderNumber: orderId}, 'application/json').done(function (data) {
                        window.location.href = `/orderInfo.html?orderId=${data.id}&orderNumber=${orderId}`;
                    }).fail(function () {
                        Swal.fire({
                            title: "Ошибка ввода",
                            text: "Данного заказа не существует",
                            icon: "info",
                            showConfirmButton: false,
                            timer: 5000
                        });

                    });
                }
            )
        },
        error: function (exception) {
            swal(exception.message);
        }
    });
}

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

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('sessionTime')).getHours()
    if ((new Date().getHours() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('clientPhone');
        sessionStorage.removeItem('sessionTime');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('sessionTime', (new Date()).toString())
    }
}

function addBackToOrderListButton() {
    return '<button class="btn btn-primary buttonText additionalInfoButton" type="button">'
        + '<span class="glyphicon glyphicon-pencil"></span>Дополнительная информация</button>';
}

function getTimeFormat(time) {
    return moment(time).format("YYYY-MM-DD HH:mm:ss z");
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
            icon: "error",
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
            sessionStorage.removeItem('clientPhone');
            sessionStorage.removeItem('sessionTime');
            window.location.href = `/homePage.html`;
        }
    )
}