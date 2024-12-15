package com.app.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column (name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column (name = "fecha_actualizacion")
	private LocalDateTime fechaActualizacion;
	
	@Column (name = "detalle")
	private String detalle;
	
	@Enumerated(EnumType.STRING)
	@Column (name = "estado")
	private Estatus estado;

	public Ticket() {
		super();
	}

	public Ticket(Long id, String usuario, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, Estatus estado) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario.toUpperCase();
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		throw new UnsupportedOperationException("La fecha de creación no se puede modificar.");
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Estatus getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = Estatus.fromString(estado);
		this.fechaActualizacion = LocalDateTime.now();
	}
	
	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	@PrePersist
    public void prePersist() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = LocalDateTime.now();
        }
        if (this.fechaActualizacion == null) {
            this.fechaActualizacion = LocalDateTime.now();
        }
    }
}
