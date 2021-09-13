let $form;
init()

jQuery("#backToTheActionPageBtnId").on('click', function () {
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
            url: `http://localhost:8081/calculatePrice/${config.destinationPoint.country}`,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(parcelParameters),
            success: function (result) {
                if (validateParams(config) !== true) {
                    swal({
                        title: "Введенные вами данные не соответствуют требованиям",
                        icon: "error",
                    })
                } else {
                    price.innerHTML = " " + result.toFixed(2);
                }
            },
            error: function (exception) {
                swal({
                    title: "Ошибка подсчета суммы",
                    text: exception.responseJSON.message,
                    icon: "error",
                });
            }
        });
    }
}

function validateParams(params) {
    if (params.parcelWidth < 0) {
        swal({
            title: "Ширина не может быть меньше нуля",
            icon: "error",
        });
        return false;
    }
    if (params.parcelLength < 0) {
        swal({
            title: "Длина не может быть меньше нуля",
            icon: "error",
        });
        return false;
    }
    if (params.parcelHeight < 0) {
        swal({
            title: "Высота не может быть меньше нуля",
            icon: "error",
        });
        return false;
    }
    if (params.parcelWeight < 0) {
        swal({
            title: "Вес не может быть меньше нуля",
            icon: "error",
        });
        return false;
    }
    return true;
}

function init() {
    if (localStorage.getItem('workersToken') === null) {
        window.location.href = "http://localhost:8081/homePage.html";
    }

    if (localStorage.getItem('workersToken') !== null) {
        insertLogoutButton();
    }

    $.ajax({
        url: `http://localhost:8081/coefficients`,
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
    $form = $(this).serializeArray();
    let sender = new ClientDto({
        name: `${$form[0].value}`,
        surname: `${$form[1].value}`,
        passportId: `${$form[2].value}`,
        phoneNumber: `${$form[3].value}`
    });

    let recipient = new ClientDto({
        name: `${$form[4].value}`,
        surname: `${$form[5].value}`,
        passportId: `${$form[6].value}`,
        phoneNumber: `${$form[7].value}`
    });

    let parcelParameters = new ParcelParametersDto(
        {
            width: `${$form[9].value}`,
            length: `${$form[10].value}`,
            height: `${$form[11].value}`,
            weight: `${$form[12].value}`
        }
    )

    let dataForSend = new CreateOrderRequestDto({
        sender: sender,
        recipient: recipient,
        destinationPoint: `${$form[8].value}`,
        parcelParameters: parcelParameters,
        price: price.innerText
    })
    if (validateParams(config) !== true) {
        swal({
            title: "Ошибка ввода",
            text: "Введенные вами данные не верны, пожалуйста попробуйте еще раз",
            icon: "error",
        });
    } else {
        $.ajax({
            type: 'POST',
            url: `http://localhost:8081/createOrder`,
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
            let jwtToken = localStorage.getItem('workersToken');
            if (jwtToken !== null) {
                xhr.setRequestHeader("Authorization", 'Bearer ' + jwtToken);
            }
        },
            data: JSON.stringify(dataForSend)
        }).done(function (data) {
            window.location.href = `http://localhost:8081/ticketPage.html?ticketNumber=${data.ticketDto.ticketNumber}&orderId=${data.orderDto.id}`;
        }).fail(function (exception) {
            swal({
                title: "Не удалось создать заказ",
                text: exception.responseJSON.message,
                icon: "error",
            });
        });

        e.preventDefault();
    }
});

function insertLogoutButton() {
    let table = document.getElementById("logoutButtonToInsert");
    table.innerHTML = '<button type="button" class="btn btn-danger logout-button-margin">Выйти</button>';
    const hiddenButton = document.querySelector('.logout-button-margin');
    hiddenButton.addEventListener(
        'click', () => {
            localStorage.removeItem('workersToken');
            window.location.href = `http://localhost:8081/homePage.html`;
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
    var r = [], j = -1;
    for (let key = 0, size = countries.length; key < size; key++) {
        r[++j] = '<option>';
        r[++j] = countries[key].country;
        r[++j] = '</option>';
    }
    $('#towns').append(r.join(''));

}