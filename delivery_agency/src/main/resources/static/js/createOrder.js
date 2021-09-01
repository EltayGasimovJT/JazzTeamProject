let $form;
init()

const INITIAL_SIZE = 1400;
const INITIAL_WEIGHT = 20;
let allCoefficients;

let config = {
    destinationPoint :null,
    parcelWidth: null,
    parcelLength: null,
    parcelHeight: null,
    parcelWeight: null,
}

const price = document.getElementById('price');

document.getElementById('destinationPoint').oninput = function(event) {
    config.destinationPoint = allCoefficients.find(elem => elem.country === event.target.value);

    calcPrice();
};
document.getElementById('parcelWidth').oninput = function(event) {
    config.parcelWidth = +event.target.value;
    calcPrice();
};
document.getElementById('parcelLength').oninput = function(event) {
    config.parcelLength = +event.target.value;
    calcPrice();
};
document.getElementById('parcelHeight').oninput = function(event) {
    config.parcelHeight = +event.target.value;
    calcPrice();
};
document.getElementById('parcelWeight').oninput = (event) => {
    config.parcelWeight = +event.target.value;
    calcPrice();
};

function calcPrice() {
    const volume = config.parcelWidth * config.parcelLength * config.parcelHeight;

    if (volume > INITIAL_SIZE && config.parcelWeight > INITIAL_WEIGHT) {
        price.innerHTML = Math.round(volume * config.destinationPoint.countryCoefficient * (volume / INITIAL_SIZE) * (config.parcelWeight / INITIAL_WEIGHT));
        return;
    }

    if (volume > INITIAL_SIZE) {
        price.innerHTML = Math.round(volume * config.destinationPoint.countryCoefficient * (volume / INITIAL_SIZE));
        return;
    }

    if (config.parcelWeight > INITIAL_WEIGHT) {

        price.innerHTML = Math.round(volume * config.destinationPoint.countryCoefficient * (config.parcelWeight / INITIAL_WEIGHT));
        return;
    }

    price.innerHTML =  Math.round(volume * config.destinationPoint.countryCoefficient);
}

function init() {
    $.ajax({
        url: `http://localhost:8081/coefficients`,
        type: 'GET',
        contentType: 'application/json',
        success: function (result) {
            allCoefficients = result;
        }
    });
}

$('#createOrderForm').submit(function (e) {
    $form = $(this).serializeArray();

    let sender = new ClientDto({
        name: `${$form[0].value}`,
        surname: `${$form[1].value}`,
        phoneNumber: `${$form[2].value}`,
        passportId: `${$form[3].value}`
    });

    let recipient = new ClientDto({
        name: `${$form[4].value}`,
        surname: `${$form[5].value}`,
        phoneNumber: `${$form[6].value}`,
        passportId: `${$form[7].value}`
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
        parcelParameters: parcelParameters
    })

    $.ajax({
        type: 'POST',
        url: `http://localhost:8081/createOrder`,
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(dataForSend)
    }).done(function () {
        console.log('success');
        console.log('success');
    }).fail(function () {
        console.log('fail');
    });
    e.preventDefault();
});

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


