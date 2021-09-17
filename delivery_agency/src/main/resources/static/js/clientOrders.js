getUsersOrders();

jQuery("#backToTheActionPageBtnId").on('click', function () {
    checkSession();
    window.location.href = `/homePage.html`;
})

function getUsersOrders() {
    checkSession();
    let phoneNumber = localStorage.getItem('clientPhone');
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
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    $.get(`/orders/findByTrackNumber`, {orderNumber: orderId}, 'application/json').done(function (data) {
                        window.location.href = `/orderInfo.html?orderId=${data.id}&orderNumber=${orderId}`;
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
    let sessionTimeMinutes = new Date(localStorage.getItem('sessionTime')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 4) {
        localStorage.removeItem('clientPhone');
        localStorage.removeItem('sessionTime');
        window.location.href = `/homePage.html`;
    } else {
        localStorage.setItem('sessionTime', (new Date()).toString())
    }
}

function addBackToOrderListButton() {
    return '<button class="customHiddenBtn buttonText additionalInfoButton" type="button">'
        + '<span class="glyphicon glyphicon-pencil"></span>Дополнительная информация</button>';
}

function getTimeFormat(time) {
    let date = new Date(time);

    date.setDate(date.getDate() + 20);

    return ('0' + date.getDate()).slice(-2) + '.'
        + ('0' + (date.getMonth() + 1)).slice(-2) + '.'
        + date.getFullYear() + ' ' + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':' + ('0' + date.getSeconds()).slice(-2);
}