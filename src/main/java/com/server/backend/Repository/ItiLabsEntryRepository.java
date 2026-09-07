package com.server.backend.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.labs.Labs;

public interface ItiLabsEntryRepository extends JpaRepository<Labs, Long> {
}

