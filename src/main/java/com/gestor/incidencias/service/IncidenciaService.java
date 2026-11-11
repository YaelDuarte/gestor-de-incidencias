package com.gestor.incidencias.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestor.incidencias.dto.ActualizarIncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaRequest;
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
	public Incidencia guardarIncidencia(IncidenciaRequest incidencia) throws  EntityNotFoundException{
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
		
		return incidenciaRepo.save(incidenciaEntity);
	}
	
	@Transactional(readOnly = true)
	public Incidencia findById(Long id) {
		return incidenciaRepo.findById(id).orElseThrow( () -> new EntityNotFoundException());
	}
	
	
	// Listamos todas las incidencias que se han creado hasta la fecha
	@Transactional(readOnly = true)
	public List<Incidencia> findAll(){
		return incidenciaRepo.findAll();
	}
	
	// Buscamos la incidenci a actualizar y posterior actualizamos y devolvemos esa misma incidencia
	@Transactional
	public Incidencia editarIncidencia(Long id, ActualizarIncidenciaRequest incidenciaRequest) {
		Incidencia incidencia = findById(id);
		incidencia.setTitulo(incidenciaRequest.getTitulo());
		incidencia.setDescripcion(incidenciaRequest.getDescripcion());
		
		Tipo tipoId = tipoRepo.findById(incidenciaRequest.getTipoId()).orElseThrow( () -> new EntityNotFoundException());
		incidencia.setTipo(tipoId);
		return incidenciaRepo.save(incidencia);
		
	}
	
	// Implementacion del servicio para eliminar un registro, mediante un ID
	@Transactional
	public void eliminarIncidenciaPorId(Long id) {
		Incidencia incidencia = findById(id);
		incidenciaRepo.delete(incidencia);
	}
}
