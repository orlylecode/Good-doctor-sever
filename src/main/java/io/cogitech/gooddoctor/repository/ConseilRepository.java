package io.cogitech.gooddoctor.repository;
import io.cogitech.gooddoctor.domain.Conseil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Conseil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConseilRepository extends JpaRepository<Conseil, Long> {

}
