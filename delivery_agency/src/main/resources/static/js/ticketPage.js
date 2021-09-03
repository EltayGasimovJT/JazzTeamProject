let idFromUrl = getIdFromUrl();

console.log(idFromUrl.orderId);
getUsersOrders();

function getUsersOrders() {
    $.ajax({
        url: `http://localhost:8081/generateTicket/${idFromUrl.orderId}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            console.log(result);
        }
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