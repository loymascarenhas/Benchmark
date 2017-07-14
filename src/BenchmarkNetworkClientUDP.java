import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BenchmarkNetworkClientUDP implements Runnable{
	private String ip = null;
	private DatagramSocket ds = null;
	
	/**
	 * This {@link Constructor} reads input from the user for connecting to the server and starts the peer server required for peer download functionality.
	 * It also provides the client interface for register and search functionality.
	 */
	public BenchmarkNetworkClientUDP(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Server IP: ");
			ip = br.readLine();
			ds = new DatagramSocket();
			Thread[] isThread = new Thread[2];
			
			for(int i=0; i<2; i++){
				isThread[i] = new Thread(this);
				isThread[i].start();
				Thread.sleep(1000);
			}
		} catch (NumberFormatException | IOException | InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int[] fileBytes = {1,1024,65507};
			double start, end, total, throughput, latency;
			double nanoSecond = 1000000000;
			
			for(int fileSizes=0 ; fileSizes<fileBytes.length ; fileSizes++){
				FileInputStream fis = new FileInputStream(new File("data.txt"));
				
				//TCP send
				byte[] buf = new byte[fileBytes[fileSizes]];
				fis.read(buf, 0, buf.length);
				
				start = System.nanoTime();
				
				//UDP send
				DatagramPacket send = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip),5002);
				ds.send(send);
				
				//UDP receive
				DatagramPacket receive = new DatagramPacket(buf, buf.length);
				ds.receive(receive);
				end = System.nanoTime();
				
				//time
				total = end - start;
				throughput = buf.length/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("UDP Throughput: "+throughput);
				latency = total/1000000;
				System.out.println("UDP Latency: "+latency);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a main class to start the peer program.
	 * @param args
	 */
	public static void main(String[] args) {
		new BenchmarkNetworkClientUDP();
	}
}