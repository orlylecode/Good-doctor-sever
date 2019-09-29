package io.cogitech.gooddoctor.repository;
import io.cogitech.gooddoctor.domain.Symptome;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Symptome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SymptomeRepository extends JpaRepository<Symptome, Long> {

}
