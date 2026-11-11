package com.gestor.incidencias.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Excepcion para cundo se manda un argumento no valido
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,Object>> manejarErroresValidacion(MethodArgumentNotValidException ex){
		Map<String,Object> mapException = new HashMap<>();
		
		// Creamos mapa para manejar los mensajes de errores sobre datos invalidos
		Map<String,String> mapMessageErrors = new HashMap<>();
		for(FieldError error : ex.getBindingResult().getFieldErrors()) {
			mapMessageErrors.put(error.getField(),error.getDefaultMessage());
		}
		
		
		mapException.put("fechaError", LocalDateTime.now());
		mapException.put("message", "Los datos enviados no son validos");
		mapException.put("status", HttpStatus.BAD_REQUEST.value());
		mapException.put("errores", mapMessageErrors);
		
		return new ResponseEntity<>(mapException,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String,Object>> manejarErrorNoEncontrado(EntityNotFoundException ex){
		Map<String,Object> mapNotFound = new HashMap<>();
		
		mapNotFound.put("fechaError", LocalDateTime.now());
		mapNotFound.put("message", "Los datos no existen, favor de verificar");
		mapNotFound.put("status", HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<>(mapNotFound,HttpStatus.NOT_FOUND);
	}
}
