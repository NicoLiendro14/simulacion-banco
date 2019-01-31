
public class BancoSinSync {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Banco b1=new Banco();
		for(int i=0;i<100;i++) {
			EjecucionTransferencias r= new EjecucionTransferencias(b1,i,2000);
			Thread t=new Thread(r);
			t.start();
		}
	}



}




















