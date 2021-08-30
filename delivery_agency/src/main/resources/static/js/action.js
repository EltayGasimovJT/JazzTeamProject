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
    const createOrder = document.querySelector('.create-order');

    openModel.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'visible';
    })

    backgrounDModal.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'hidden';
    })

    modal.addEventListener('click', (event) => {
        event.stopPropagation();
    })

    closeModel.addEventListener('click', () => {
        backgrounDModal.style.visibility = 'hidden';
    })

    createOrder.addEventListener('click', () => {
        location.href = "createOrder.html";
    })


    $("#passportIdEnterForm").on("submit", function () {
        $.ajax({
            url: '/clients/byPassport',
            method: 'get',
            dataType: 'text',
            contentType: 'application/json;charset=uft-8',
            data: $(this).serialize(),
            success: function (data) {
                console.log(typeof(data));
            },

            error: function () {
                alert('ERROR!!!')
            }
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

    $("#createOrderForm").on("submit", function () {
        $.ajax({
            url: '/createOrder',
            method: 'post',
            dataType: 'json',
            contentType: 'application/json;charset=uft-8',
            data: $(this).serialize(),
            success: function () {
                location.href = 'actionPage.html';
            },

            error: function (){
                alert("Error")
            }
        });
    });
})
