package gerenciadores;

import gerenciadores.acoes.AcaoDeSistema;

import java.awt.Color;
import java.util.HashMap;

public class CtlMemoria {
	
	private static CtlMemoria mySelf;
	
	private AcaoDeSistema interfaceSistema = null; 
	
	private final static int TAM_MEMORIA = 100;
	
	private int tamMemoriaEmUso = 0;
	private int[] memoria = null;
	private HashMap<Integer,Processo> vc = new HashMap<Integer,Processo>();
	
	public CtlMemoria( AcaoDeSistema interfaceSistema ){
		mySelf = this;
		this.interfaceSistema = interfaceSistema;
		memoria = new int[ TAM_MEMORIA ];
		for (int i = 0; i < memoria.length; i++) {
			memoria[ i ] = -1;
		}
	}

	public static void envia( Processo processo ) throws Exception {
		if( mySelf != null ){
			mySelf.vc.put( processo.getPid(), processo );
			mySelf.gravaMemoria( processo.getPid(), processo.getFrame() );
		}
	}

	public static void desalocaMemoria(int pid ){
		if( mySelf != null ){
			for (int i = 0; i < mySelf.memoria.length; i++) {
				if( mySelf.memoria[ i ] == pid ){
					mySelf.remoreMemoria( i ); 
				}
			}
		}
	}
	
	public void alteraFrames( int pid, int frame ){
		Processo p = vc.get( pid );
		frame -= p.getFrame();
		if( frame != 0 ){
			for (int i = 0; i < memoria.length; i++) {
				if( frame == 0 ){
					break;
				}
				if( memoria[ i ] == -1 ){
					if( frame > 0 ){
						addMemoria( i, pid );
						frame--;
					} else {
						remoreMemoria( i );
						frame++;
					}
				}
			}
		}
	}
	
	private void gravaMemoria( int pid, int frame ) throws Exception {
		int x = 0;

		for ( int i = 0; i < memoria.length; i++ ) {
			if( memoria[ i ] == pid && frame > x ){
				x++;
			} else if( memoria[ i ] == -1 && frame > x ){
				addMemoria( i, pid );
				x++;
			} else if( frame == x ){
				break;
			}
		}
		
		for ( int i = 0; i < memoria.length; i++ ) {
			
			if( vc.containsKey( memoria[ i ] ) ) { 
				if( vc.get( memoria[ i ] ).getEstado() != Processo.ESTADO_EXECUTANDO && frame > x ){
					remoreMemoria( i );
					addMemoria( i, pid );
					x++;
				}
			} else if( frame == x ){
				break;
			}
		}
		
		
		
		if( x != frame ){
			throw new Exception( "Estouro de Memoria" );
		}
	}

	
	
	private void addMemoria( int posicao, int pid ){
		memoria[ posicao ] = pid;
		interfaceSistema.addMemoria( posicao, getCor( pid ) );
		tamMemoriaEmUso++;
	}
	
	private Color getCor( int pid ){
		if( vc.containsKey( pid ) ){
			return vc.get( pid ).getCor();
		}
		
		return Color.WHITE;
	}
	private void remoreMemoria( int posicao ){
		interfaceSistema.removeMemoria( posicao );
		memoria[ posicao ] = -1;
		tamMemoriaEmUso--;
	}
	
	public int emUso(){
		return tamMemoriaEmUso;
	}
	
	public int livre(){
		return TAM_MEMORIA - tamMemoriaEmUso;
	}
}
