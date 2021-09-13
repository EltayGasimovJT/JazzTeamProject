jQuery('document').ready(function () {
    let idFromUrl = getIdFromUrl();
    swal({
        title: "Заказ успешно создан",
        icon: "success",
    });

    $('#ticketSpanId').append(idFromUrl.ticketNumber);
    getOrder(idFromUrl.orderId);

    jQuery("#downloadTicketPdf").on('click', function () {
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
                        'Фамилия и имя Адресата: ' + order.recipient.name + " " + order.recipient.surname + '\n' +
                        'Место отправки: ' + order.destinationPlace.location + '\n' +
                        'Место назначения: ' + order.destinationPlace.location + '\n' +
                        'Вермя отправки: ' + getTimeFormat(order.sendingTime) + '\n' +
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
    let date = new Date(time);

    date.setDate(date.getDate() + 20);

    return ('0' + date.getDate()).slice(-2) + '.'
        + ('0' + (date.getMonth() + 1)).slice(-2) + '.'
        + date.getFullYear() + ' ' + ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2) + ':' + ('0' + date.getSeconds()).slice(-2);
}