jQuery('document').ready(function () {
    const backgroundModal = document.querySelector('.backGround-modal');
    const clientBackgroundModal = document.querySelector('.client-backGround-modal');
    const codeBackgroundModal = document.querySelector('.code-backGround-modal');

    const asUserModal = document.querySelector('.asUserId');
    const asClientModal = document.querySelector('.asClient');
    const modal = document.querySelector('.modal-redirect');
    const openModel = document.querySelector('.open-modal');
    const closeNumberForm = document.querySelector('.input-numberForm');
    const closeCodeFrom = document.querySelector('.input-codeForm');
    const processingPointBtn = document.querySelector('.processing-point-worker');

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    closeNumberForm.addEventListener(
        'click', (event) => {
            event.stopPropagation();
        }
    )

    closeCodeFrom.addEventListener(
        'click', (event) => {
            event.stopPropagation();
        }
    )

    clientBackgroundModal.addEventListener('click', () => {
        clientBackgroundModal.style.visibility = 'hidden';
    })

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
    })

    codeBackgroundModal.addEventListener('click', (event) => {
        codeBackgroundModal.style.visibility = 'hidden';
    })

    asClientModal.addEventListener('click', () => {
        clientBackgroundModal.style.visibility = 'visible';
    })

    openModel.addEventListener('click', () => {
        backgroundModal.style.visibility = 'visible';
    })

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    asUserModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'visible';
    })

    processingPointBtn.addEventListener('click', () => {
        window.location.href = "http://localhost:8081/processingPointWorkerActionPage.html";
    })

    let generatedCode;

    $('#phoneNumberInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            phoneNumber = $form.find("input[name='phoneNumber']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {phoneNumber: phoneNumber}, 'application/json');
        geting.done(function (data) {
            generatedCode = data.code.generatedCode;
            codeBackgroundModal.style.visibility = 'visible';

        });
    })

    $('#codeInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            code = $form.find("input[name='code']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {code: code}, 'application/json');
        geting.done(function (data) {
            deleteCode(code);
        });
    })

    $("#trackOrderForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            orderNumber = $form.find("input[name='orderNumber']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {orderNumber: orderNumber}, 'application/json');
        geting.done(function (data) {
            window.location.href = `http://localhost:8081/orderInfo.html?orderId=${data.id}&orderNumber=${orderNumber}`;
        });
    });

    function deleteCode(code){
        $.ajax({
            url: `http://localhost:8081/codes/${code}`,
            type: 'DELETE',
            contentType: 'application/json',
            data: {code: code},
            success: function () {
                alert('Успех епт');            }
        });
    }
})