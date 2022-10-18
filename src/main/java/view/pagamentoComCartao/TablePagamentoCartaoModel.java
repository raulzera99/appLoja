package view.pagamentoComCartao;

import java.util.ArrayList;
import java.util.List;

import models.PagamentoComCartao;
import models.enums.EstadoPagamento;
import view.table.JTableModel;

public class TablePagamentoCartaoModel extends JTableModel<PagamentoComCartao>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<PagamentoComCartao> tabela;
	
	private final String colunaPagamentoCartao[] = {"Código","Estado do pagamento", "Número de parcelas"};
	
	private final Integer tamanhoCampo[] = {};
	
	public TablePagamentoCartaoModel() {
		tabela = new ArrayList<PagamentoComCartao>();
		this.coluna = colunaPagamentoCartao;
	}
	
	public TablePagamentoCartaoModel(List<PagamentoComCartao> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaPagamentoCartao;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		PagamentoComCartao pagamentoCartao = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return pagamentoCartao.getId();
			}
			case 1:{
				return EstadoPagamento.toEnum(pagamentoCartao.getEstado()).getDescricao();
			}
			case 2:{
				return pagamentoCartao.getNumeroDeParcelas();
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
				return String.class;
			}
			case 2:{
				return Integer.class;
			}
			default:
				return null;
		}
	}
	
	public PagamentoComCartao getPagamentoCartao(int index) {
		return getTabela().get(index);
	}
	
	public void salvarPagamentoCartao(PagamentoComCartao pagamentoCartao) {
		getTabela().add(pagamentoCartao);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarPagamentoCartao(PagamentoComCartao pagamentoCartao, int index) {
		getTabela().set(index, pagamentoCartao);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerPagamentoCartao(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<PagamentoComCartao> getTabela() {
		return tabela;
	}

	public void setTabela(List<PagamentoComCartao> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
}
