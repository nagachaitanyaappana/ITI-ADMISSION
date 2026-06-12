package com.server.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface ItiRepository extends JpaRepository<Iti, String> {

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :distCode AND govt = :govt",nativeQuery = true)
    List<String> findItiNamesByDistrictCodeAndGovt(@Param("distCode") String distCode,@Param("govt") String govt);

    @Query(value = "SELECT iti_name FROM iti WHERE dist_code = :distCode",nativeQuery = true)
    List<String> findItiNamesByDistrictCode(@Param("distCode") String distCode);

    @Query(value = "SELECT iti_code FROM iti WHERE iti_name = :itiName LIMIT 1", nativeQuery = true)
    String findCodeByItiName(@Param("itiName") String itiName);

    @Query(value = "SELECT trade_short FROM ititrade WHERE iti_code = :itiCode", nativeQuery = true)
    List<String> findTradeShortsByItiCode(@Param("itiCode") String itiCode);

    @Query(value = "SELECT i.iti_name, m.trade_name " +
                   "FROM iti i " +
                   "LEFT JOIN ititrade t ON i.iti_code = t.iti_code " +
                   "LEFT JOIN ititrade_master m ON t.trade_short = m.trade_short " +
                   "WHERE i.dist_code = :distCode AND i.govt = :govt", nativeQuery = true)
    List<Object[]> findItiAndTradeNamesByDistrictCodeAndGovt(@Param("distCode") String distCode, @Param("govt") String govt);

    @Query(value = "SELECT i.iti_name, m.trade_name " +
                   "FROM iti i " +
                   "LEFT JOIN ititrade t ON i.iti_code = t.iti_code " +
                   "LEFT JOIN ititrade_master m ON t.trade_short = m.trade_short " +
                   "WHERE i.dist_code = :distCode", nativeQuery = true)
    List<Object[]> findItiAndTradeNamesByDistrictCode(@Param("distCode") String distCode);
}