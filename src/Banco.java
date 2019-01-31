import java.util.concurrent.locks.*;
class Banco{
	
	private final double[] cuentas;
	private Lock cierreBanco=new ReentrantLock();
	private Condition saldoSuficiente;
	
	public Banco() {
		cuentas=new double[100];
		
		for(int i=0;i<cuentas.length;i++) {
			cuentas[i]=2000;
		}
		saldoSuficiente=cierreBanco.newCondition();
	}
	public void transferencia(int cuentOrig, int cuentDes, double cant) throws InterruptedException {
		
		cierreBanco.lock();
		
		try {
			
			while(cuentas[cuentOrig]<cant) {
				saldoSuficiente.await(); //Se duerme si no cumple la condicion
			}
			System.out.println(Thread.currentThread());
			
			cuentas[cuentOrig]-=cant; //dinero que sale de la cuenta origen
			
			System.out.printf("%10.2f de %d para %d", cant, cuentOrig, cuentDes);
			
			cuentas[cuentDes]+=cant;
			
			System.out.printf("Saldo total: %10.2f%n", getSaldoTotal());
			
			saldoSuficiente.signalAll(); //Avisa que los hilos dormidos puedan usarlo
		}
		
		finally {
			cierreBanco.unlock();
		}
		
		
	}
	
	public double getSaldoTotal() {
		double total=0;
		for(int i=0; i<cuentas.length;i++) {
			total=total+cuentas[i];
		}
		
		return total;
	}
	
}