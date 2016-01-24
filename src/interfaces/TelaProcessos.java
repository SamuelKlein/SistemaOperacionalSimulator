package interfaces;

import gerenciadores.CtlProcesso;
import gerenciadores.acoes.DadosProcesso;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import util.ColorRenderer;


public class TelaProcessos extends JPanel {

	private static final long serialVersionUID = 5753410923048915059L;
	private JTable tableBrowse;
	private JScrollPane scroller = null;
	private CtlProcesso ctl;
	
	public TelaProcessos(){
		setBounds( 0, 90, 390, 200 );
		setLayout( null );
		setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		scroller = new JScrollPane( createTable() );
		scroller.setBounds( 7, 30, 320, 150 );
		scroller.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		tableBrowse.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		tableBrowse.setBounds( 0, 0, 20, 20 );
		tableBrowse.setLayout( null );
		
        tableBrowse.setPreferredScrollableViewportSize( new Dimension( 500, 70 ) );
        tableBrowse.setFillsViewportHeight( true );
        tableBrowse.setDefaultRenderer( Color.class, new ColorRenderer( true ) );
		add( scroller );
		add( getBotaoCriar() );
		add( getBotaoSuspender() );
		add( getBotaoProsseguir() );
		add( getBotaoFinalizar() );
		add( getPrioridade() );
	}
	
