
package com.server.backend.Repository.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.CasteMaster;
import com.server.backend.entity.SubCasteMaster;

public interface SubCasteMaster_Repo extends JpaRepository<SubCasteMaster, Long> {

    List<SubCasteMaster> findByCasteMaster(CasteMaster casteMaster);

}