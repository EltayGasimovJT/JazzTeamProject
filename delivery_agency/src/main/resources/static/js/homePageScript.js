jQuery('document').ready(function () {
    if (sessionStorage.getItem('clientsPhone') !== null) {
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
        if (sessionStorage.getItem('workersToken') !== null) {
            Swal.fire({
                title: "Вы уверены, что хотите выйти из рабочей учетной записи?",
                icon: "warning",
                showDenyButton: true,
                confirmButtonText: 'Выйти',
                denyButtonText: `Отмена`,
            })
                .then((willDelete) => {
                    if (willDelete.isConfirmed) {
                        sessionStorage.removeItem('workersToken');
                        sessionStorage.removeItem('workerSession');
                    } else if (willDelete.isDenied) {
                        window.location.href = "/homePage.html";
                    }
                });
        }
        if (sessionStorage.getItem('clientPhone') !== null) {
            window.location.href = "/clientsOrders.html";
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
        if (sessionStorage.getItem('clientPhone') !== null) {
            sessionStorage.removeItem('clientPhone');
            sessionStorage.removeItem('sessionTime');
        }
        if (sessionStorage.getItem('workersToken') !== null) {
            window.location.href = "/processingPointWorkerActionPage.html";
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
            Swal.fire({
                icon: 'info',
                title: "На ваш номер телефона был выслан четырехзначный код подтверждения",
                showConfirmButton: false,
                timer: 5000
            }).then(function () {
                Swal.fire({
                    text: data.code.generatedCode,
                    icon: 'info',
                    title: "Четырехзначный код из смс",
                    showConfirmButton: false,
                    timer: 7000
                })
            });
            clientPhoneNumber = phoneNumber;
            codeBackgroundModal.style.visibility = 'visible';
        }).fail(function () {
            Swal.fire({
                text: "Пользователь с таким номером не зарегистрирован",
                title: "Неверный номер телефона",
                icon: 'info',
                showConfirmButton: false,
                timer: 5000
            })
        });
    })

    $('#codeInput').submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            code = $form.find("input[name='code']").val(),
            url = $form.attr("action");
        let geting = $.get(url, {code: code}, 'application/json');
        geting.done(function () {
            sessionStorage.setItem('clientPhone', clientPhoneNumber);
            sessionStorage.setItem('sessionTime', (new Date()).toString())
            window.location.href = `/clientsOrders.html`;
        }).fail(function () {
            Swal.fire({
                title: "Неверный код, повторите попытку",
                text: "Введенный вами четырехзначный код неправильный, пожалуйста повторите попытку",
                icon: "info",
                showConfirmButton: false,
                timer: 5000
            });
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
            sessionStorage.setItem('workersToken', data.token)
            sessionStorage.setItem('workerSession', (new Date()).toString())
            window.location.href = "/processingPointWorkerActionPage.html";
        }).fail(function () {
            Swal.fire({
                title: "Неправильный логин или пароль",
                text: "Пожалуйста попробуйте ввести данные сотрудника еще раз",
                icon: "info",
                showConfirmButton: false,
                timer: 5000
            });
        });
    })

    $("#trackOrderForm").submit(function (event) {
        event.preventDefault();
        let $form = $(this),
            orderNumber = $form.find("input[name='orderNumber']").val(),
            url = $form.attr("action");
        $.get(url, {orderNumber: orderNumber}, 'application/json').done(function (data) {
            window.location.href = `/orderInfo.html?orderId=${data.id}&orderNumber=${orderNumber}`;
        }).fail(function () {
            Swal.fire({
                title: "Ошибка ввода",
                text: "Данного заказа не существует",
                icon: "info",
            });

        });
    });
})

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('sessionTime')).getHours()
    if ((new Date().getHours() - sessionTimeMinutes) > 1) {
        sessionStorage.removeItem('clientPhone');
        sessionStorage.removeItem('sessionTime');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('sessionTime', (new Date()).toString())
    }
}