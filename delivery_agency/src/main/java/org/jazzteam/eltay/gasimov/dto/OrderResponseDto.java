package org.jazzteam.eltay.gasimov.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    OrderDto orderDto;
    TicketDto ticketDto;
}
