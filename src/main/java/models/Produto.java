package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import services.errors.CampoRequerido;

@Entity
@Table(name="table_produto")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	//Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@CampoRequerido(valor = 1, mensagem = "O nome do produto deve ser informado")
	@NotNull(message = "O nome do produto deve ser informado")	
	@Column(name = "nome")
	private String nome;
	
	@CampoRequerido(valor = 2, mensagem = "O preço do produto deve ser informado")
	@NotNull(message = "O preço do produto deve ser informado")	
	@Column(name = "preco")
	private double preco;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="table_produto_categoria",
	joinColumns = @JoinColumn(name="id_produto"),
	inverseJoinColumns= @JoinColumn(name = "id_categoria"))
	private List<Categoria> categorias = new ArrayList<Categoria>();
	
	@OneToMany(mappedBy="produto")
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_codigo", referencedColumnName = "id")
	private Codigo codigo;
	
	//Constructors
	
	public Produto() {
	}

	public Produto(Long id, String nome, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	//Methods
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public double getPreco() {
		return preco;
	}
	
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		long temp;
		temp = Double.doubleToLongBits(preco);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (Double.doubleToLongBits(preco) != Double.doubleToLongBits(other.preco))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", nome=" + nome + ", preco=" + preco + "]";
	}

}
