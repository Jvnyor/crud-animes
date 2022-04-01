package com.Jvnyor.animes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Jvnyor.animes.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

	public Page<Anime> findByName(String name, Pageable paegable);
}
