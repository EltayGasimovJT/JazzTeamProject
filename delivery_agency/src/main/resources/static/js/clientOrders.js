getUsersOrders();

jQuery("#backToTheActionPageBtnId").on('click', function () {
    checkSession();
    window.location.href = `http://localhost:8081/homePage.html`;
})

function getUsersOrders() {
    checkSession();
    let phoneNumber = localStorage.getItem('clientPhone');
    console.log(localStorage.getItem('sessionTime'))
    $.ajax({
        url: `http://localhost:8081/clients/ordersByPhoneNumber/${phoneNumber}`,
        type: 'GET',
        contentType: 'application/json',
        data: {phoneNumber: phoneNumber},
        success: function (result) {
            var r = [], j = -1;

            for (let key = 0, size = result.length; key < size; key++) {
                r[++j] = '<tr class="text"><td>';
                r[++j] = result[key].recipient.name;
                r[++j] = '</td><td>';
                r[++j] = result[key].recipient.surname;
                r[++j] = '</td><td>';
                r[++j] = result[key].sendingTime;
                r[++j] = '</td><td>';
                r[++j] = result[key].price;
                r[++j] = '</td><td>';
                r[++j] = result[key].state.state;
                r[++j] = '</td><td>';
                r[++j] = result[key].currentLocation.location;
                r[++j] = '</td></tr>';
            }
            $('#orders').append(r.join(''));

        },
        error: function () {
            alert(localStorage.getItem('clientPhone'));
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

function checkSession(){
    let sessionTimeMinutes = new Date(localStorage.getItem('sessionTime')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 1) {
        localStorage.removeItem('clientPhone');
        localStorage.removeItem('sessionTime');
        window.location.href = `http://localhost:8081/homePage.html`;
    } else {
        localStorage.setItem('sessionTime', (new Date()).toString())
    }
}