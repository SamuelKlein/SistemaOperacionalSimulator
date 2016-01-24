package gerenciadores;

import gerenciadores.acoes.AcaoDeSistema;
import gerenciadores.acoes.DadosProcesso;

import java.util.Vector;

public class CtlSistema extends Thread {
	
	private static long tempo = 0;
	private boolean finaliza = false;
	private Vector<Processo> vp = new Vector<Processo>();
	
	private CtlProcessador processador = null;
	private CtlMemoria memoria = null;
	private AcaoDeSistema interfaceSistema = null;
	
	private int novoPid = 0;
	
	public CtlSistema( AcaoDeSistema interfaceSistema ){
		this.interfaceSistema = interfaceSistema;
		processador = new CtlProcessador( interfaceSistema );
		memoria = new CtlMemoria( interfaceSistema );
		start();
	}
	
	public void adicionalProcesso( Processo processo ){
		novoPid ++;
		processo.setPid( novoPid );
		vp.add( processo );
		processador.envia( processo );
		interfaceSistema.processos( vp.size() );
		interfaceSistema.addProcesso( processo );
	}
	
	public void run() {
		while( !finaliza ){
			relogio();
		}
	}
	
	public void interrupt() {
		finaliza = true;
		processador.interrupt();
//		super.interrupt();
	}
	
	private void relogio(){
		try {
			while( interfaceSistema == null ) {
				Thread.sleep( 1000 );
			}
			atualizaProcesso();
			interfaceSistema.tempo( tempo++ );
			interfaceSistema.memoria( memoria.livre() );
			Thread.sleep( 1000 );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void atualizaProcesso() {
		try{
			for (int i = 0; i < vp.size(); i++) {
				DadosProcesso dados = vp.get( i );
				interfaceSistema.atualizaProcesso( dados );	
				if( dados.getEstado() == Processo.ESTADO_ENCERRADO ){
					CtlMemoria.desalocaMemoria( dados.getPid() );
					vp.remove( i );
					interfaceSistema.processos( vp.size() );
					interfaceSistema.removeProcesso( dados );
				}
				
			}
		} catch (Exception e) {}
	}
	
}