jQuery('document').ready(function () {
    let order = getIdFromUrl();
    getOrderHistories(order.orderId);
    getOrder(order.orderId);
    $('#orderId').append(order.orderNumber);
})

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

        }
    });
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
            r[++j] = '</td></tr>';
            $('#orderInfo').append(r.join(''));
        }
    });
}

function getTimeFormat(time) {
    let sendingTime = new Date(time);
    return sendingTime.getDay() + "." + sendingTime.getMonth() + "." + sendingTime.getFullYear() + " " + sendingTime.getHours() + ":" + sendingTime.getMinutes() + ":" + sendingTime.getSeconds();
}