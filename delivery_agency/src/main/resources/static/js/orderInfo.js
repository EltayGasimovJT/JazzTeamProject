jQuery('document').ready(function () {

    let order = getIdFromUrl();
    let histories = getOrderHistories(order.orderId);

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
            /*let orders = result.orders;
            var r = [], j = -1;

            for (let key = 0, size = orders.length; key < size; key++) {
                r[++j] = '<tr class="text"><td>';
                r[++j] = orders[key].recipient.name;
                r[++j] = '</td><td>';
                r[++j] = orders[key].recipient.surname;
                r[++j] = '</td><td>';
                r[++j] = orders[key].sendingTime;
                r[++j] = '</td><td>';
                r[++j] = orders[key].price;
                r[++j] = '</td><td>';
                r[++j] = orders[key].state.state;
                r[++j] = '</td><td>';
                r[++j] = orders[key].currentLocation.location;
                r[++j] = '</td></tr>';
            }*/
            console.log(result);
            /*
                        $('#orders').append(r.join(''));
            */
        }
    });
}