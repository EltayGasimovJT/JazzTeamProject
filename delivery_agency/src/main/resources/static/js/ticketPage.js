jQuery('document').ready(function () {
    if (sessionStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }
    let idFromUrl = getIdFromUrl();
    Swal.fire({
        title: "Заказ успешно создан",
        icon: "success",
        position: 'top-end',
        showConfirmButton: false
    });
    if (sessionStorage.getItem('workersToken') !== null) {
        insertWorkerInfo();
        insertLogoutButton();
    }

    $('#ticketSpanId').append(idFromUrl.ticketNumber);
    getOrder(idFromUrl.orderId);

    jQuery("#downloadTicketPdf").on('click', function () {
        checkSession()
        let docInfo = {
            info: {
                title: 'Ticket №' + idFromUrl.ticketNumber,
                author: 'Eltay',
                subject: 'Theme',
                keywords: 'Keywords',
                fontStyle: 'Calibri'
            },
            pageSize: 'A5',
            pageOrientation: 'portrait',
            pageMargins: [50, 50, 30, 60],

            header: function (currentPage, pageCount) {
                return {
                    text: currentPage.toString() + 'из' + pageCount,
                    alignment: 'right',
                    margin: [0, 30, 10, 50]
                }
            },

            footer: [
                {
                    text: '',
                    alignment: 'right'
                }
            ],
            content: [
                {
                    text: 'Ticket #' + idFromUrl.ticketNumber,
                    fontSize: 20
                },
                {
                    text: 'Номер трекера: #' + order.orderTrackNumber + '\n' +
                        'Фамилия и имя отправителя: ' + order.sender.name + " " + order.sender.surname + '\n' +
                        'Фамилия и имя получателя: ' + order.recipient.name + " " + order.recipient.surname + '\n' +
                        'Место отправки: ' + order.departurePoint.location + '\n' +
                        'Место назначения: ' + order.destinationPlace.location + '\n' +
                        'Время отправки: ' + getTimeFormat(order.sendingTime) + '\n' +
                        'Итоговая стоимость: ' + order.price + ' BYN\n',
                    fontSize: 13,
                    marginTop: 20
                },
                {
                    text: 'Дата__________' + '                    Подпись сотрудника__________',
                    fontSize: 13,
                    alignment: 'left',
                    marginTop: 150
                },
                {
                    text: "Подпись клиента__________",
                    fontSize: 13,
                    alignment: 'right',
                    marginTop: 20
                }
            ]
        }

        pdfMake.createPdf(docInfo).download('Ticket#' + idFromUrl.ticketNumber);
    })
})
let order;

function getOrder(orderId) {
    $.ajax({
        type: 'GET',
        url: `/orders/${orderId}`,
        contentType: 'application/json; charset=utf-8',
    }).done(function (data) {
        order = data;
        insetValuesIntoTicket(data);
    }).fail(function (exception) {
        Swal.fire({
            text: 'Не удалось найти заказ',
            title: `${exception.message}`,
            icons: 'error'
        })
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

function insetValuesIntoTicket(data) {
    $('#trackNumber').append(data.orderTrackNumber);
    $('#senderName').append(data.sender.name + " " + data.sender.surname);
    $('#recipientName').append(data.recipient.name + " " + data.recipient.surname);
    $('#departurePoint').append(data.departurePoint.location);
    $('#destinationPoint').append(data.destinationPlace.location);
    $('#sendingTime').append(getTimeFormat(data.sendingTime));
    $('#price').append(data.price + " BYN");
}

function getTimeFormat(time) {
    return moment(time).format("YYYY-MM-DD HH:mm:ss z");
}

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('workerSession')).getHours()
    if ((new Date().getHours() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('workersToken');
        sessionStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('workerSession', (new Date()).toString())
    }
}

function insertLogoutButton() {
    let logoutButtonDiv = document.getElementById("logoutButtonToInsert");
    logoutButtonDiv.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    document.querySelector('.logout-button-margin').addEventListener(
        'click', () => {
            sessionStorage.removeItem('workersToken');
            sessionStorage.removeItem('workerSession');
            window.location.href = `/homePage.html`;
        }
    )
}

function insertWorkerInfo() {
    let name = document.getElementById("worker-name-nav");
    let surname = document.getElementById("worker-surname-nav");
    let roles = document.getElementById("worker-role-nav");
    $.ajax({
        type: 'GET',
        url: `/users/getCurrentWorker`,
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            let jwtToken = sessionStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
    }).done(function (data) {
        console.log(data)
        console.log(data.name)
        name.innerHTML = `Имя: ${data.name}`
        surname.innerHTML = `Фамилия: ${data.surname}`
        roles.innerHTML = `Роль: ${data.role}`
    }).fail(function () {
        Swal.fire({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "error",
            showConfirmButton: false
        });
    });
}
