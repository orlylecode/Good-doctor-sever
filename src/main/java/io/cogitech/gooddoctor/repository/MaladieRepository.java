package io.cogitech.gooddoctor.repository;
import io.cogitech.gooddoctor.domain.Maladie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Maladie entity.
 */
@Repository
public interface MaladieRepository extends JpaRepository<Maladie, Long> {

    @Query(value = "select distinct maladie from Maladie maladie left join fetch maladie.conseils left join fetch maladie.traitements left join fetch maladie.symptomes",
        countQuery = "select count(distinct maladie) from Maladie maladie")
    Page<Maladie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct maladie from Maladie maladie left join fetch maladie.conseils left join fetch maladie.traitements left join fetch maladie.symptomes")
    List<Maladie> findAllWithEagerRelationships();

    @Query("select maladie from Maladie maladie left join fetch maladie.conseils left join fetch maladie.traitements left join fetch maladie.symptomes where maladie.id =:id")
    Optional<Maladie> findOneWithEagerRelationships(@Param("id") Long id);

}
