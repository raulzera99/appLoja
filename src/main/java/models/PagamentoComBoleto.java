package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jakarta.validation.constraints.NotNull;
import models.enums.EstadoPagamento;
import services.errors.CampoRequerido;

@Entity
@DiscriminatorValue("1")
public class PagamentoComBoleto extends Pagamento{
	private static final long serialVersionUID = 1L;

	//Attributes
	@CampoRequerido(valor = 2, mensagem = "A data de vencimento deve ser informada")
	@NotNull(message = "A data de vencimento deve ser informada")	
	@Column(name = "data_vencimento")
	private String dataVencimento;
	
	@CampoRequerido(valor = 3, mensagem = "A data de pagamento deve ser informada")
	@NotNull(message = "A data de pagamento deve ser informada")	
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
