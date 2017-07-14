import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BenchmarkNetworkServerUDP implements Runnable {
	private DatagramSocket ds = null;
	public BenchmarkNetworkServerUDP() throws InterruptedException{
		try {
			ds = new DatagramSocket(5002);
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("UDP Server Started");
		System.out.println("Started to accept client connections...");
		Thread[] isThread = new Thread[2];
		for(int i=0; i<2; i++){
			isThread[i] = new Thread(this);
			isThread[i].start();
			Thread.sleep(1000);
		}
	}

	public void run(){

		try{
			int[] fileBytes = {1,1024,65507};
			for(int fileSizes=0 ; fileSizes<fileBytes.length ; fileSizes++){
				byte[] buf = new byte[fileBytes[fileSizes]];
				DatagramPacket receive = new DatagramPacket(buf, buf.length);
				ds.receive(receive);
				buf = receive.getData();
				InetAddress IPAddress = receive.getAddress();
				int port = receive.getPort();
				DatagramPacket send = new DatagramPacket(buf, buf.length, IPAddress, port);
				ds.send(send);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new BenchmarkNetworkServerUDP();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}