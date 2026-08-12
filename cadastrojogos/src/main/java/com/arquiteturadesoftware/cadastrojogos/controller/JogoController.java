package com.arquiteturadesoftware.cadastrojogos.controller;

import com.arquiteturadesoftware.cadastrojogos.business.dto.JogoDTO;
import com.arquiteturadesoftware.cadastrojogos.business.service.JogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogos")
@RequiredArgsConstructor
@Tag(name = "Jogos", description = "Catálogo de Jogos Disponíveis")
public class JogoController {
    private final JogoService jogoService;

    @PostMapping
    @Operation(summary = "Cadastrar Jogos", description = "Cria um novo jogo no catálogo")
    @ApiResponse(responseCode = "200", description = "Jogo cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Jogo já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<JogoDTO> cadastrarJogo(@RequestBody JogoDTO dto) {
        return ResponseEntity.ok(jogoService.salvarJogo(dto));
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Buscar Jogos no Catálogo", description = "Buscar dados do jogo no catálogo")
    @ApiResponse(responseCode = "200", description = "Jogo encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<List<JogoDTO>> vitrine() {
        return ResponseEntity.ok(jogoService.listarJogosVitrine());
    }


    @PutMapping
    @Operation(summary = "Atualizar Dados do Jogo", description = "Atualiza Jogo")
    @ApiResponse(responseCode = "200", description = "Jogo atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<JogoDTO> atualizarJogo(@RequestParam("id") Long id, @RequestBody JogoDTO dto) {
        return ResponseEntity.ok(jogoService.atualizarJogo(id, dto));
    }

    @DeleteMapping
    @Operation(summary = "Deletar Jogo por Id", description = "Deleta Jogo")
    @ApiResponse(responseCode = "200", description = "Jogo deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> excluirJogo(@RequestParam("id") Long id) {
        jogoService.deletaJogoPorId(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar Jogo por ID", description = "Busca os detalhes de um jogo específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Jogo encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<JogoDTO> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jogoService.buscarJogoPorId(id));
    }

    @PatchMapping("/{id}/decrementar-estoque")
    @Operation(summary = "Decrementar Estoque do Jogo", description = "Decrementa uma unidade disponível do jogo quando uma reserva é efetuada")
    @ApiResponse(responseCode = "204", description = "Estoque reduzido com sucesso")
    @ApiResponse(responseCode = "400", description = "Estoque esgotado")
    @ApiResponse(responseCode = "404", description = "Jogo não encontrado")
    public ResponseEntity<Void> decrementarEstoque(@PathVariable("id") Long id) {
        jogoService.decrementarEstoque(id);
        return ResponseEntity.noContent().build();
    }
}