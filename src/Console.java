import gerenciadores.CtlProcesso;
import gerenciadores.acoes.AcaoDeSistema;
import gerenciadores.acoes.DadosProcesso;
import interfaces.TelaConsole;
import interfaces.TelaMemoria;
import interfaces.TelaProcessador;
import interfaces.TelaProcessos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Console extends JFrame implements WindowListener, AcaoDeSistema, ActionListener {
	
	private static final long serialVersionUID = 484071848082228539L;
	
	private TelaConsole col = null;
	private TelaMemoria men = null;
	private TelaProcessador processador = null;
	private TelaProcessos processos = null;
	private CtlProcesso ctl = null;
	
	
	public Console(){
		addWindowListener( this );
		setBounds( 150, 0, 800, 560 );
		setTitle("Gerenciador de Escalonamendo" );
		setResizable( false );
		setLayout( null );
		
		Container container = getContentPane();
		
		container.add( getPainelPrincipal() );
		container.add( getGerenciadorProcesso() );
		container.add( getGerenciadorMemoria() );
		container.add( getGerenciadorProcessador() );
		
		ctl = new CtlProcesso( this );
		processos.setControle( ctl );
		setVisible( true );
	}
	
	private Component getPainelPrincipal() {
		col = new TelaConsole( this );
		return col;
	}
	
	private Component getGerenciadorProcesso() {
		processos = new TelaProcessos();
		return processos;
	}
	
	private Component getGerenciadorMemoria() {
		men = new TelaMemoria();
		return men;
	}
	
	private Component getGerenciadorProcessador() {
		processador = new TelaProcessador();
		return processador;
	}
	
	public void addProcesso( DadosProcesso dados ) {
		processos.addProcesso( dados );
	}
	
	public void criarProcesso( int prioridade, int tipoProcesso, int frame, Color cor ){
		ctl.criarProcesso( prioridade, tipoProcesso, frame, cor );
	}

	public void atualizaProcesso( DadosProcesso dados ) {
		processos.atualizarProcesso( dados );
	}
	
	public void removeProcesso(DadosProcesso dados) {
		processos.removeProcesso( dados );
	}
	
	public void memoria( int memoria ) {
		col.setMemoria( memoria );
	}

	public void processos( int processo ) {
		col.setProcessos( processo );
	}

	public void tempo(long tempo) {
		col.setTempo( tempo );
	}

	public void addMemoria(int pos, Color cor) {
		men.getCor( pos, cor );
	}

	public void removeMemoria(int pos) {
		men.getCor( pos, Color.WHITE );
	}
	
	public void enviaFila( Vector<DadosProcesso> vcDados, int prioridade ) {
		
		processador.enviaFilaPrioridade( vcDados, prioridade );
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {
//		ctl.fechar();dispose();
		ctl = null;
	}

	public void windowClosing(WindowEvent e) {
		dispose();
		ctl.fechar();
		ctl = null;
	}
	
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	public static void main(String[] args) {
		new Console();
	}

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if( o instanceof JButton ){
			JButton bnt = (JButton) o;
			String acao = bnt.getName();
			if( acao.equals( "ACAO_PARAR" ) ){
				ctl.fechar();
				dispose();
			}
		}
	}

}