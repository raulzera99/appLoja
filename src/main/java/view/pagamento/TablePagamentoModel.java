package view.pagamento;

import java.util.ArrayList;
import java.util.List;

import models.Pagamento;
import models.enums.EstadoPagamento;
import view.table.JTableModel;

public class TablePagamentoModel extends JTableModel<Pagamento>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Pagamento> tabela;
	
	private final String colunaPagamento[] = {"Código","Estado do pagamento", "Instante do pedido"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TablePagamentoModel() {
		tabela = new ArrayList<Pagamento>();
		this.coluna = colunaPagamento;
	}
	
	public TablePagamentoModel(List<Pagamento> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaPagamento;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Pagamento pagamento = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return pagamento.getId();
			}
			case 1:{
				return EstadoPagamento.toEnum(pagamento.getEstado()).getDescricao();
			}
			case 2:{
				return pagamento.getPedido().getInstante();
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
				return String.class;
			}
			default:
				return null;
		}
	}
	
	public Pagamento getPagamento(int index) {
		return getTabela().get(index);
	}
	
	public void salvarPagamento(Pagamento pagamento) {
		getTabela().add(pagamento);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarPagamento(Pagamento pagamento, int index) {
		getTabela().set(index, pagamento);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerPagamento(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Pagamento> getTabela() {
		return tabela;
	}

	public void setTabela(List<Pagamento> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
