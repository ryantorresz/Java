package teste.demo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

// DTO para validação
public class AgendamentoRequest {
    @NotNull
    private Long medicoId;
    @NotNull
    private Long pacienteId;
    @NotNull
    private LocalDateTime dataHora;

    // Getters e Setters
    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
