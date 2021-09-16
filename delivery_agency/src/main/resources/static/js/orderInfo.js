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