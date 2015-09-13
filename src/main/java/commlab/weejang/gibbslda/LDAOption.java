package commlab.weejang.gibbslda;


public class LDAOption {
	
	
	
	
	/**
	 * Specify whether we want to estimate model from scratch
	 */
	public boolean est = false;
	
	/**
	 * Specify whether we want to continue the last estimation
	 */
	public boolean estc = false;
	
	/**
	 * Specify whether we want to do inference
	 */
	public boolean inf = false;
	
	/**
	 * Specify directory
	 */
	public String dir = "/home/jangwee/application/ChineseSplit/rawData/utf/Reduced/model/";
	
	/**
	 * Specify data file
	 */
	public String dfile = "casestudy/testFormat.txt";
	
	/**
	 * Specify the model name
	 */
	public String modelName = "model-final";
	
	/**
	 * Specify alpha
	 */
	public double alpha = -1.0;
	
	/**
	 * Specify beta
	 */
	public double beta = -1.0;
	
	/**
	 * Specify the number of topics
	 */
	public int K = 100;
	
	/**
	 * Specify the number of iterations
	 */
	public int niters = 1000;
	
	/**
	 * Specify the number of steps to save the model since the last save
	 */
	public int savestep = 100;
	
	/**
	 * Specify the number of most likely words to be printed for each topic
	 */
	public int twords = 100;
	
	/**
	 * Specify whether we include raw data in the input
	 */
	public boolean withrawdata = false;
	
	/**
	 * usage="Specify the wordmap file
	 */
	public String wordMapFileName = "wordmap.txt";
	
	public LDAOption(){
		
	}
	
	
	public LDAOption(double alpha, double beta, int K,
			int niters, int savestep, int twords, boolean withrawdata,
			String wordMapFileName){
		this.alpha = alpha;
		this.beta = beta;
		this.K = K;
		this.niters = niters;
		this.savestep = savestep;
		this.twords = twords;
		this.withrawdata = withrawdata;
		this.wordMapFileName = wordMapFileName;
	}
}
