package com.Jvnyor.animes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Jvnyor.animes.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

	public List<Anime> findByName(String name);
}
