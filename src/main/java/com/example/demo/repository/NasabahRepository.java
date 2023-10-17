package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Nasabah;

public interface NasabahRepository extends JpaRepository<Nasabah, Long> {
	Page<Nasabah> findAll(Pageable pageable);

}
