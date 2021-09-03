jQuery('document').ready(function () {
    jQuery("#backToTheActionPageBtnId").on('click', function () {
        location.href = "actionPage.html";
    })

    const backgroundModal = document.querySelector('.backGround-modal');
    const modal = document.querySelector('.modal-custom');
    const closeModel = document.querySelector('.modal-close');
    const openModel = document.querySelector('.open-modal');


    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
    })

    closeModel.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    openModel.addEventListener('click', () => {
        backgroundModal.style.visibility = 'visible';
    })


    /*$("#passportIdEnterForm").on("submit", function () {
        $.ajax({
            url: 'http://localhost:8081/clients/byPassport',
            method: 'get',
            dataType: 'text',
            contentType: 'application/json;charset=uft-8',
            data: $(this).serialize(),
            success: function (data) {
                alert(data);
            },

            error: function () {
                alert('ERROR!!!')
            }
        });
    });*/

    $("#passportIdEnterForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            passportId = $form.find("input[name='passportId']").val(),
            url = $form.attr("action");
        console.log(url);
        let geting = $.get(url, {passportId: passportId});
        geting.done(function (data) {
            console.log(data);
            window.location.href = `http://localhost:8081/clientsOrders.html?clientId=${data.id}`;
        });
    });

    /*$("#form").on("submit", function () {
        $.ajax({
            url: '/clientByPassportId',
            method: 'post',
            dataType: 'text',
            contentType: 'application/json;charset=uft-8',
            data: $(this).serialize(),
            success: function (clientTest) {
                alert(clientTest.name);
                location.href = 'clientsOrders.html';
            }
        });
    });*/

    /*$("#createOrderForm").on("submit", function () {
        $.ajax({
            url: '/createOrder',
            method: 'post',
            dataType: 'json',
            contentType: 'application/json;charset=uft-8',
            data: $(this).serialize(),
            success: function () {
                location.href = 'actionPage.html';
            }
        });
    });*/

    /*$("#createOrderForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            name = $form.find("input[name='senderName']").val(), url = $form.attr("action");

        alert(name);
        let posting = $.post(url, {s: term});
        posting.done(function (data) {
            let content = $(data).find("#content");
            $("#foundClient").empty().append(content);
        });
    });*/
})
