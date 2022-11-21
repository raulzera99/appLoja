package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jakarta.validation.constraints.NotNull;
import models.enums.EstadoPagamento;
import services.errors.CampoRequerido;

@Entity
@DiscriminatorValue("2")
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	//Attributes
	@CampoRequerido(valor = 2, mensagem = "O número de parcelas deve ser informado")
	@NotNull(message = "O número de parcelas deve ser informado")	
	@Column(name = "numeroDeParcelas")
	private Integer numeroDeParcelas;
	
	//Constructors
	public PagamentoComCartao() {
	}

	public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	//Methods
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}

	@Override
	public String toString() {
		return "\nPagamentoComCartao \n[numeroDeParcelas = " + numeroDeParcelas + "\ngetId() = " + getId() + "\ngetEstado() = "
				+ EstadoPagamento.toEnum(getEstado()).getDescricao() + "]";
	}
	
}
