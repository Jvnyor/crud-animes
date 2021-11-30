package br.com.josias.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.josias.apirest.model.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {

	public List<Anime> findByName(String name);
}
