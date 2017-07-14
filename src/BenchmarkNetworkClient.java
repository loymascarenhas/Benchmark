import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class BenchmarkNetworkClient implements Runnable{
	private Socket clientSocket = null;
	private String ip = null;
	
	/**
	 * This {@link Constructor} reads input from the user for connecting to the server and starts the peer server required for peer download functionality.
	 * It also provides the client interface for register and search functionality.
	 */
	public BenchmarkNetworkClient(){
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter Server IP: ");
			ip = br.readLine();
			Thread[] isThread = new Thread[2];
			
			for(int i=0; i<2; i++){
				clientSocket = new Socket(ip,5001);	//connection to the index server
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
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				
				//TCP send
				byte[] buf = new byte[fileBytes[fileSizes]];
				fis.read(buf, 0, buf.length);
				start = System.nanoTime();
				out.write(buf);

				//TCP receive
				in.read(buf, 0, buf.length);
				end = System.nanoTime();
				
				//TCP time
				total = end - start;
				throughput = buf.length/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("TCP Throughput: "+throughput+" MB/s");
				latency = total/1000000;
				System.out.println("TCP Latency: "+latency+ " ms");
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
		new BenchmarkNetworkClient();
	}
}