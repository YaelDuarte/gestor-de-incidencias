package com.gestor.incidencias.dto;

import java.time.LocalDateTime;

import com.gestor.incidencias.entitys.Estado;
import com.gestor.incidencias.entitys.Tipo;

public class IncidenciaResponse {
	
	private String titulo;
	private String descripcion;
	private LocalDateTime fechaCreacion;
	private Estado estado;
	private Long id;
	private String nombreTipo;
	
	
	// CREACION DEL CONSTRUCTOR PARA EL RESPONSE
	public IncidenciaResponse (String titulo, String descripcion, LocalDateTime fechaCreacion, Estado estado, Long id, Tipo nombreTipo) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaCreacion = fechaCreacion;
		this.estado = estado;
		this.id = id;
		this.nombreTipo = nombreTipo.getNombre();
	}
	
	
	
	public String getNombreTipo() {
		return nombreTipo;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	public Estado getEstado() {
		return estado;
	}
	public Long getId() {
		return id;
	}
	
	
	
	
}
