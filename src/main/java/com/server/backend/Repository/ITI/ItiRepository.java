package com.server.backend.Repository.ITI;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import com.server.backend.entity.Iti;

public interface ItiRepository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT d.dist_name, i.iti_code, i.ncvt_code, i.iti_name " +
                   "FROM iti i " +
                   "LEFT JOIN dist_mst d ON i.dist_code = d.dist_code " +
                   "WHERE (:govt IS NULL OR i.govt = :govt) " +
                   "ORDER BY d.dist_name, i.iti_name", nativeQuery = true)
    List<Object[]> findItiListRows(@Param("govt") String govt);

    Optional<Iti> findByItiCodeAndDistCode(String itiCode, String distCode);
        List<Iti> findByDistCode(String distCode);

    // New method for Govt/Pvt API (added from varshitha module)
    List<Iti> findByGovt(String govt);
}