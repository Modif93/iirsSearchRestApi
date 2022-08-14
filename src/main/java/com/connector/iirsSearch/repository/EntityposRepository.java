package com.connector.iirsSearch.repository;

import com.connector.iirsSearch.model.Entitypos;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityposRepository extends JpaRepository<Entitypos, Long> {
    List<Entitypos> findAll();
    List<Entitypos> findAllBySimulationTime(long lastTime);
}