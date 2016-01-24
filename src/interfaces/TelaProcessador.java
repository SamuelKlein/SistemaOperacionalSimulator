package interfaces;

import gerenciadores.acoes.DadosProcesso;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TelaProcessador extends JPanel {

	private static final long serialVersionUID = 3117574847340546234L;
	private HashMap<Integer,ElementoProcesso[]> prioridades = new HashMap<Integer,ElementoProcesso[]>();

	private JPanel processador = new JPanel(); 
	public TelaProcessador(){
		setBounds( 0, 300, 390, 230 );
		setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		setLayout( null );
		
		add( getTitulo() );
		
		add( criarLabel( "Execução", 120, 25 ) );
		add( criarLabel( "Pronto", 280, 25 ) );
		add( criarExecutacao() );
		
		add( getPrioridade() );
	}
	
	private Component criarExecutacao() {
		JPanel panel = new JPanel();
		panel.setBounds( 150, 45, 40, 40 );
		panel.setLayout( null );
		panel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		processador.setBounds( 2, 2, panel.getWidth() - 4, panel.getHeight() -4 );
		panel.add( processador );
		return panel;
	}

	private JLabel getTitulo() {
		JLabel label = new JLabel( "Gerenciador de Processador" );
		label.setBounds( 82, 0, 200, 20 );
		label.setHorizontalAlignment( JLabel.CENTER );
		
		return label;
	}
	

	private JLabel criarLabel( String name, int x, int y ){
		JLabel label = new JLabel( name );
		label.setBounds( x, y, 80, 20 );
		
		label.setHorizontalAlignment( JLabel.CENTER );
		return label;
	}
	
	private JPanel getPrioridade() {
		JPanel panel = new JPanel();
		panel.setLayout( null );
		panel.setBounds( 200, 45, 185, 180 );
		panel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		for ( int i = 0; i < 12; i++ ) {
			panel.add( getItemPrioridade( i ) );
		}
		
		return panel;
	}

	private JPanel getItemPrioridade( int prioridade ) {
		JPanel panel = new JPanel();
		panel.setLayout( null );
		panel.setBounds( 0, prioridade * 15, 205, 15 );
		panel.add( criarLabelPrioridade( prioridade + "", 0, 0, false ) );
		panel.add( criarGradePrioridade( prioridade ) );
		
		return panel;
	}
	
	
	
	private JLabel criarLabelPrioridade( String name, int x, int y, boolean linha ){
		JLabel lb = new JLabel( name );
		lb.setFont( new Font( Font.SERIF, Font.PLAIN, 10 ) );
		if( linha ){
			lb.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		}
		lb.setBounds( x, y, 20, 15 );
		lb.setHorizontalAlignment( JLabel.RIGHT );
		lb.setBackground( Color.BLACK );
		return lb;
	}
	
	private JPanel criarGradePrioridade( int prioridade ){
		JPanel panel = new JPanel();
		panel.setLayout( null );
		panel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		panel.setBounds( 30, 0, 150, 15 );

		ElementoProcesso[] elementos = new ElementoProcesso[ 10 ];
		for( int i = 0; i < 10; i++ ) {
			ElementoProcesso e = new ElementoProcesso( i );
			panel.add( e );
			elementos[ i ] = e;
		}
		prioridades.put( prioridade, elementos );		
		
		return panel;
	}
	
	public synchronized void enviaFilaPrioridade( Vector<DadosProcesso> vcDados, int prioridade ){
		ElementoProcesso[] elementos = prioridades.get( prioridade );
		
		if( vcDados.size() > 0 ){
			processador.setBackground( vcDados.get( 0 ).getCor() );
			processador.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );			
		} else {
			processador.setBackground( null );
			processador.setBorder( null );
		}
		
		for (int i = 0; i < vcDados.size(); i++) {
			elementos[ i ].setCor( vcDados.get( i ).getCor() );
		}
		for (int i = vcDados.size(); i < elementos.length; i++) {
			elementos[ i ].setRemoveCor();
		}
		
	}
	
	
	private class ElementoProcesso extends JPanel{

		private static final long serialVersionUID = 1752309523318723339L;
		private JPanel p = null; 
		private Color cor = null;
		
		public ElementoProcesso( int posicao ){
			setLayout( null );
			p = new JPanel();
			
			setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
			setBounds( posicao * 15, 0, 15, 15 );
			
			add( p );
		}
		
		public void setRemoveCor(){
			p.setBackground( null );
			p.setBorder( null );
		}
		
		public void setCor( Color cor ){
			p.setBackground( cor );
			p.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		}
		
		public void setBounds(int x, int y, int width, int height) {
			super.setBounds( x, y, width, height );
			p.setBounds( 2, 2, getWidth() - 4, getHeight() -4 );
		}
		
	}
	
	private void espera( int segundo ){
		try{
			Thread.sleep( segundo * 1000 );
		} catch (Exception e) {}
	}

}
