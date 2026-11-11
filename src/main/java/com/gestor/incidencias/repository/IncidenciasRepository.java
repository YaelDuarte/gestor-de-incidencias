package com.gestor.incidencias.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestor.incidencias.dto.IncidenciaRequest;
import com.gestor.incidencias.entitys.Incidencia;

public interface IncidenciasRepository extends JpaRepository<Incidencia, Long>{

	void save(IncidenciaRequest incidencia);

}
