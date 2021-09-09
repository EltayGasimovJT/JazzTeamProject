jQuery('document').ready(function () {
    if (localStorage.getItem('clientsPhone') !== null) {
        checkSession();
    }

    const backgroundModal = document.querySelector('.backGround-modal');
    const clientBackgroundModal = document.querySelector('.client-backGround-modal');
    const codeBackgroundModal = document.querySelector('.code-backGround-modal');

    const asUserModal = document.querySelector('.asUserId');
    const asClientModal = document.querySelector('.asClient');
    const modal = document.querySelector('.modal-redirect');
    const openModel = document.querySelector('.open-modal');
    const closeNumberForm = document.querySelector('.input-numberForm');
    const closeCodeFrom = document.querySelector('.input-codeForm');

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

    codeBackgroundModal.addEventListener('click', () => {
        codeBackgroundModal.style.visibility = 'hidden';
    })

    asClientModal.addEventListener('click', () => {
        if (localStorage.getItem('workersToken') !== null) {
            localStorage.removeItem('workersToken');
        }
        if (localStorage.getItem('clientPhone') !== null) {
            window.location.href = "http://localhost:8081/clientsOrders.html";
        } else {
            clientBackgroundModal.style.visibility = 'visible';
        }
    })

    openModel.addEventListener('click', () => {
        backgroundModal.style.visibility = 'visible';
    })

    backgroundModal.addEventListener('click', () => {
        backgroundModal.style.visibility = 'hidden';
    })

    asUserModal.addEventListener('click', () => {
        if (localStorage.getItem('clientPhone') !== null) {
            localStorage.removeItem('clientPhone');
            localStorage.removeItem('sessionTime');
        }
        if (localStorage.getItem('workersToken') !== null) {
            window.location.href = "http://localhost:8081/processingPointWorkerActionPage.html";
        } else {
            backgroundModal.style.visibility = 'visible';
        }
    })

    let clientPhoneNumber;

    $('#phoneNumberInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            phoneNumber = $form.find("input[name='phoneNumber']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {phoneNumber: phoneNumber}, 'application/json');
        geting.done(function (data) {
            alert(data.code.generatedCode)
            clientPhoneNumber = phoneNumber;
            codeBackgroundModal.style.visibility = 'visible';
        }).fail(function (exception) {
            alert(exception.responseJSON.message);
        });
    })

    $('#codeInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            code = $form.find("input[name='code']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {code: code}, 'application/json');
        geting.done(function () {
            localStorage.setItem('clientPhone', clientPhoneNumber);
            localStorage.setItem('sessionTime', (new Date()).toString())
            window.location.href = `http://localhost:8081/clientsOrders.html`;
        }).fail(function () {
            alert("Неверный код, повторите попытку");
        });
    })

    $('#workerAuthInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            login = $form.find("input[name='login']").val(),
            password = $form.find("input[name='password']").val(),
            url = $form.attr("action");

        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({
                login: `${login}`,
                password: `${password}`
            })
        }).done(function (data) {
            console.log(data)
            localStorage.setItem('workersToken', data.token)
            window.location.href = "http://localhost:8081/processingPointWorkerActionPage.html";
        }).fail(function (exception) {
            alert(exception.responseJSON.message);
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
        }).fail(function (exception) {
            alert(exception.responseJSON.message);
        });
    });
})

function checkSession() {
    let sessionTimeMinutes = new Date(localStorage.getItem('sessionTime')).getMinutes()
    if ((new Date().getMinutes() - sessionTimeMinutes) > 5) {
        localStorage.removeItem('clientPhone');
        localStorage.removeItem('sessionTime');
        window.location.href = `http://localhost:8081/homePage.html`;
    } else {
        localStorage.setItem('sessionTime', (new Date()).toString())
    }
}