	private JButton getBotaoCriar() {
		JButton btn = getCriaBotao( "C", "Criar", 330, 30 );
		btn.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CriarProcesso c = new CriarProcesso( 400, 160 );
				c.setVisible( true );
			}
		});
		return btn;
	}

	private JButton getBotaoSuspender() {
		JButton btn = getCriaBotao( "S", "Suspender", 330, 55 );
		return btn;
	}

	private JButton getBotaoProsseguir() {
		JButton btn = getCriaBotao( "P", "Prosseguir", 330, 80 );
		return btn;
	}

	private JButton getBotaoFinalizar() {
		JButton btn = getCriaBotao( "F", "Finalizar", 330, 105 );
		return btn;
	}

	private JButton getPrioridade() {
		JButton btn = getCriaBotao( "P", "Prioridade", 330, 130 );
		return btn;
	}

	private JButton getCriaBotao( String name, String tip, int x, int y ) {
		JButton btn = new JButton( name );
		btn.setToolTipText( tip );
		btn.setBounds( x, y, 55, 20 );
		return btn;
	}
	
	private Component createTable() {

		String[] titulos = { "Cor", "PID", "Prio", "Estado", "TempoUCP", "Frame" };
	
//		tableBrowse = new JTable( new MyTableModel() );
		
		tableBrowse = new JTable( new DefaultTableModel( new Object[][]{}, titulos )
			{
				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {
					return false;
				}
				
			}
		);

		int [] tamanhos = { 20, 50, 25, 70, 50, 40 };
		
		TableColumnModel columnModel = tableBrowse.getColumnModel();
		TableColumn column = null;

		for (int i = 0; i < columnModel.getColumnCount(); i++) {
		    column = tableBrowse.getColumnModel().getColumn(i);
		    column.setCellRenderer( new ComboTableCellRenderer() );
		    if( i == 1 ){
		    	column.setPreferredWidth( tamanhos[i] );
		    } else {
		    	column.setMaxWidth( tamanhos[i] );
		    }
		    
		}		
		
		JTableHeader jth = tableBrowse.getTableHeader();
 		Color corCabecalho = new Color( 165, 183, 160 );
		jth.setForeground( Color.BLACK );
		jth.setBackground( corCabecalho );			
		
		// Listener de duplo clique em uma linha
		tableBrowse.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

//				if ( e.getClickCount() >= 2 ) {
//					
//					int linha = tableBrowse.getSelectedRow();
//					String chave = chaves[ linha ];
//					
//					if( chave != null ) {
//						btnConfirmaNome.doClick();
//					}
//				}
			}
		});
		
		tableBrowse.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int row = tableBrowse.getSelectedRow();
				if ( row >= 0 ) {
				}
			}
		});
		
		return tableBrowse;
	}

	public void addProcesso( DadosProcesso dados ) {
		DefaultTableModel modelo = (DefaultTableModel) tableBrowse.getModel();
		Object[] valores = getArrayDados( dados );
		modelo.addRow( valores );
	}
	
	public void atualizarProcesso( DadosProcesso dados ){
		DefaultTableModel modelo = (DefaultTableModel) tableBrowse.getModel();
		
		for (int i = 0; i < modelo.getRowCount(); i++) {
			if( modelo.getValueAt( i, 1 ).equals( dados.getPid() + "" ) ){
				modelo.setValueAt( dados.getDescricaoEstado(), i, 3 );
				modelo.setValueAt( dados.getTempoUCP(), i, 4 );
			}
		}
		
		
	}
	
	public void removeProcesso( DadosProcesso dados ){
		DefaultTableModel modelo = (DefaultTableModel) tableBrowse.getModel();
		
		for (int i = 0; i < modelo.getRowCount(); i++) {
			if( modelo.getValueAt( i, 1 ).equals( dados.getPid() + "" ) ){
				modelo.removeRow( i );	
			}
		}
		
		
	}
	
	private class CriarProcesso extends JDialog{
		private static final long serialVersionUID = 1L;

		private JComboBox campoPrioridade;
		private JTextField campoFrame;
		private JComboBox campoProcesso;
		
		
		public CriarProcesso( int x, int y ){
			setBounds( x, y, 230, 200 );
			setLayout( null );
			
			add( criaLabel( "Prioridade", 10, 0 ) );
			campoPrioridade = new JComboBox();
			campoPrioridade.setBounds( 10, 20, 100, 20 );
			for (int i = 0; i < 12; i++) {
				campoPrioridade.addItem( "" + i );	
			}
			campoPrioridade.setSelectedIndex( 0 );
			add( campoPrioridade );
			
			add( criaLabel( "Frame", 160, 0 ) );
			campoFrame = new JTextField( "5" );
			campoFrame.setBounds( 160, 20, 50, 20 );
			add( campoFrame );
			
			add( criaLabel( "Tipo de Processo", 10, 60 ) );
			campoProcesso = new JComboBox();
			campoProcesso.setBounds( 10, 80, 200, 20 );
			campoProcesso.addItem( "CPU bound" );
			campoProcesso.addItem( "I/O bound disco" );
			campoProcesso.addItem( "I/O bound fila" );
			campoProcesso.addItem( "I/O bound teminal" );
			campoProcesso.addItem( "CPU e I/O bound disco" );
			campoProcesso.addItem( "CPU e I/O bound fila" );
			campoProcesso.addItem( "CPU e I/O bound teminal" );
			campoProcesso.setSelectedIndex( 0 );
			add( campoProcesso );
			
			JButton btn = getCriaBotao( "Criar", "", 80, 150 );
			btn.setBounds( 60, 150, 100, 20 );
			btn.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					ctl.criarProcesso( getPrioridade(), getTpProcesso(), getFrame(), getCor() );
					dispose();
				}
			});
			add( btn );
		}
		
		private JLabel criaLabel( String name, int x, int y ){
			JLabel label = new JLabel( name );
			label.setBounds( x, y, 150, 20 );
			return label;
		}
		
		public int getPrioridade() {
			return campoPrioridade.getSelectedIndex();
		}
		
		public int getFrame() {
			return Integer.parseInt( campoFrame.getText() );
		}
		
		public int getTpProcesso() {
			return campoProcesso.getSelectedIndex();
		}
		
		public Color getCor(){
			return new Color( new Float( Math.random() * 255 ).intValue(), new Float( Math.random() * 255 ).intValue(), new Float( Math.random() * 255 ).intValue() );
		}
	}

	public void setControle(CtlProcesso ctl ) {
		this.ctl = ctl; 
	}
	
	public Object[] getArrayDados( DadosProcesso dados ){
		Object[] s = { "", "PID", "Prio", "Estado", "TempoUCP", "Frame" };
		s[ 0 ] = dados.getCor(); 
		s[ 1 ] = "" + dados.getPid();
		s[ 2 ] = "" + dados.getPrioridade();
		s[ 3 ] = "" + dados.getDescricaoEstado();
		s[ 4 ] = "" + dados.getTempoUCP();
		s[ 5 ] = "" + dados.getFrame();
		return s;
	}
	
	private class ComboTableCellRenderer implements ListCellRenderer, TableCellRenderer {
		  DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();

		  DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();

		  private void configureRenderer(JLabel renderer, Object value) {
		    if ((value != null) && (value instanceof Color)) {
		      renderer.setIcon(new DiamondIcon((Color) value));
		      renderer.setText("");
		    } else {
		      renderer.setIcon(null);
		      renderer.setText( "" + value );
		      renderer.setToolTipText( "" + value );
		    }
		  }

		  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    listRenderer = (DefaultListCellRenderer) listRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    configureRenderer( listRenderer, value );
		    return listRenderer;
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    tableRenderer = (DefaultTableCellRenderer) tableRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		    configureRenderer( tableRenderer, value );
		    return tableRenderer;
		  }
	}
 
	class DiamondIcon implements Icon {
		  private Color color;

		  private boolean selected;

		  private int width;

		  private int height;

		  private Polygon poly;

		  private static final int DEFAULT_WIDTH = 10;

		  private static final int DEFAULT_HEIGHT = 10;

		  public DiamondIcon(Color color) {
		    this(color, true, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		  }

		  public DiamondIcon(Color color, boolean selected) {
		    this(color, selected, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		  }

		  public DiamondIcon(Color color, boolean selected, int width, int height) {
		    this.color = color;
		    this.selected = selected;
		    this.width = width;
		    this.height = height;
		    initPolygon();
		  }

		  private void initPolygon() {
		    poly = new Polygon();
		    int halfWidth = width / 2;
		    int halfHeight = height / 2;
		    poly.addPoint(0, halfHeight);
		    poly.addPoint(halfWidth, 0);
		    poly.addPoint(width, halfHeight);
		    poly.addPoint(halfWidth, height);
		  }

		  public int getIconHeight() {
		    return height;
		  }

		  public int getIconWidth() {
		    return width;
		  }

		  public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(color);
		    g.translate(x, y);
		    if (selected) {
		      g.fillPolygon(poly);
		    } else {
		      g.drawPolygon(poly);
		    }
		    g.translate(-x, -y);
		  }
	}

}
