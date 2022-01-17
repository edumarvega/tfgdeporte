package com.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="planificaciones")
public class Planificacione implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -709023351511550048L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descripcion;
	private String observacion;

	@OneToOne
    @JoinColumn(name="sala_id")
	private Sala sala;
			
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private  Date createAt;
		
	@ManyToOne(fetch=FetchType.LAZY)
	private Usuario usuario;
	
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="planificacione_id")
	private List<ItemPlanificacione> items;
	
	
	
	public Planificacione() {
		this.items = new ArrayList<ItemPlanificacione>();
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}	
	
	public List<ItemPlanificacione> getItems() {
		return items;
	}

	public void setItems(List<ItemPlanificacione> items) {
		this.items = items;
	}
	
	public void addItemPlanificacione(ItemPlanificacione item) {
		this.items.add(item);
		
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}


}
