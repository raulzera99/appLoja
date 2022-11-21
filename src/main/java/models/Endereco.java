package models;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Table;

import jakarta.validation.constraints.NotNull;
import services.errors.CampoRequerido;

@Entity
@Table(name="table_endereco")
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;
	//Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@CampoRequerido(valor = 1, mensagem = "O número da casa deve ser informado")
	@NotNull(message = "O número da casa deve ser informado")
	@Column(name = "numero")
	private String numero;
	
	@CampoRequerido(valor = 2, mensagem = "O bairro do endereço deve ser informado")
	@NotNull(message = "O bairro do endereço deve ser informado")
	@Column(name = "bairro")
	private String bairro;
	
	@CampoRequerido(valor = 3, mensagem = "O CEP do endereço deve ser informado")
	@NotNull(message = "O CEP do endereço deve ser informado")
	@Column(name = "cep")
	private String cep; 
	
	@ManyToOne
	@JoinColumn(name="id_cliente", referencedColumnName = "id")
	private Cliente cliente;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_cidade", referencedColumnName = "id")
	private Cidade cidade;
	
	@OneToMany(mappedBy = "enderecoDeEntrega", fetch = FetchType.EAGER)
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	
	//Constructors
	public Endereco(Long id, String logradouro, String numero, String complemento, String bairro, String cep,
			Cliente cliente, Cidade cidade) {
		super();
		this.id = id;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cliente = cliente;
		this.setCidade(cidade);
	}


	public Endereco() {
		super();
	}

	//Methods
	
	public Long getId() {
		return id;
	}
	public void setId(Long idEndereco) {
		this.id = idEndereco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Endereco [id=" + id + ", numero=" + numero + ", bairro=" + bairro + ", cep=" + cep + ", cliente=" + cliente + "]";
	}
	
	
	
	
	
}
