jQuery('document').ready(function () {
    let idFromUrl = getIdFromUrl();

    $('#ticketSpanId').append(idFromUrl.ticketNumber);
    getOrder(idFromUrl.orderId);

    jQuery("#downloadTicketPdf").on('click', function () {
        let docInfo = {
            info: {
                title: 'Ticket №' + idFromUrl.ticketNumber,
                author: 'Eltay',
                subject: 'Theme',
                keywords: 'Keywords'
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
                    fontSize: 20,
                    alignment: 'center'
                },
                {
                    text: 'Order tracking number: #' + order.orderTrackNumber + '\n' +
                        'Sender name: ' + order.sender.name + '\n' +
                        'Recipient name: ' + order.recipient.name + '\n' +
                        'Departure point: ' + order.destinationPlace.location + '\n' +
                        'Destination point: ' + order.destinationPlace.location + '\n' +
                        'Sending time: ' + getTimeFormat(order.sendingTime) + '\n' +
                        'Price: ' + order.price + ' BYN\n',
                    fontSize: 15,
                    marginTop: 20
                },
                {
                    text: 'Date__________' + "                      Worker sign__________",
                    fontSize: 15,
                    alignment: 'right',
                    marginTop: 150
                },
                {
                    text: "Client sign__________",
                    fontSize: 15,
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
        url: `http://localhost:8081/orders/${orderId}`,
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
    $('#departurePoint').append(data.destinationPlace.location);
    $('#destinationPoint').append(data.destinationPlace.location);
    $('#sendingTime').append(getTimeFormat(data.sendingTime));
    $('#price').append(data.price + " BYN");
}

function getTimeFormat(time) {
    let MyDate = new Date(time);

    MyDate.setDate(MyDate.getDate() + 20);

    return ('0' + MyDate.getDate()).slice(-2) + '/'
        + ('0' + (MyDate.getMonth()+1)).slice(-2) + '/'
        + MyDate.getFullYear();

/*   /!* let sendingTime = new Date(time);
    return sendingTime.getDay() + "." + sendingTime.getMonth() + "." + sendingTime.getFullYear() + " " + sendingTime*!/.getHours() + ":" + sendingTime.getMinutes() + ":" + sendingTime.getSeconds();*/
}