jQuery('document').ready(function () {

    let order = getIdFromUrl();
    getOrderHistories(order.orderId);
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
            let r = [], j = -1;

            for (let key = 0, size = result.length; key < size; key++) {
                r[++j] = '<tr><td>';
                r[++j] = result[key].sentAt;
                r[++j] = '</td><td>';
                r[++j] = result[key].changedAt;
                r[++j] = '</td><td>';
                r[++j] = result[key].comment;
                r[++j] = '</td></tr>';
            }
            console.log(result);
            $('#histories').append(r.join(''));
        }
    });
}