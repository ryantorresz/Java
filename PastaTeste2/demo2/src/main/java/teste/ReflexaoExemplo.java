package teste;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflexaoExemplo {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("teste.Pessoa"); // 1. Adicionei o pacote

            Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);

            Object pessoa = constructor.newInstance("Alice", 30);

            System.out.println("Nome: " + clazz.getMethod("getNome").invoke(pessoa));
            System.out.println("Idade: " + clazz.getMethod("getIdade").invoke(pessoa));

            Field[] fields = clazz.getDeclaredFields();
            System.out.println("\nCampos declarados na classe Pessoa:");
            for (Field field : fields) {
                System.out.println("Nome: " + field.getName() + ", Tipo: " + field.getType().getSimpleName()); // 2. Corrigi field.getNome() para getName()
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.out.println("Construtor ou método não encontrado: " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("Erro ao criar instância ou acessar métodos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}