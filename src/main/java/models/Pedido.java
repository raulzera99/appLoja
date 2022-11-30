package models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jakarta.validation.constraints.NotNull;
import services.errors.CampoRequerido;


@Entity
@Table(name="table_pedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	// Attributes
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@NotNull(message = "O instante do pedido deve ser informado")	
	@Temporal(TemporalType.TIME)
	@Column(name = "instante")
	private Date instante;
	
	@NotNull(message = "A data do pedido deve ser informada")	
	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data;
	
	@CampoRequerido(valor = 1, mensagem = "A descrição do pedido deve ser informada")
	@Column(name = "descricao")
	private String descricao;
	
	@OneToMany(mappedBy = "pedido")
	private Set<Pagamento> pagamentos = new HashSet<Pagamento>();
	
	@ManyToOne
	@JoinColumn(name="id_cliente", referencedColumnName = "id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_endereco", referencedColumnName = "id")
	private Endereco enderecoDeEntrega;
	
	@OneToMany(mappedBy="pedido")
	private Set<ItemPedido> itens = new HashSet<ItemPedido>();
	
	// Constructors
	
	public Pedido(Long id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	public Pedido() {
	}

	public Pedido(Long id, Date instante) {
		super();
		this.id = id;
		this.instante = instante;
	}

	// Methods

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}
	
	public Set<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(Set<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instante == null) ? 0 : instante.hashCode());
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instante == null) {
			if (other.instante != null)
				return false;
		} else if (!instante.equals(other.instante))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\nPedido [id=" + id + ", \ninstante=" + instante + ", \ndata=" + data + ", \ndescricao=" + descricao
				+ ", \ncliente=" + cliente + ", \nenderecoDeEntrega=" + enderecoDeEntrega
				+ "]";
	}

	

}
