package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import com.server.backend.entity.Iti;

public interface ItiRepository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT i.iti_code, i.iti_name, m.trade_name, t.strength " +
               "FROM iti i " +
               "LEFT JOIN ititrade t ON i.iti_code = t.iti_code " +
               "LEFT JOIN ititrade_master m ON t.trade_short = m.trade_short " +
               "WHERE i.dist_code = :distCode AND i.govt = :govt", nativeQuery = true)
    List<Object[]> findTradeDisplayRowsByDistrictCodeAndGovt(@Param("distCode") String distCode, @Param("govt") String govt);



    @Query(value = "SELECT i.iti_code, i.iti_name, m.trade_name, t.strength " +
                   "FROM iti i " +
                   "LEFT JOIN ititrade t ON i.iti_code = t.iti_code " +
                   "LEFT JOIN ititrade_master m ON t.trade_short = m.trade_short " +
                   "WHERE i.dist_code = :distCode", nativeQuery = true)
    List<Object[]> findTradeDisplayRowsByDistrictCode(@Param("distCode") String distCode);

    @Query(value = "SELECT d.dist_name, i.iti_code, i.ncvt_code, i.iti_name " +
                   "FROM iti i " +
                   "LEFT JOIN dist_mst d ON i.dist_code = d.dist_code " +
                   "WHERE (:govt IS NULL OR i.govt = :govt) " +
                   "ORDER BY d.dist_name, i.iti_name", nativeQuery = true)
    List<Object[]> findItiListRows(@Param("govt") String govt);

    Optional<Iti> findByItiCodeAndDistCode(String itiCode, String distCode);
    List<Iti> findByDistCode(String distCode);
}