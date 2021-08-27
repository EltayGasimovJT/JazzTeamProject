jQuery('document').ready(function () {
    jQuery("#checkOrderBtn").on('click', function () {
        openForm();

        jQuery("#openRegFormId").on('submit', function () {

        })
    })

    jQuery("#createOrderBtn").on('click', function () {
        location.href = "createOrder.html";
    })

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
})