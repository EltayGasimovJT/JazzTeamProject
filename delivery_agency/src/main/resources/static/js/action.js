jQuery('document').ready(function () {
    jQuery("#backToTheActionPageBtnId").on('click', function () {
        location.href = "homePage.html";
    })

    const backgroundModal = document.querySelector('.backGround-modal');

    document.querySelector('.modal-custom').addEventListener('click', (event) => {
        event.stopPropagation();
    });
    document.querySelector('.modal-close').addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    });
    document.querySelector('.open-modal').addEventListener('click', () => {
        backgroundModal.style.visibility = 'visible';
    });

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    $("#passportIdEnterForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            passportId = $form.find("input[name='passportId']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {passportId: passportId});
        geting.done(function (data) {
            window.location.href = `/clientsOrders.html?clientId=${data.id}`;
        });
    });
})
