package com.server.backend.Repository.Labs;



import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.LabItems;

public interface ItiLabItemsRepository extends JpaRepository<LabItems, Long> {

}
