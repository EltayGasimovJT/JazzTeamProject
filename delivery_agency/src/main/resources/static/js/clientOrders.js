getUsersOrders();

jQuery("#backToTheActionPageBtnId").on('click', function () {
    checkSession();
    window.location.href = `/homePage.html`;
})
const idUnik = Date.now()

function getUsersOrders() {
    checkSession();
    let phoneNumber = localStorage.getItem('clientPhone');
    $.ajax({
        url: `/clients/ordersByPhoneNumber/${phoneNumber}`,
        type: 'GET',
        contentType: 'application/json',
        data: {phoneNumber: phoneNumber},
        success: function (result) {
            var r = [], j = -1;
            for (let key = 0, size = result.length; key < size; key++) {
                r[++j] = '<tr class="text"><td>';
                r[++j] = result[key].orderTrackNumber;
                r[++j] = '</td><td class="orderNum">';
                r[++j] = result[key].recipient.name;
                r[++j] = '</td><td>';
                r[++j] = result[key].recipient.surname;
                r[++j] = '</td><td>';
                r[++j] = getTimeFormat(result[key].sendingTime);
                r[++j] = '</td><td>';
                r[++j] = result[key].price;
                r[++j] = '</td><td>';
                r[++j] = result[key].state.state;
                r[++j] = '</td><td>';
                r[++j] = result[key].departurePoint.location;
                r[++j] = '</td><td>';
                r[++j] = result[key].currentLocation.location;
                r[++j] = '</td><td>';
                r[++j] = result[key].currentLocation.location;
                r[++j] = '</td><td>';
                r[++j] = addBackToOrderListButton();
                r[++j] = '</td></tr>';
                $('#orders').append(r.join(''));
                r = []
            }

            $(".additionalInfoButton").click(function (event) {
                    let orderId = event.target.parentElement.parentElement.firstChild.innerText;
                    let geting = $.get(`/orders/findByTrackNumber`, {orderNumber: orderId}, 'application/json');
                    geting.done(function (data) {
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
            alert(exception.message);
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
    let sessionTimeMinutes = new Date(localStorage.getItem('sessionTime')).getSeconds()
    if ((new Date().getSeconds() - sessionTimeMinutes) > 30) {
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