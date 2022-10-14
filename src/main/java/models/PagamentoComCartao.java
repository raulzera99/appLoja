package models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import models.enums.EstadoPagamento;

@Entity
@Table(name="table_pagamento_com_cartao")
@DiscriminatorValue("2")
public class PagamentoComCartao extends Pagamento{
	private static final long serialVersionUID = 1L;
	
	//Attributes
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
}
