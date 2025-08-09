interface Animal {
    String fazerSom();
}

abstract class AnimalBase implements Animal {
    protected String nome;

    public AnimalBase(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

class Cachorro extends AnimalBase {
    public Cachorro(String nome) {
        super(nome);
    }

    @Override
    public String fazerSom() {
        return nome + " diz: Au Au!";
    }
}

class Gato extends AnimalBase {
    public Gato(String nome) {
        super(nome);
    }

    @Override
    public String fazerSom() {
        return nome + " diz: Miau!";
    }
}

public class ExemploOOP {
    public static void main(String[] args) {
        Animal[] animais = {
                new Cachorro("Rex"),
                new Gato("Mimi")
        };

        for (Animal animal : animais) {
            System.out.println(animal.fazerSom());
        }
    }
}