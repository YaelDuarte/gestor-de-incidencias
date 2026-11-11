package com.gestor.incidencias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActualizarIncidenciaRequest {

	@NotBlank(message = "El titulo es obligatorio")
	private String titulo;
	@NotBlank(message = "La descripcion es obligatoria")
	private String descripcion;
	@NotNull(message = "El tipo de incidencia es obligatorio")
	private Long tipoId;
	
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getTipoId() {
		return tipoId;
	}
	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}
	
	
	
}
