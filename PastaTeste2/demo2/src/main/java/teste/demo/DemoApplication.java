package teste.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import teste.demo.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository) {
        return args -> {
            // Cadastro de médicos iniciais
            Medico medico1 = new Medico();
            medico1.setNome("Dr. João Silva");
            medicoRepository.save(medico1);

            Medico medico2 = new Medico();
            medico2.setNome("Dra. Maria Souza");
            medicoRepository.save(medico2);

            // Cadastro de pacientes iniciais
            Paciente paciente1 = new Paciente();
            paciente1.setNome("Carlos Oliveira");
            pacienteRepository.save(paciente1);

            Paciente paciente2 = new Paciente();
            paciente2.setNome("Ana Costa");
            pacienteRepository.save(paciente2);

            System.out.println("Dados iniciais carregados com sucesso!");
            System.out.println("Acesse: http://localhost:8080/api/agendamentos");
            System.out.println("Console H2: http://localhost:8080/h2-console");
        };
    }
}