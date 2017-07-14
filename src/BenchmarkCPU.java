class BenchmarkCPUThread extends Thread{
	int ops = 1000000000; //10^9
	float diffIOPS = 0;
	float diffFLOPS = 0;
	long countOperations = 0;

	public void run() {
		//IOPS operation
		int i=0;
		int icpu1=10, icpu2=20;
		float fcpu1=10.5f, fcpu2=20.5f;
		long start, end;
		
		//integer point operations
		start = System.currentTimeMillis();
		i=0;
		while(i < ops){
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;//10
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1+icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1*icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;//20
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;//30
			icpu2 = icpu1*icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;
			icpu1 = icpu1-icpu2;
			icpu1 = icpu1+icpu2;
			icpu2 = icpu1-icpu2;//40
			countOperations = 80;
			i++;
		}
		end = System.currentTimeMillis();
		diffIOPS = (end - start)/1000f;
		i=0;
		//countOperations = 0;
		//floating point operations
		start = System.currentTimeMillis();
		while(i < ops){
			fcpu1 = fcpu1+fcpu2;
			fcpu2 = fcpu1-fcpu2;
			fcpu1 = fcpu2-fcpu1;
			fcpu1 = fcpu1+fcpu1;
			fcpu1 = fcpu1+fcpu1;//fcpu1 = fcpu2+10.8f;fcpu1 = fcpu1+12.8f;fcpu1 = fcpu2+10.8f;fcpu1 = fcpu2+15.8f;
			fcpu2 = fcpu1-fcpu2;
			fcpu1 = fcpu1-fcpu2;
			fcpu1 = fcpu1/fcpu2;
			fcpu2 = fcpu1-fcpu2;
			fcpu1 = fcpu1+fcpu2;
			fcpu1 = fcpu1+fcpu2;//10
//			fcpu2 = fcpu1-fcpu2;
//			fcpu1 = fcpu1-fcpu2;
//			fcpu1 = fcpu1+fcpu2;
//			fcpu2 = fcpu1+fcpu2;
//			fcpu1 = fcpu1-fcpu2;
//			fcpu1 = fcpu1+fcpu2;
//			fcpu2 = fcpu1-fcpu2;
//			fcpu1 = fcpu1*fcpu2;
//			fcpu2 = fcpu1-fcpu2;
//			fcpu1 = fcpu1-fcpu2;//20

			countOperations = 20;
			i++;
		}
		end = System.currentTimeMillis();
		diffFLOPS = (end - start)/1000f;
	}
}

public class BenchmarkCPU{
	public static void main(String[] args) {
		try{
			// IOPS, FLOPS for 1,2,4 threads
			System.out.println("CPU Benchmarking\nPlease wait performing operations");
			int totalThreads = 4;
			float countIOPS=0.0f;
			float countFLOPS=0.0f;
			long countOperations = 0, countLoops = 0;
			int noOfThreads;
			BenchmarkCPUThread bThread[] = new BenchmarkCPUThread[totalThreads];
			for(noOfThreads=1 ; noOfThreads<=totalThreads ; noOfThreads++){
				if(noOfThreads == 3){
					noOfThreads = 4;
					//timer = true;
				}
				bThread[noOfThreads-1] = new BenchmarkCPUThread();
				bThread[noOfThreads-1].start();
			}

			for(noOfThreads=1 ; noOfThreads<=totalThreads ; noOfThreads++){
				if(noOfThreads == 3){
					noOfThreads = 4;
					//timer = true;
				}
				bThread[noOfThreads-1].join();
				countIOPS +=bThread[noOfThreads-1].diffIOPS/3;
				countFLOPS +=bThread[noOfThreads-1].diffFLOPS/7;
				countOperations =bThread[noOfThreads-1].countOperations;
				countLoops = bThread[noOfThreads-1].ops;
			
				System.out.println("Number of threads: "+noOfThreads);
				System.out.println("Number of operations per thread: "+countOperations);
				System.out.println("Number of loops per thread: "+countLoops);
				System.out.println("Total IOPS time in seconds: "+countIOPS);
				System.out.println("IOPS: "+(countOperations*noOfThreads*countLoops)/(countIOPS * 1000000000)+" GIOP/s\n");
				System.out.println("Total FLOPS time in seconds: "+countFLOPS);
				System.out.println("FLOPS: "+(countOperations*noOfThreads*countLoops)/(countFLOPS * 1000000000)+" GFLOP/s\n");
				
			}
		}
		catch(InterruptedException e){
			System.err.println(e.getMessage());
		}
		catch(NumberFormatException e){
			System.err.println(e.getMessage());
		}
	}
}