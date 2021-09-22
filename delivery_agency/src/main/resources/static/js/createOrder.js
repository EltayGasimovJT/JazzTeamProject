let $form;
init()

jQuery("#backToTheActionPageBtnId").on('click', function () {
    checkSession();
    location.href = "homePage.html";
})

let allCoefficients;

let config = {
    destinationPoint: null,
    parcelWidth: null,
    parcelLength: null,
    parcelHeight: null,
    parcelWeight: null,
}

const price = document.getElementById('price');

document.getElementById('destinationPoint').oninput = function (event) {
    config.destinationPoint = allCoefficients.find(elem => elem.country === event.target.value);
    calculatePrice();
};
document.getElementById('parcelWidth').oninput = function (event) {
    config.parcelWidth = +event.target.value;
    calculatePrice();
};
document.getElementById('parcelLength').oninput = function (event) {
    config.parcelLength = +event.target.value;
    calculatePrice();
};
document.getElementById('parcelHeight').oninput = function (event) {
    config.parcelHeight = +event.target.value;
    calculatePrice();
};
document.getElementById('parcelWeight').oninput = (event) => {
    config.parcelWeight = +event.target.value;
    calculatePrice();
};

function checkIfRecipientFieldsAreNotEmpty() {
    return document.getElementById('recipientName').value.length === 0 &&
        document.getElementById('recipientSurname').value.length === 0 &&
        document.getElementById('recipientPassportId').value.length === 0;
}

function checkIfSenderFieldsAreNotEmpty() {
    return document.getElementById('senderName').value.length === 0 &&
        document.getElementById('senderSurname').value.length === 0 &&
        document.getElementById('senderPassportId').value.length === 0;
}

document.getElementById('recipientPhoneNumber').oninput = (event) => {
    if (event.target.value.length === 14) {
        $.ajax({
            url: `/clients/findByPhoneNumber`,
            type: 'GET',
            contentType: 'application/json',
            data: {phoneNumber: event.target.value},
            success: function (result) {
                document.getElementById('recipientName').value = result.name;
                document.getElementById('recipientSurname').value = result.surname;
                document.getElementById('recipientPassportId').value = result.passportId;

            }
        });
    }
};

document.getElementById('senderPhoneNumber').oninput = (event) => {
    if (event.target.value.length === 14) {
        $.ajax({
            url: `/clients/findByPhoneNumber`,
            type: 'GET',
            contentType: 'application/json',
            data: {phoneNumber: event.target.value},
            success: function (result) {
                document.getElementById('senderName').value = result.name;
                document.getElementById('senderSurname').value = result.surname;
                document.getElementById('senderPassportId').value = result.passportId;

            }
        });
    }
};

function calculatePrice() {
    if (config.parcelWeight !== null && config.parcelHeight !== null && config.parcelLength !== null && config.parcelWidth !== null && config.destinationPoint !== null) {
        let parcelParameters = new ParcelParametersDto(
            {
                width: `${config.parcelWidth}`,
                length: `${config.parcelLength}`,
                height: `${config.parcelHeight}`,
                weight: `${config.parcelWeight}`
            }
        )
        $.ajax({
            url: `/calculatePrice/${config.destinationPoint.country}`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(parcelParameters),
            success: function (result) {
                if (validateParams(config) !== true) {
                    Swal.fire({
                        icon: 'info',
                        title: "Введенные вами данные не соответствуют требованиям",
                        showConfirmButton: false,
                        timer: 5000
                    })
                } else {
                    price.innerHTML = " " + result.toFixed(2);
                }
            },
            error: function (exception) {
                Swal.fire({
                    icon: 'info',
                    title: "Ошибка подсчета суммы",
                    text: exception.responseJSON.message,
                    showConfirmButton: false,
                    timer: 5000
                })
            }
        });
    }
}

