package interfaces;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TelaConsole extends JPanel {

	private JLabel lbTempo;
	private JLabel lbProcesso;
	private JLabel lbMemoria;
	
	private long tempo = 0;
	private int processos = 0;
	private int memoria = 0;
	
	private static final long serialVersionUID = 3117574847340546234L;
	
	public TelaConsole( ActionListener oPrograma ){
		setBounds( 0, 2, 390, 80 );
		setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		setLayout( null );
		
		add( getTitulo() );
		
		add( criarLabel( "Tempo", 10, 20 ) );
		add( criarLabel( "Processo", 95, 20 ) );
		add( criarLabel( "Memoria", 180, 20 ) );
		add( criarBntStop( oPrograma ) );
		
		lbTempo = criarLabel( tempo + "", 10, 40 );
		lbProcesso = criarLabel( processos + "", 95, 40 );
		lbMemoria = criarLabel( memoria + "%", 180, 40 );
		
		
		add( lbTempo );
		add( lbProcesso );
		add( lbMemoria );
	}
	
	private JLabel getTitulo() {
		JLabel label = new JLabel( "Console" );
		label.setBounds( 150, 0, 80, 20 );
		label.setHorizontalAlignment( JLabel.CENTER );
		return label;
	}

	private JButton criarBntStop( ActionListener oPrograma ){
		JButton btn = new JButton( "Parar" );
		btn.setName( "ACAO_PARAR" );
		btn.setBounds( 280, 25, 80, 29 );
		btn.addActionListener( oPrograma );
		return btn;
	}
	
	private JLabel criarLabel( String name, int x, int y ){
		JLabel label = new JLabel( name );
		label.setBounds( x, y, 80, 20 );
		label.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		label.setHorizontalAlignment( JLabel.CENTER );
		return label;
	}
	
	public void setTempo( long tempo ) {
		this.tempo = tempo;
		lbTempo.setText( tempo + "" );
	}
	
	public void setProcessos( int processos ) {
		this.processos = processos;
		lbProcesso.setText( processos + "" );
	}
	
	public void setMemoria( int memoria ) {
		this.memoria = memoria;
		lbMemoria.setText( memoria + "%" );
	}

}
