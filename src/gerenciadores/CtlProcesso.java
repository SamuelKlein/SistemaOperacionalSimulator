package gerenciadores;

import gerenciadores.acoes.AcaoDeSistema;
import gerenciadores.acoes.DadosProcesso;

import java.awt.Color;
import java.util.HashMap;



public class CtlProcesso {

	private CtlSistema sistema = null;
	private HashMap<Integer,DadosProcesso> hash = new HashMap<Integer,DadosProcesso>();
	
	public CtlProcesso( AcaoDeSistema interfaceSistema ){
		sistema = new CtlSistema( interfaceSistema );
	}
	
	public void criarProcesso( int prioridade, int tipoProcesso, int frame, Color cor ){
		try{
		Processo processo =  new Processo( prioridade, tipoProcesso, frame, cor );
		sistema.adicionalProcesso( processo );
		hash.put( processo.getPid(), processo );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void suspenderProcesso( int pid ){
		hash.get( pid ).suspender();
	}
	
	public void prosseguirProcesso( int pid ){
		hash.get( pid ).prosseguir();
	}
	
	public void prioridadeProcesso( int pid, int prioridade ){
		hash.get( pid ).setPrioridade( prioridade );
	}
	
	public void finalizarProcesso( int pid ){
		hash.get( pid ).finalizar();
	}
	
	public DadosProcesso getProcesso( int pid ){
		return hash.get( pid );
	}
	
	public void fechar(){
		sistema.interrupt();
	}
}
