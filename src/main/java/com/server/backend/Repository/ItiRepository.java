package com.server.backend.Repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.server.backend.entity.Iti;

public interface ItiRepository extends JpaRepository<Iti, String> {

<<<<<<< HEAD
    /*@Query(value = "SELECT i.iti_code, i.iti_name, m.trade_name, t.strength " + // Added i.iti_code
=======
    @Query(value = "SELECT i.iti_code, i.iti_name, m.trade_name, t.strength " +
>>>>>>> 1fed77977b61eac06cc5b58df75ac415e00c2be3
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
<<<<<<< HEAD
    List<Object[]> findItiAndTradeNamesByDistrictCode(@Param("distCode") String distCode);*/
}
=======
    List<Object[]> findTradeDisplayRowsByDistrictCode(@Param("distCode") String distCode);
}
>>>>>>> 1fed77977b61eac06cc5b58df75ac415e00c2be3
