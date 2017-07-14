import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BenchmarkDisk implements Runnable {
	public BenchmarkDisk() throws InterruptedException{
		Thread[] isThread = new Thread[2];
		System.out.println("Disk for 1 thread\n");
		isThread[0] = new Thread(this);
		isThread[0].start();
		isThread[0].join();
		System.out.println("\nDisk for 2 threads\n");
		for(int i=0; i<2; i++){
			isThread[i] = new Thread(this);
			isThread[i].start();
		}
	}

	public void run(){
		try{
			int[] fileBytes = {1,1024,1024*1024};
			double start, end, total, throughput, latency;
			double nanoSecond = 1000000000;

			for(int fileSizes=0 ; fileSizes<fileBytes.length ; fileSizes++){
				FileInputStream sr = new FileInputStream(new File("data.txt"));
				FileOutputStream sw = new FileOutputStream("outputSeq"+fileBytes[fileSizes]);

				//Sequential
				//read
				byte[] buf = new byte[fileBytes[fileSizes]];
				start = System.nanoTime();
				sr.read(buf);
				end = System.nanoTime();
				//time
				total = end - start;
				throughput = fileBytes[fileSizes]/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("Sequential Throughput Read - "+ fileBytes[fileSizes] + " byte: "+throughput+" MB/s");
				latency = total/1000000;
				System.out.println("Sequential Latency Read - "+ fileBytes[fileSizes] + " byte: "+latency+" millisecond");
				
				//write
				start = System.nanoTime();
				sw.write(buf);
				end = System.nanoTime();
				//time
				total = end - start;
				throughput = fileBytes[fileSizes]/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("Sequential Throughput Write - "+ fileBytes[fileSizes] + " byte: "+throughput+" MB/s");
				latency = total/1000000;
				System.out.println("Sequential Latency Write - "+ fileBytes[fileSizes] + " byte: "+latency+" millisecond");
				sr.close();
				sw.close();
				
				//Random
				RandomAccessFile rr = new RandomAccessFile(new File("data.txt"), "r");
				RandomAccessFile rw = new RandomAccessFile(new File("outputRan"+fileBytes[fileSizes]), "rw");
				//read
				byte[] rbuf = new byte[fileBytes[fileSizes]];
				start = System.nanoTime();
				rr.seek(10);
				rr.read(rbuf, 0, rbuf.length);
				end = System.nanoTime();
				//time
				total = end - start;
				throughput = fileBytes[fileSizes]/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("Random Throughput Read - "+ fileBytes[fileSizes] + " byte: "+throughput+" MB/s");
				latency = total/1000000;
				System.out.println("Random Latency Read - "+ fileBytes[fileSizes] + " byte: "+latency+" millisecond");
				
				//write
				rw.write(rbuf, 0, rbuf.length);
				end = System.nanoTime();
				//time
				total = end - start;
				throughput = fileBytes[fileSizes]/(1024.0*1024.0)/(total/nanoSecond);
				System.out.println("Random Throughput Write - "+ fileBytes[fileSizes] + " byte: "+throughput+" MB/s");
				latency = total/1000000;
				System.out.println("Random Latency Write - "+ fileBytes[fileSizes] + " byte: "+latency+" millisecond");
				rr.close();
				rw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new BenchmarkDisk();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}