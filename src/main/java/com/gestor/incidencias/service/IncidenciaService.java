package com.gestor.incidencias.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gestor.incidencias.controller.IncidenciaController;
import com.gestor.incidencias.dto.ActualizarIncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaResponse;
import com.gestor.incidencias.entitys.Estado;
import com.gestor.incidencias.entitys.Incidencia;
import com.gestor.incidencias.entitys.Tipo;
import com.gestor.incidencias.repository.IncidenciasRepository;
import com.gestor.incidencias.repository.TiposRepositorys;

import jakarta.persistence.EntityNotFoundException;


@Service
public class IncidenciaService {


	private final IncidenciasRepository incidenciaRepo;
	private final TiposRepositorys tipoRepo;
	
	public IncidenciaService(IncidenciasRepository incidenciaRepo, TiposRepositorys tipoRepo) {
		this.incidenciaRepo = incidenciaRepo;
		this.tipoRepo = tipoRepo;
	}
	
	@Transactional
	public IncidenciaResponse guardarIncidencia(IncidenciaRequest incidencia) throws  EntityNotFoundException{
		Incidencia incidenciaEntity = new Incidencia();
		LocalDateTime fechaActual = LocalDateTime.now();
		// Busqueda del tipo para la asignacion 
		Tipo tipo = tipoRepo.findById(incidencia.getTipoId()).orElseThrow( () -> 
		new EntityNotFoundException("Valor no encontrado"));
		
		incidenciaEntity.setTitulo(incidencia.getTitulo());
		incidenciaEntity.setDescripcion(incidencia.getDescripcion());
		incidenciaEntity.setTipo(tipo);
		incidenciaEntity.setFechaCreacion(fechaActual);
		incidenciaEntity.setEstado(Estado.PENDIENTE);
		
		Incidencia response = incidenciaRepo.save(incidenciaEntity);
		return convertirAResponse(response);
	}
	
	@Transactional(readOnly = true)
	public IncidenciaResponse findById(Long id) {
		Incidencia entrada = incidenciaRepo.findById(id).orElseThrow( () -> new EntityNotFoundException());
		return convertirAResponse(entrada);
	}
	
	
	// Listamos todas las incidencias que se han creado hasta la fecha
	@Transactional(readOnly = true)
	public List<IncidenciaResponse> findAll(){
		return incidenciaRepo.findAll().stream().map(this::convertirAResponse).toList();
	}
	
	// Buscamos la incidenci a actualizar y posterior actualizamos y devolvemos esa misma incidencia
	@Transactional
	public Incidencia editarIncidencia(Long id, ActualizarIncidenciaRequest incidenciaRequest) {
		Incidencia incidencia = buscarIncidenciaPorId(id);
		incidencia.setTitulo(incidenciaRequest.getTitulo());
		incidencia.setDescripcion(incidenciaRequest.getDescripcion());
		
		Tipo tipoId = tipoRepo.findById(incidenciaRequest.getTipoId()).orElseThrow( () -> new EntityNotFoundException());
		incidencia.setTipo(tipoId);
		return incidenciaRepo.save(incidencia);
		
	}
	
	// Implementacion del servicio para eliminar un registro, mediante un ID
	@Transactional
	public void eliminarIncidenciaPorId(Long id) {
		Incidencia incidencia = buscarIncidenciaPorId(id);
		incidenciaRepo.delete(incidencia);
	}
	
	
	private IncidenciaResponse convertirAResponse(Incidencia incidencia) {
		return new IncidenciaResponse
				(incidencia.getTitulo(), 
				incidencia.getDescripcion(), 
				incidencia.getFechaCreacion(), 
				incidencia.getEstado(), 
				incidencia.getId(), incidencia.getTipo());
	}
	
	private Incidencia buscarIncidenciaPorId(Long id) {
		return incidenciaRepo.findById(id).orElseThrow( () -> new EntityNotFoundException()); 
	}
}
