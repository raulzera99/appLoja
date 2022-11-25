package view.itemPedido;

import java.util.ArrayList;
import java.util.List;

import models.ItemPedido;
import view.table.JTableModel;

public class TableItemPedidoModel extends JTableModel<ItemPedido>{
	private static final long serialVersionUID = 2259690966041330349L;
	private List<ItemPedido> tabela;
	
	private final String colunaItemPedido[] = {"Código","Desconto", "Quantidade", "Preço total", "Instante do pedido","Nome do produto"};
	
	private final Integer tamanhoCampo[] = {};
	
	
	public TableItemPedidoModel() {
		tabela = new ArrayList<ItemPedido>();
		this.coluna = colunaItemPedido;
	}
	
	public TableItemPedidoModel(List<ItemPedido> tabela) {
		super(tabela);
		this.tabela = tabela;
		this.coluna = colunaItemPedido;
	}
	
	@Override
	public Object getValueAt(int linha, int coluna) {
		ItemPedido itemPedido = tabela.get(linha);
		switch(coluna) {
			case 0:{
				return itemPedido.getId();
			}
			case 1:{
				return itemPedido.getDesconto();
			}
			case 2:{
				return itemPedido.getQuantidade();
			}
			case 3:{
				return itemPedido.getPreco();
			}
			case 4:{
				return itemPedido.getPedido().getInstante();
			}
			case 5:{
				return itemPedido.getProduto().getNome();
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
				return Double.class;
			}
			case 2:{
				return Integer.class;
			}
			case 3:{
				return Double.class;
			}
			case 4:{
				return String.class;
			}
			case 5:{
				return String.class;
			}
			default:
				return null;
		}
	}
	
	public ItemPedido getItemPedido(int index) {
		return getTabela().get(index);
	}
	
	public void salvarItemPedido(ItemPedido itemPedido) {
		getTabela().add(itemPedido);
		fireTableRowsInserted(getRowCount()-1, getColumnCount()-1);
	}
	
	public void alterarItemPedido(ItemPedido itemPedido, int index) {
		getTabela().set(index, itemPedido);
		fireTableRowsUpdated(index, index);
	}
	
	public void removerItemPedido(int index) {
		getTabela().remove(index);
		fireTableRowsDeleted(index, index);
	}
	
	public void removeAll() {
		getTabela().clear();
		fireTableDataChanged();
	}

	public List<ItemPedido> getTabela() {
		return tabela;
	}

	public void setTabela(List<ItemPedido> tabela) {
		this.tabela = tabela;
	}

	public String[] getColuna() {
		return coluna;
	}

	public Integer[] getTamanhoCampo() {
		return tamanhoCampo;
	}
	
	
	
}
