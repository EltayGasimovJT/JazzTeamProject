jQuery('document').ready(function () {

    const backgroundModal = document.querySelector('.backGround-modal');

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    $("#trackOrderForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            orderNumber = $form.find("input[name='orderNumber']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {orderNumber: orderNumber}, 'application/json');
        geting.done(function (data) {
            window.location.href = `http://localhost:8081/orderInfo.html?orderId=${data.id}`;
        });
    });
})