package com.arquiteturadesoftware.reservas.business.dto;

import com.arquiteturadesoftware.reservas.infrastructure.enums.StatusReserva;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    private Long id;
    private Long usuarioId;
    private Long jogoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusReserva status;
}