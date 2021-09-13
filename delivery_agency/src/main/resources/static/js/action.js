jQuery('document').ready(function () {
    jQuery("#backToTheActionPageBtnId").on('click', function () {
        location.href = "homePage.html";
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
