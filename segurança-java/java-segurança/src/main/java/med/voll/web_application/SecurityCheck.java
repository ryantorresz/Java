package med.voll.web_application;

import java.security.Provider;
import java.security.Security;
public class SecurityCheck {

    public static void main(String[] args) {
        System.out.println("Iniciando a verificação de segurança...");
        System.out.println("----------------------------------------");

        // Obtém todos os provedores de segurança instalados.
        Provider[] providers = Security.getProviders();

        if (providers.length == 0) {
            System.out.println("Nenhum provedor de segurança encontrado. A segurança pode estar desativada ou mal configurada.");
        } else {
            System.out.println("Provedores de Segurança Encontrados (" + providers.length + "):");
            for (Provider provider : providers) {
                System.out.println("  - Nome: " + provider.getName());
                System.out.println("    Versão: " + provider.getVersion());
                System.out.println("    Informações: " + provider.getInfo());
                System.out.println("    -------------------------");
            }
        }

        // Exemplo de como verificar um algoritmo específico
        System.out.println("----------------------------------------");
        System.out.println("Verificando a disponibilidade de um algoritmo específico (por exemplo, SHA-256):");
        if (Security.getProviders("MessageDigest.SHA-256").length > 0) {
            System.out.println("  - Algoritmo SHA-256 está disponível.");
        } else {
            System.out.println("  - Algoritmo SHA-256 não encontrado. Isso pode indicar um problema com o provedor.");
        }

        System.out.println("----------------------------------------");
        System.out.println("Verificação de segurança concluída.");
    }

}