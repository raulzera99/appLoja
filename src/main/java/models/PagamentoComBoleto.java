package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.enums.EstadoPagamento;

@Entity
@Table(name = "table_pagamento_com_boleto")
@DiscriminatorValue("1")
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;

	//Attributes
	
	@Column(name = "data_vencimento")
	private String dataVencimento;
	
	@Column(name = "data_pagamento")
	private String dataPagamento;	
	
	//Constructors
	public PagamentoComBoleto() {}
	
	public PagamentoComBoleto(Long id, EstadoPagamento estado, Pedido pedido, String dataVencimento, String dataPagamento) {
		super(id, estado, pedido);
		this.dataPagamento = dataPagamento;
		this.dataVencimento = dataVencimento;
	}
	
	//Methods
	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	@Override
	public String toString() {
		return "\nPagamentoComBoleto\n[dataVencimento = " + dataVencimento + "\ndataPagamento=" + dataPagamento
				+ "\ngetId()=" + getId() + "\ngetEstado()=" + EstadoPagamento.toEnum(getEstado()).getDescricao() + "]";
	}	
	
	
}
