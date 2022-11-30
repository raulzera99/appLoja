package view.pedido;

import java.util.ArrayList;
import java.util.List;

import models.Pedido;
import view.table.JTableModel;

public class TablePedidoModel extends JTableModel<Pedido>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<Pedido> tabela;
	
	private final String colunaPedido[] = {"Código", "Descrição", "Data", "Instante"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TablePedidoModel() {
		tabela = new ArrayList<Pedido>();
		this.coluna = colunaPedido;
	}
	
	public TablePedidoModel(List<Pedido> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaPedido;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		Pedido pedido = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return pedido.getId();
			}
			case 1:{
				return pedido.getDescricao();
			}
			case 2:{
				return pedido.getData().toString();
			}
			case 3:{
				return pedido.getInstante().toString();
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
			case 3:{
				return String.class;
			}
			
			default:
				return null;
		}
	}
	
	public Pedido getPedido(int index) {
		return getTabela().get(index);
	}
	
	public void salvarPedido(Pedido pedido) {
		getTabela().add(pedido);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarPedido(Pedido pedido, int index) {
		getTabela().set(index, pedido);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerPedido(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<Pedido> getTabela() {
		return tabela;
	}

	public void setTabela(List<Pedido> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
