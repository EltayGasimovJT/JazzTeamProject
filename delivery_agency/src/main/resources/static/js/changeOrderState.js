$("#trackOrderForm").submit(function (event) {
    event.preventDefault();
    let $form = $(this),
        orderNumber = $form.find("input[name='orderNumber']").val(),
        url = $form.attr("action");
    let geting = $.get(url, {orderNumber: orderNumber}, 'application/json');
    geting.done(function (data) {
    }).fail(function () {
        swal({
            title: "Ошибка ввода",
            text: "Данного заказа не существует",
            icon: "error",
        });

    });
});