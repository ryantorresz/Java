public static void main(String[] args) {
        class Veiculo {
            void mostrarDetalhes() {
                System.out.println("Detalhes do veículo");
            }
        }

        class Carro extends Veiculo {
            void mostrarDetalhes() {
                super.mostrarDetalhes(); // Chama mostrarDetalhes() da classe Veiculo
                System.out.println("Detalhes do carro");
            }

        }

}