function validateParams(params) {
    if (params.parcelWidth < 0) {
        Swal.fire({
            icon: 'info',
            title: "Ширина не может быть меньше нуля",
            showConfirmButton: false,
            timer: 2000
        })
        return false;
    }
    if (params.parcelLength < 0) {
        Swal.fire({
            icon: 'info',
            title: "Длина не может быть меньше нуля",
            showConfirmButton: false,
            timer: 2000
        })
        return false;
    }
    if (params.parcelHeight < 0) {
        Swal.fire({
            icon: 'info',
            title: "Высота не может быть меньше нуля",
            showConfirmButton: false,
            timer: 2000
        })
        return false;
    }
    if (params.parcelWeight < 0) {
        Swal.fire({
            icon: 'info',
            title: "Вес не может быть меньше нуля",
            showConfirmButton: false,
            timer: 2000
        })
        return false;
    }
    return true;
}

function init() {
    if (sessionStorage.getItem('workersToken') === null) {
        window.location.href = "/homePage.html";
    }

    if (sessionStorage.getItem('workersToken') !== null) {
        insertWorkerInfo();
        insertLogoutButton();
    }

    $.ajax({
        url: `/coefficients`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            allCoefficients = result;
            setupCountries(allCoefficients)
        },
        error: function (exception) {
            exception.responseJSON.message;
        }
    });
}

$('#createOrderForm').submit(function (e) {
    checkSession()

    let sender = new ClientDto({
        name: `${document.getElementById('senderName').value}`,
        surname: `${document.getElementById('senderSurname').value}`,
        passportId: `${document.getElementById('senderPassportId').value}`,
        phoneNumber: `${document.getElementById('senderPhoneNumber').value}`
    });

    let recipient = new ClientDto({
        name: `${document.getElementById('recipientName').value}`,
        surname: `${document.getElementById('recipientSurname').value}`,
        passportId: `${document.getElementById('recipientPassportId').value}`,
        phoneNumber: `${document.getElementById('recipientPhoneNumber').value}`
    });

    let parcelParameters = new ParcelParametersDto(
        {
            width: `${document.getElementById('parcelWidth').value}`,
            length: `${document.getElementById('parcelLength').value}`,
            height: `${document.getElementById('parcelHeight').value}`,
            weight: `${document.getElementById('parcelWeight').value}`
        }
    )

    let dataForSend = new CreateOrderRequestDto({
        sender: sender,
        recipient: recipient,
        destinationPoint: `${document.getElementById('destinationPoint').value}`,
        parcelParameters: parcelParameters,
        price: price.innerText
    })
    if (validateParams(config) !== true ) {
        swal({
            title: "Ошибка ввода",
            text: "Введенные вами данные не верны, пожалуйста попробуйте еще раз",
            icon: "info",
        });

    } else {
        $.ajax({
            type: 'POST',
            url: `/createOrder`,
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                let jwtToken = sessionStorage.getItem('workersToken');
                if (jwtToken !== null) {
                    xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
                }
            },
            data: JSON.stringify(dataForSend)
        }).done(function (data) {
            window.location.href = `/ticketPage.html?ticketNumber=${data.ticketDto.ticketNumber}&orderId=${data.orderDto.id}`;
        }).fail(function (exception) {
            Swal.fire({
                title: "Не удалось создать заказ",
                text: `${exception.responseJSON.message}`,
                icon: "info",
            });
        });

        e.preventDefault();
    }
});
$('#cancel-order-on-create').click(function () {
    Swal.fire({
        title: "Создание заказа было отменено",
        icon: "info",
        timer: 2000,
        showConfirmButton: false
    }).then(() => {
        window.location.reload();
    });
})

function insertLogoutButton() {
    let logoutButtonDiv = document.getElementById("logoutButtonToInsert");
    logoutButtonDiv.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    document.querySelector('.logout-button-margin').addEventListener(
        'click', () => {
            sessionStorage.removeItem('workersToken');
            sessionStorage.removeItem('workerSession');
            window.location.href = `/homePage.html`;
        }
    )
}

export class ClientDto {
    constructor(props = {}) {
        this._id = props.id;
        this._name = props.name;
        this._surname = props.surname;
        this._phoneNumber = props.phoneNumber;
        this._passportId = props.passportId;
        this._orders = props.orders;
    }

    get id() {
        return this._id;
    }

