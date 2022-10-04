package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "codigo")
public class Codigo implements Serializable{

	private static final long serialVersionUID = -2666348852462257102L;
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "numero")
	private Integer numero;
	
	@OneToOne(mappedBy = "codigo")
	private Produto produto;

}
