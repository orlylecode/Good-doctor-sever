package io.cogitech.gooddoctor.repository;
import io.cogitech.gooddoctor.domain.Remede;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Remede entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemedeRepository extends JpaRepository<Remede, Long> {

}
