package com.server.backend.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.labs.LabItems;

public interface ItiLabItemsRepository extends JpaRepository<LabItems, Long> {

}
