package com.arquiteturadesoftware.reservas.business.service;

import com.arquiteturadesoftware.reservas.infrastructure.entity.Reserva;

import java.util.List;

public interface ReservaService {
    Reserva salvarReserva(Reserva reserva);
    List<Reserva> buscarTodas();
    List<Reserva> buscarPorUsuario(Long usuarioId);
    void cancelarReserva(Long id);
    void confirmarReserva(Long id);
    void finalizarReserva(Long id);
}