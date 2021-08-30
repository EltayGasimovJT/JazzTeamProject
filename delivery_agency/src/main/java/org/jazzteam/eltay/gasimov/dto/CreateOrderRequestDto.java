package org.jazzteam.eltay.gasimov.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderRequestDto {
    private String senderName;
    private String senderSurname;
    private String senderPhoneNumber;
    private String senderPassportId;
    private String recipientName;
    private String recipientSurname;
    private String recipientPhoneNumber;
    private String recipientPassportId;
    private String destinationPlaceTown;
    private Double weight;
    private Double height;
    private Double length;
    private Double width;
}
