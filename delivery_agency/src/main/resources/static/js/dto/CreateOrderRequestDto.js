export class CreateOrderRequestDto {
    constructor(props = {}) {
        this._sender = props.sender;
        this._recipient = props.recipient;
        this._parcelParameters = props.parcelParameters;
        this._destinationPoint = props.destinationPoint;
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
            sender: this._sender.toJSON(),
            recipient: this._recipient.toJSON(),
            destinationPoint: this._destinationPoint,
            parcelParameters: this._parcelParameters.toJSON()
        };
    }
}