package com.gestor.incidencias.mapper;

import org.springframework.stereotype.Component;

import com.gestor.incidencias.dto.ActualizarIncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaResponse;
import com.gestor.incidencias.entitys.Incidencia;

@Component
public class IncidenciaMapper {
	
	public IncidenciaResponse toResponse(Incidencia incidencia) {
		
		if (incidencia == null) {
			return null;
		}
		
		IncidenciaResponse incidenciaADevolver = new IncidenciaResponse
				(incidencia.getTitulo(), 
				incidencia.getDescripcion(), 
				incidencia.getFechaCreacion(), 
				incidencia.getEstado(), 
				incidencia.getId(), incidencia.getTipo());
		
		return incidenciaADevolver;
	}
	
	
	public Incidencia toEntity(IncidenciaRequest incidencia) {
		
		Incidencia incidenciaNueva = new Incidencia();
		incidenciaNueva.setTitulo(incidencia.getTitulo());
		incidenciaNueva.setDescripcion(incidencia.getDescripcion());
		
		return incidenciaNueva;
	}
	
	public Incidencia toEditarEntity(ActualizarIncidenciaRequest incidenciaEditar,Incidencia incidencia) {
		incidencia.setTitulo(incidenciaEditar.getTitulo());
		incidencia.setDescripcion(incidenciaEditar.getDescripcion());
		
		return incidencia;
	}
}
