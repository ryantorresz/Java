    package com.example.topacoes.repositories;

    import com.example.topacoes.models.Acao;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface AcaoRepository extends JpaRepository<Acao, Long> {
        @Query(value = """
        SELECT * FROM acoes 
        WHERE codigo LIKE '%.SA' OR 
              codigo REGEXP '^[A-Z]{4}[0-9]{1,2}$'
        ORDER BY volume_negociado DESC 
        LIMIT 10
        """, nativeQuery = true)
        List<Acao> findTop10BrasileirasPorVolume();
    }