    set id(value) {
        this._id = value;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get surname() {
        return this._surname;
    }

    set surname(value) {
        this._surname = value;
    }

    get passportId() {
        return this._passportId;
    }

    set passportId(value) {
        this._passportId = value;
    }

    get phoneNumber() {
        return this._phoneNumber;
    }

    set phoneNumber(value) {
        this._phoneNumber = value;
    }

    get orders() {
        return this._orders;
    }

    set orders(value) {
        this._orders = value;
    }

    toJSON() {
        return {
            id: this.id,
            name: this.name,
            surname: this.surname,
            passportId: this.passportId,
            phoneNumber: this.phoneNumber,
            orders: this.orders
        };
    }
}

export class CreateOrderRequestDto {
    get price() {
        return this._price;
    }

    set price(value) {
        this._price = value;
    }

    constructor(props = {}) {
        this._sender = props.sender;
        this._recipient = props.recipient;
        this._parcelParameters = props.parcelParameters;
        this._destinationPoint = props.destinationPoint;
        this._price = props.price;
    }

    get sender() {
        return this._sender;
    }

    set sender(value) {
        this._sender = value;
    }

    get recipient() {
        return this._recipient;
    }

    set recipient(value) {
        this._recipient = value;
    }

    get parcelParameters() {
        return this._parcelParameters;
    }

    set parcelParameters(value) {
        this._parcelParameters = value;
    }

    get destinationPoint() {
        return this._destinationPoint;
    }

    set destinationPoint(value) {
        this._destinationPoint = value;
    }

    toJSON() {
        return {
            sender: this.sender.toJSON(),
            recipient: this.recipient.toJSON(),
            destinationPoint: this.destinationPoint,
            parcelParameters: this.parcelParameters.toJSON(),
            price: this.price,
        };
    }
}

export class ParcelParametersDto {
    constructor(props = {}) {
        this._weight = props.weight;
        this._length = props.length;
        this._height = props.height;
        this._width = props.width;
    }

    get weight() {
        return this._weight;
    }

    set weight(value) {
        this._weight = value;
    }

    get length() {
        return this._length;
    }

    set length(value) {
        this._length = value;
    }

    get height() {
        return this._height;
    }

    set height(value) {
        this._height = value;
    }

    get width() {
        return this._width;
    }

    set width(value) {
        this._width = value;
    }

    toJSON() {
        return {
            weight: this.weight,
            height: this.height,
            length: this.length,
            width: this.width
        };
    }
}

function setupCountries(countries) {
    for (let key = 0, size = countries.length; key < size; key++) {
        let row = '<option>' + countries[key].country + '</option>';
        $('#towns').append(row);
    }
}

function insertWorkerInfo() {
    let name = document.getElementById("worker-name-nav");
    let surname = document.getElementById("worker-surname-nav");
    let roles = document.getElementById("worker-role-nav");
    $.ajax({
        type: 'GET',
        url: `/users/getCurrentWorker`,
        contentType: 'application/json; charset=utf-8',
        beforeSend: function (xhr) {
            let jwtToken = sessionStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
    }).done(function (data) {
        name.innerHTML = `Имя: ${data.name}`
        surname.innerHTML = `Фамилия: ${data.surname}`
        if (data.role === "ROLE_ADMIN") {
            roles.innerHTML = `Роль: Администратор`
        }
        if (data.role === "ROLE_WAREHOUSE_WORKER") {
            roles.innerHTML = `Роль: Работник промежуточного склада`
        }
        if (data.role === "ROLE_PROCESSING_POINT_WORKER") {
            roles.innerHTML = `Роль: Работник пункта отправки/выдачи`
        }
    }).fail(function () {
        Swal.fire({
            title: "Что-то пошло не так",
            text: "Ошибка при поиске сотрудника",
            icon: "info",
            showConfirmButton: false,
            timer: 5000
        });
    });
}

function checkSession() {
    let sessionTimeMinutes = new Date(sessionStorage.getItem('workerSession')).getHours()
    if ((new Date().getHours() - sessionTimeMinutes) > 3) {
        sessionStorage.removeItem('workersToken');
        sessionStorage.removeItem('workerSession');
        window.location.href = `/homePage.html`;
    } else {
        sessionStorage.setItem('workerSession', (new Date()).toString())
    }
}