package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2112119666019776186L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String usuario;

	private String email;

	private String password;

	private String nombrecompleto;
	
	@OneToMany(mappedBy="usuario", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Planificacione> planificaciones;
	
	@OneToOne
	@JoinColumn(name="rol_id")
	private Rol rol;
	
	

	public Usuario() {
		planificaciones = new ArrayList<Planificacione>();
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
		this.usuario = usuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public void setNombrecompleto(String nombrecompleto) {
		this.nombrecompleto = nombrecompleto;
	}


	public List<Planificacione> getPlanificaciones() {
		return planificaciones;
	}

	public void setPlanificaciones(List<Planificacione> planificaciones) {
		this.planificaciones = planificaciones;
	}

	public void addPlanificacione(Planificacione planificacione) {
		planificaciones.add(planificacione);
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
