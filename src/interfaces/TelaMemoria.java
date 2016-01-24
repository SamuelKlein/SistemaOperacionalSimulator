package interfaces;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaMemoria extends JPanel{

	private Vector<JPanel> vp;
	private static final long serialVersionUID = -8071728304936378078L;
	
	public TelaMemoria(){
		setLayout( null );
		setBounds( 400, 2, 300, 400 );
		setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		add( getTitulo() );
		add( getGradeMemoria() );
	}
	
	private JLabel getTitulo() {
		JLabel label = new JLabel( "Gerenciador de Memoria" );
		label.setBounds( 50, 0, 200, 20 );
		label.setHorizontalAlignment( JLabel.CENTER );
		return label;
	}
	
	private JPanel getGradeMemoria() {
		vp = new Vector<JPanel>();
		GridLayout layout = new GridLayout( 10, 10 );
		JPanel pnl = new JPanel();
		pnl.setLayout( layout );
		pnl.setBackground( Color.WHITE );
		pnl.setBounds( 0, 30, 300, 300 );
		
		for (int i = 0; i < 100; i++) {
			JPanel label = getItemGrade( i );
			vp.add( label );
			pnl.add( label );
		}
		
		return pnl;
	}
	
	private JPanel getItemGrade( int numero ){
		JPanel panel = new JPanel();
		
		panel.setBorder( BorderFactory.createLineBorder( Color.BLACK ) );
		
		JLabel label = new JLabel( ( numero + 1 ) + "" );
		label.setHorizontalAlignment( JLabel.CENTER );
		
		panel.setBackground( Color.WHITE );
		panel.add( label );
		return panel;
	}
	
	public void getCor( int memoria, Color color ){
		vp.get( memoria ).setBackground( color ); 
	}
}
