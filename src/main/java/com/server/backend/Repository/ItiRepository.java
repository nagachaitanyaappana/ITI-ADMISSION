package com.server.backend.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.server.backend.entity.Iti;

public interface ItiRepository
        extends JpaRepository<Iti,String> {
}
