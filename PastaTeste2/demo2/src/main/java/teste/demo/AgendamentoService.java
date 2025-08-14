package teste.demo;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import teste.demo.Agendamento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              MedicoRepository medicoRepository,
                              PacienteRepository pacienteRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Agendamento criarAgendamento(Long medicoId, Long pacienteId, LocalDateTime dataHora) {
        // Validações de integridade
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado"));
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));

        // Validações de negócio
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data e hora devem ser futuras");
        }
        if (dataHora.getHour() < 8 || dataHora.getHour() >= 18 || dataHora.getMinute() != 0) {
            throw new IllegalArgumentException("Horário deve ser entre 08:00 e 18:00, em intervalos de hora cheia");
        }

        // Verificar conflitos (JPQL)
        LocalDateTime inicio = dataHora.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime fim = inicio.plusHours(1);
        List<Agendamento> conflitos = agendamentoRepository.findConflitosByMedicoAndPeriodo(medicoId, inicio, fim);
        if (!conflitos.isEmpty()) {
            throw new IllegalArgumentException("Horário já ocupado para o médico");
        }

        // Criar e salvar agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataHora(inicio);
        return agendamentoRepository.save(agendamento);
    }
}