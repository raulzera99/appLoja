package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import services.errors.CampoRequerido;


@Entity
@Table(name="table_codigo")
public class Codigo implements Serializable{

	private static final long serialVersionUID = -2666348852462257102L;
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@CampoRequerido(valor = 1, mensagem = "O numero de série do código deve ser informado")
	@NotNull(message = "O número de série do código deve ser informado")	
	@Column(name = "numero")
	private Integer numero;
	
	@OneToOne(mappedBy = "codigo")
	@JoinColumn(name="id_produto", referencedColumnName = "id")
	private Produto produto;
	
	public Codigo() {}
	
	public Codigo(Integer numero, Produto produto) {
		super();
		this.numero = numero;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
