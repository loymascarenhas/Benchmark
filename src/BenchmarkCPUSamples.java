class BenchmarkCPUThrea extends Thread{
	long timerInMinutes;
	int ops = 1000000000; //10^9
	float diffIOPS = 0;
	float diffFLOPS = 0;
	long countOperations = 0;
	String operation = "";

	public void run() {
		//IOPS operation
		int icpu1=10, icpu2=20;
		float fcpu1=10.5f, fcpu2=20.5f;
		long tempTime = 0;
		long countSeconds =0;
		while(countSeconds <= timerInMinutes*60){
			long endTime = System.currentTimeMillis()/1000;
			if(endTime != tempTime){
				tempTime = endTime;
				System.out.println(countSeconds+","+countOperations);
				countOperations = 0;
				countSeconds++;
			}
			if(operation.equals("iop")){
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
				countOperations += 80;
			}
			else if(operation.equals("flop")){
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu2-fcpu1;
				fcpu1 = fcpu1+10.8f;
				fcpu1 = fcpu1+10.8f;//fcpu1 = fcpu2+10.8f;fcpu1 = fcpu1+12.8f;fcpu1 = fcpu2+10.8f;fcpu1 = fcpu2+15.8f;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1/fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1+2.2f;
				fcpu1 = fcpu1+fcpu2;//10
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1+fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1*fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;//20
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;//30
				fcpu2 = fcpu1*fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;
				fcpu1 = fcpu1-fcpu2;
				fcpu1 = fcpu1+fcpu2;
				fcpu2 = fcpu1-fcpu2;//40
				countOperations += 80;
			}
		}
	}
}

public class BenchmarkCPUSamples{
	public static void main(String[] args) {
		try{
			
			// IOPS, FLOPS for 1,2,4 threads
			int totalThreads = 4;

			int noOfThreads;
			BenchmarkCPUThrea bThread[] = new BenchmarkCPUThrea[totalThreads];

			for(noOfThreads=1 ; noOfThreads<=totalThreads ; noOfThreads++){
				bThread[noOfThreads-1] = new BenchmarkCPUThrea();
				bThread[noOfThreads-1].operation = args[0];
				bThread[noOfThreads-1].timerInMinutes = 10;
				bThread[noOfThreads-1].start();
			}
		}
		catch(NumberFormatException e){
			System.err.println(e.getMessage());
		}
	}
}