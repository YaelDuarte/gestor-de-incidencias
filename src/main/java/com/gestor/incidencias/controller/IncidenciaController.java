package com.gestor.incidencias.controller;




import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.incidencias.dto.ActualizarIncidenciaRequest;
import com.gestor.incidencias.dto.IncidenciaRequest;
import com.gestor.incidencias.entitys.Incidencia;
import com.gestor.incidencias.exception.GlobalExceptionHandler;
import com.gestor.incidencias.service.IncidenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/incidencias")
public class IncidenciaController {

    private final GlobalExceptionHandler globalExceptionHandler;

	// Hacemos la inyeccion del servicio mediante constructor
	private final IncidenciaService incidenciaS;
	public IncidenciaController (IncidenciaService incidenciaS, GlobalExceptionHandler globalExceptionHandler) {
		this.incidenciaS = incidenciaS;
		this.globalExceptionHandler = globalExceptionHandler;
	}
	
	
	@PostMapping
	public ResponseEntity<Incidencia> guardarIncidencia(@Valid @RequestBody IncidenciaRequest incidencia){
		Incidencia incidenciaNueva = incidenciaS.guardarIncidencia(incidencia);
		return new ResponseEntity<>(incidenciaNueva,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Incidencia>> listaIncidencias(){
		ArrayList<Incidencia> listaIncidencias = (ArrayList<Incidencia>) incidenciaS.findAll();
		return new ResponseEntity<>(listaIncidencias,HttpStatus.OK);		
	}
	
	// Busqueda centralizada por id unico de la incidencia 
	@GetMapping("/{id}")
	public Incidencia buscarIncidenciaPorId(@PathVariable Long id) {
		return incidenciaS.findById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Incidencia> modificarIncidencia(@PathVariable Long id,
			@Valid @RequestBody ActualizarIncidenciaRequest incidencia){
		Incidencia incidenciaModificada = incidenciaS.editarIncidencia(id, incidencia);
		return new ResponseEntity<Incidencia>(incidenciaModificada,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarIncidencia(@PathVariable Long id){
		incidenciaS.eliminarIncidenciaPorId(id);
		return ResponseEntity.noContent().build();
	}
	
}
