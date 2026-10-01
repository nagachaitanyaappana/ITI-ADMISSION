package com.server.backend.Repository.Labs;


import org.springframework.data.jpa.repository.JpaRepository;

import com.server.backend.entity.Labs;

public interface ItiLabsEntryRepository extends JpaRepository<Labs, Long> {
}

