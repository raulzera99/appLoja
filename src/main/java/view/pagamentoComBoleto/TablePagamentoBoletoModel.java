package view.pagamentoComBoleto;

import java.util.ArrayList;
import java.util.List;

import models.PagamentoComBoleto;
import models.enums.EstadoPagamento;
import view.table.JTableModel;

public class TablePagamentoBoletoModel extends JTableModel<PagamentoComBoleto>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<PagamentoComBoleto> tabela;
	
	private final String colunaPagamentoBoleto[] = {"Código","Estado do pagamento", "Data de vencimento", "Data de pagamento"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TablePagamentoBoletoModel() {
		tabela = new ArrayList<PagamentoComBoleto>();
		this.coluna = colunaPagamentoBoleto;
	}
	
	public TablePagamentoBoletoModel(List<PagamentoComBoleto> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaPagamentoBoleto;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		PagamentoComBoleto pagamentoBoleto = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return pagamentoBoleto.getId();
			}
			case 1:{
				return EstadoPagamento.toEnum(pagamentoBoleto.getEstado()).getDescricao();
			}
			case 2:{
				return pagamentoBoleto.getDataVencimento();
			}
			case 3:{
				return pagamentoBoleto.getDataPagamento();
			}
			
			default:
				return null;
			
		}
		
	}
	
	@Override
	public Class<?> getColumnClass(int coluna){
		switch (coluna) {
			case 0:{
				return Long.class;
			}
			case 1:{
				return EstadoPagamento.class;
			}
			case 2:{
				return String.class;
			}
			case 3:{
				return String.class;
			}
			

			default:
				return null;
		}
	}
	
	public PagamentoComBoleto getPagamentoBoleto(int index) {
		return getTabela().get(index);
	}
	
	public void salvarPagamentoBoleto(PagamentoComBoleto pagamentoBoleto) {
		getTabela().add(pagamentoBoleto);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarPagamentoBoleto(PagamentoComBoleto pagamentoBoleto, int index) {
		getTabela().set(index, pagamentoBoleto);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerPagamentoBoleto(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<PagamentoComBoleto> getTabela() {
		return tabela;
	}

	public void setTabela(List<PagamentoComBoleto> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
