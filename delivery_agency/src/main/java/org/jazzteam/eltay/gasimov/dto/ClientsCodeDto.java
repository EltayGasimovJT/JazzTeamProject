package org.jazzteam.eltay.gasimov.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = "client")
@ToString(exclude = "client")
@NoArgsConstructor
public class ClientsCodeDto {
    private Long id;
    private String generatedCode;
    @JsonBackReference
    private ClientDto client;
}
