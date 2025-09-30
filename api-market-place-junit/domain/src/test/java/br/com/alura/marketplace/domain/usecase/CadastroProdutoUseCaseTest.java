package br.com.alura.marketplace.domain.usecase;

import br.com.alura.marketplace.domain.entity.Produto;
import br.com.alura.marketplace.domain.repository.BucketRepository;
import br.com.alura.marketplace.domain.repository.PetStoreRepository;
import br.com.alura.marketplace.domain.repository.ProdutoRepository;
import br.com.alura.marketplace.domain.repository.QueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static br.com.alura.marketplace.domain.entity.factory.ProdutoFactory.criarProduto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class CadastroProdutoUseCaseTest {

    @InjectMocks
    CadastroProdutoUseCase cadastroProdutoUseCase;

    @Mock
    ProdutoRepository produtoRepository;

    @Mock
    PetStoreRepository petStoreRepository;

    @Mock
    BucketRepository bucketRepository;

    @Mock
    QueueRepository queueRepository;

    @DisplayName("Quando cadastrar um produto")
    @Nested
    class Cadastrar {

        @BeforeEach
        void beforeEach() {
            lenient()
                    .when(petStoreRepository.cadastrarPet(any()))
                    .thenAnswer(invocationOnMock -> {
                        Produto produto = invocationOnMock.getArgument(0);
                        setField(produto, "petStorePetId", 99L);
                        return produto;
                    });
        }

        @DisplayName("Então deve executar com sucesso")
        @Nested
        class Sucesso {

            @BeforeEach
            void beforeEach() {
                when(produtoRepository.save(any()))
                        .thenAnswer(invocationOnMock -> {
                            Produto produto = invocationOnMock.getArgument(0);
                            setField(produto, "produtoId", UUID.fromString("b34f8434-5dfb-4a3e-aa51-bff7ce7dd884"));
                            return produto;
                        });
            }

            @DisplayName("Dado um produto com todos os campos")
            @Test
            void teste1() {
                // Dado
                var produto = criarProduto()
                        .comTodosOsCampos();
                // Quando
                var atual = cadastroProdutoUseCase.cadastrar(produto);
                // Então
                assertThat(atual.getProdutoId())
                        .isNotNull();
            }

            @DisplayName("Dado um produto com todos os campos e com {status}")
            @ParameterizedTest
            @EnumSource(Produto.Status.class)
            void teste2(Produto.Status status) {
                // Dado
                var produto = criarProduto()
                        .comTodosOsCampos();
                setField(produto, "status", status);
                // Quando
                var atual = cadastroProdutoUseCase.cadastrar(produto);
                // Então
                assertThat(atual.getStatus())
                        .isEqualTo(status);
            }

            @DisplayName("Dado um produto com todos os campos e com {status}")
            @ParameterizedTest
            @CsvSource(value = {
                    "AVAILABLE | (Disponível)",
                    "PENDING   | (Pendente)",
                    "SOLD      | (Vendido)",
            }, delimiterString = "|")
            void teste3(Produto.Status status, String finalDaDescricao) {
                // Dado
                var produto = criarProduto()
                        .comTodosOsCampos();
                setField(produto, "status", status);
                // Quando
                var atual = cadastroProdutoUseCase.cadastrar(produto);
                // Então
                assertThat(atual.getDescricao())
                        .endsWith(finalDaDescricao);
            }
        }
    }
}