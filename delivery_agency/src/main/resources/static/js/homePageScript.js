jQuery('document').ready(function () {
    const backgroundModal = document.querySelector('.backGround-modal');
    const asUserModal = document.querySelector('.asUserId');
    const modal = document.querySelector('.modal-redirect');
    const openModel = document.querySelector('.open-modal');
    const processingPointBtn = document.querySelector('.processing-point-worker');

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
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

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
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
})