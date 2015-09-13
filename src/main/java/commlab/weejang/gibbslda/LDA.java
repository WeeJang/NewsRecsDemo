package commlab.weejang.gibbslda;

public class LDA {
	
	/**
	 * estimate parameter from scratch
	 * 
	 * @param alpha
	 *            hyper parameter for Dirichlet Distribution of Topic
	 * @param beta
	 *            hyper parameter for Dirichlet Distribution of Word
	 * @param K
	 *            the number of the topics
	 * @param niters
	 *            the number of iterations
	 * @param savestep
	 *            the number of steps to save the model since the last save
	 * @param twords
	 *            the number of most likely words to be printed for eache topic
	 * @param withrawdata
	 *            whether we include raw data in the input
	 * @param wordMapFileName
	 *            the wordmap file
	 */
	public void ParamEstFromScratch(double alpha, double beta, int K,
			int niters, int savestep, int twords, boolean withrawdata,
			String wordMapFileName) {

		LDAOption option = new LDAOption(alpha, beta, K, niters, savestep,
				twords, withrawdata, wordMapFileName);
		
		option.est = true;
		option.estc = false;
		option.inf = false;

		paramEstimation(option);

	}

	/**
	 * estimate parameter continue with the previous model
	 * 
	 * @param alpha
	 *            hyper parameter for Dirichlet Distribution of Topic
	 * @param beta
	 *            hyper parameter for Dirichlet Distribution of Word
	 * @param K
	 *            the number of the topics
	 * @param niters
	 *            the number of iterations
	 * @param savestep
	 *            the number of steps to save the model since the last save
	 * @param twords
	 *            the number of most likely words to be printed for eache topic
	 * @param withrawdata
	 *            whether we include raw data in the input
	 * @param wordMapFileName
	 *            the wordmap file
	 */
	public void ParamEstFromPrevEstModel(double alpha, double beta, int K,
			int niters, int savestep, int twords, boolean withrawdata,
			String wordMapFileName) {
		LDAOption option = new LDAOption(alpha, beta, K, niters, savestep,
				twords, withrawdata, wordMapFileName);
		option.est = true;
		option.estc = false;
		option.inf = false;
		
		paramEstimation(option);
	}

	/**
	 * infer the topics for new data
	 * 
	 * @param alpha
	 *            hyper parameter for Dirichlet Distribution of Topic
	 * @param beta
	 *            hyper parameter for Dirichlet Distribution of Word
	 * @param K
	 *            the number of the topics
	 * @param niters
	 *            the number of iterations
	 * @param savestep
	 *            the number of steps to save the model since the last save
	 * @param twords
	 *            the number of most likely words to be printed for eache topic
	 * @param withrawdata
	 *            whether we include raw data in the input
	 * @param wordMapFileName
	 *            the wordmap file
	 */
	public void  Inference4NewData() {
		
		LDAOption option = new LDAOption();

		option.est = false;
		option.estc = false;
		option.inf = true;

		Inferencer inferencer = new Inferencer();
		inferencer.init(option);

		Model newModel = inferencer.inference();

		for (int i = 0; i < newModel.phi.length; ++i) {
			// phi: K * V
			System.out.println("-----------------------\ntopic" + i + " : ");
			for (int j = 0; j < 10; ++j) {
				System.out.println(inferencer.globalDict.id2word.get(j) + "\t"
						+ newModel.phi[i][j]);
			}
		}
	}

	private void paramEstimation(LDAOption option) {

		option.est = true;
		option.estc = false;
		option.inf = false;

		Estimator estimator = new Estimator();
		estimator.init(option);
		estimator.estimate();
	}

}
