jQuery('document').ready(function () {
    let order = getIdFromUrl();
    getOrderHistories(order.orderId);
    getOrder(order.orderId);
    $('#orderId').append(order.orderNumber);
    if (localStorage.getItem('clientPhone') !== null) {
        addBackToOrderListButton();
    }
})
let backToTheClientsOrdersButton = document.getElementById('backToTheClientsOrdersButton');

function getIdFromUrl() {
    let params = window
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
    return params;
}

function getOrderHistories(idFromUrl) {
    $.ajax({
        url: `http://localhost:8081/orders/findHistory/${idFromUrl}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            var r = [], j = -1;
            for (let key = 0, size = result.length; key < size; key++) {
                r[++j] = '<tr class="text"><td>';
                r[++j] = getTimeFormat(result[key].changedAt);
                r[++j] = '</td><td>';
                r[++j] = getTimeFormat(result[key].sentAt);
                r[++j] = '</td><td>';
                r[++j] = result[key].comment;
                r[++j] = '</td><td>';
                r[++j] = result[key].user.name;
                r[++j] = '</td><td>';
                r[++j] = result[key].user.surname;
                r[++j] = '</td></tr>';
            }
            $('#orderHistory').append(r.join(''));
        }
    });
}

function addBackToOrderListButton() {
    let table = document.getElementById("backToTheClientsOrdersButton");
    table.innerHTML = '<button class="customBtn buttonText hiddenButton"  type="button">'
        + '<span class="glyphicon glyphicon-pencil"></span>На страницу заказов</button>';
    const hiddenButton = document.querySelector('.hiddenButton');
    hiddenButton.addEventListener(
        'click', () => {
            window.location.href = `http://localhost:8081/clientsOrders.html`;
        }
    )
}

function getOrder(idFromUrl) {
    $.ajax({
        url: `http://localhost:8081/orders/${idFromUrl}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            let r = [], j = -1;
            r[++j] = '<tr class="text"><td>';
            r[++j] = result.recipient.name;
            r[++j] = '</td><td>';
            r[++j] = result.recipient.surname;
            r[++j] = '</td><td>';
            r[++j] = getTimeFormat(result.sendingTime);
            r[++j] = '</td><td>';
            r[++j] = result.price;
            r[++j] = '</td><td>';
            r[++j] = result.state.state;
            r[++j] = '</td><td>';
            r[++j] = result.departurePoint.location;
            r[++j] = '</td><td>';
            r[++j] = result.currentLocation.location;
            r[++j] = '</td><td>';
            r[++j] = result.destinationPlace.location;
            r[++j] = '</td><td>';
            r[++j] = result.parcelParameters.weight;
            r[++j] = '</td><td>';
            r[++j] = result.parcelParameters.height;
            r[++j] = '</td><td>';
            r[++j] = result.parcelParameters.width;
            r[++j] = '</td><td>';
            r[++j] = result.parcelParameters.length;
            r[++j] = '</td></tr>';
            $('#orderInfo').append(r.join(''));
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