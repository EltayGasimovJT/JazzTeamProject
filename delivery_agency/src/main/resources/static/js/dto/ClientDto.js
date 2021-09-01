export class ClientDto {
    constructor(props = {}) {
        this._id = props.id;
        this._name = props.name;
        this._surname = props.surname;
        this._passportId = props.passportId;
        this._phoneNumber = props.phoneNumber;
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

    toJSON(){
        return {
            id: this._id,
            name: this._name,
            surname: this._surname,
            passportId: this._passportId,
            phoneNumber: this._phoneNumber,
            orders: this._orders
        };
    }
}
