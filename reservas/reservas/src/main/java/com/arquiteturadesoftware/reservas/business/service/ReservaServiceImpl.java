package com.arquiteturadesoftware.reservas.business.service;

import com.arquiteturadesoftware.reservas.infrastructure.client.CatalogoClient;
import com.arquiteturadesoftware.reservas.infrastructure.client.UsuarioClient;
import com.arquiteturadesoftware.reservas.infrastructure.entity.Reserva;
import com.arquiteturadesoftware.reservas.infrastructure.enums.StatusReserva;
import com.arquiteturadesoftware.reservas.infrastructure.exceptions.ConflictException;
import com.arquiteturadesoftware.reservas.infrastructure.exceptions.ResourceNotFoundException;
import com.arquiteturadesoftware.reservas.infrastructure.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaServiceImpl implements ReservaService {

    private final ReservaRepository reservaRepository;
    private final CatalogoClient catalogoClient;
    private final UsuarioClient usuarioClient;

    @Override
    @Transactional
    public Reserva salvarReserva(Reserva reserva) {

        validarIntegridadeExterna(reserva);
        if (reservaRepository.verificarSobreposicao(
                reserva.getJogoId(), reserva.getDataInicio(), reserva.getDataFim())) {
            throw new ConflictException("Este jogo já possui uma reserva ativa para o período selecionado.");
        }


        reserva.setDataReserva(LocalDateTime.now());
        reserva.setStatus(StatusReserva.PENDENTE);
        Reserva reservaSalva = reservaRepository.save(reserva);

        //Dispara a chamada via OpenFeign para atualizar o estoque lá no Catálogo
        catalogoClient.decrementarEstoque(reservaSalva.getJogoId());

        return reservaSalva;
    }

    private void validarIntegridadeExterna(Reserva reserva) {
        try {
            catalogoClient.buscarJogoPorId(reserva.getJogoId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Falha na integração: Jogo com ID " + reserva.getJogoId() + " não foi localizado no catálogo.");
        }

        try {
            usuarioClient.buscarUsuarioPorId(reserva.getUsuarioId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Falha na integração: Usuário com ID " + reserva.getUsuarioId() + " não foi localizado no serviço de usuários.");
        }
    }

    @Override
    public List<Reserva> buscarTodas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> buscarPorUsuario(Long usuarioId) {
        List<Reserva> reservas = reservaRepository.findByUsuarioId(usuarioId);
        if (reservas.isEmpty()) {
            throw new ResourceNotFoundException("Nenhuma reserva encontrada para o usuário ID: " + usuarioId);
        }
        return reservas;
    }

    @Override
    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o ID: " + id));

        reserva.setStatus(StatusReserva.CANCELADA);
        reserva.setDataFim(LocalDateTime.now()); // Registra o momento do cancelamento lógico

        reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void confirmarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o ID: " + id));

        if (reserva.getStatus() != StatusReserva.PENDENTE) {
            throw new ConflictException("Apenas reservas PENDENTES podem ser confirmadas.");
        }

        reserva.setStatus(StatusReserva.CONFIRMADA);
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void finalizarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva não encontrada com o ID: " + id));

        if (reserva.getStatus() != StatusReserva.CONFIRMADA) {
            throw new ConflictException("Apenas reservas CONFIRMADAS podem ser finalizadas.");
        }

        reserva.setStatus(StatusReserva.FINALIZADA);
        reservaRepository.save(reserva);
    }
}