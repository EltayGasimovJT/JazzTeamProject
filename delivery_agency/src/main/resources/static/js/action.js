jQuery('document').ready(function () {
    jQuery("#backToTheActionPageBtnId").on('click', function () {
        location.href = "actionPage.html";
    })

    jQuery("#closeRegFormId").on('click', function () {
        closeForm();
    })

    function openForm() {
        document.getElementById("findByPassportIdForm").style.display = "block";
    }

    function closeForm() {
        document.getElementById("findByPassportIdForm").style.display = "none";
    }

    const backgrounDModal = document.querySelector('.backGround-modal');
    const modal = document.querySelector('.modal-custom');
    const closeModel = document.querySelector('.modal-close');
    const openModel = document.querySelector('.open-modal');
    const openClientsOrder = document.querySelector('.open-clientsOrder');
    const createOrder = document.querySelector('.create-order');

    backgrounDModal.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'hidden';
    })

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
    })

    closeModel.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'hidden';
    })

    openModel.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'visible';
    })

    openClientsOrder.addEventListener('click', () => {
        location.href = "clientsOrders.html";
    })

    createOrder.addEventListener('click', () => {
        location.href = "createOrder.html";
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
        let geting = $.get(url, {passportId: passportId});
        geting.done(function (data) {
            let content = $(data).find("#content");
            $("#foundClient").empty().append(content);
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

    function _getOrderData(){
        const senderName = $("#passportIdEnterForm").val();

        return {
            sender:{
                senderName: senderName
            },
            recipient:{

            }
        }
    }

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
