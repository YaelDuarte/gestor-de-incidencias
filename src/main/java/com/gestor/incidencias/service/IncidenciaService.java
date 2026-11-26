package com.gestor.incidencias.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestor.incidencias.dto.ActualizarIncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaResponse;
import com.gestor.incidencias.entitys.Estado;
import com.gestor.incidencias.entitys.Incidencia;
import com.gestor.incidencias.entitys.Tipo;
import com.gestor.incidencias.mapper.IncidenciaMapper;
import com.gestor.incidencias.repository.IncidenciasRepository;
import com.gestor.incidencias.repository.TiposRepositorys;

import jakarta.persistence.EntityNotFoundException;


@Service
public class IncidenciaService {


	private final IncidenciasRepository incidenciaRepo;
	private final TiposRepositorys tipoRepo;
	private final IncidenciaMapper incidenciaMap;
	
	public IncidenciaService(IncidenciasRepository incidenciaRepo, TiposRepositorys tipoRepo,IncidenciaMapper incidenciaMap) {
		this.incidenciaRepo = incidenciaRepo;
		this.tipoRepo = tipoRepo;
		this.incidenciaMap = incidenciaMap;
	}
	
	@Transactional
	public IncidenciaResponse guardarIncidencia(IncidenciaRequest incidencia) throws  EntityNotFoundException{
		Incidencia conversionDto =incidenciaMap.toEntity(incidencia);
		LocalDateTime fechaActual = LocalDateTime.now();
		// Busqueda del tipo para la asignacion 
		Tipo tipo = tipoRepo.findById(incidencia.getTipoId()).orElseThrow( () -> 
		new EntityNotFoundException("Valor no encontrado"));
		
		conversionDto.setFechaCreacion(fechaActual);
		conversionDto.setEstado(Estado.PENDIENTE);
		conversionDto.setTipo(tipo);
		
		Incidencia response = incidenciaRepo.save(conversionDto);
		return incidenciaMap.toResponse(response);
	}
	
	@Transactional(readOnly = true)
	public IncidenciaResponse findById(Long id) {
		return incidenciaMap.toResponse(buscarIncidenciaPorId(id));
	}
	
	
	// Listamos todas las incidencias que se han creado hasta la fecha
	@Transactional(readOnly = true)
	public List<IncidenciaResponse> findAll(){
		return incidenciaRepo.findAll().stream().map(incidenciaMap::toResponse).toList();
	}
	
	// Buscamos la incidenci a actualizar y posterior actualizamos y devolvemos esa misma incidencia
	@Transactional
	public IncidenciaResponse editarIncidencia(Long id, ActualizarIncidenciaRequest incidenciaRequest) {
		Incidencia incidencia = buscarIncidenciaPorId(id);
		incidencia = incidenciaMap.toEditarEntity(incidenciaRequest, incidencia);
		Tipo tipo = tipoRepo.findById(incidenciaRequest.getTipoId()).orElseThrow( () -> new EntityNotFoundException());
		incidencia.setTipo(tipo);
		return incidenciaMap.toResponse(incidencia);
		
	}
	
	// Implementacion del servicio para eliminar un registro, mediante un ID
	@Transactional
	public void eliminarIncidenciaPorId(Long id) {
		Incidencia incidencia = buscarIncidenciaPorId(id);
		incidenciaRepo.delete(incidencia);
	}
	
	
	private Incidencia buscarIncidenciaPorId(Long id) {
		return incidenciaRepo.findById(id).orElseThrow( () -> new EntityNotFoundException()); 
	}
}
