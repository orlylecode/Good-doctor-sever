package io.cogitech.gooddoctor.repository;
import io.cogitech.gooddoctor.domain.Traitement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Traitement entity.
 */
@Repository
public interface TraitementRepository extends JpaRepository<Traitement, Long> {

    @Query(value = "select distinct traitement from Traitement traitement left join fetch traitement.remedes",
        countQuery = "select count(distinct traitement) from Traitement traitement")
    Page<Traitement> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct traitement from Traitement traitement left join fetch traitement.remedes")
    List<Traitement> findAllWithEagerRelationships();

    @Query("select traitement from Traitement traitement left join fetch traitement.remedes where traitement.id =:id")
    Optional<Traitement> findOneWithEagerRelationships(@Param("id") Long id);

}
