jQuery('document').ready(function () {
    let idFromUrl = getIdFromUrl();

    $('#ticketSpanId').append(idFromUrl.ticketNumber);
    getOrder(idFromUrl.orderId);
})

function getOrder(orderId) {
    $.ajax({
        type: 'GET',
        url: `http://localhost:8081/orders/${orderId}`,
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        console.log(data.history[0].sentAt);
        let date = new Date(data.history[0].sentAt);
        console.log(data);
        console.log(date);
        console.log(date.getFullYear() + " " + date.getHours() +
            ":" + date.getMinutes() + ":" + date.getSeconds());
    }).fail(function () {
        console.log('fail');
    });
}

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