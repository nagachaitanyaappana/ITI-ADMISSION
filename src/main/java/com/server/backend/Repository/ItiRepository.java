package com.server.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.iti;

public interface ItiRepository
        extends JpaRepository<iti,String> {
}
