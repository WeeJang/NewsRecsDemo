package commlab.weejang.ml

/**
 * @author jangwee
 * 
 * Infer the result by  Spark
 */


import java.io.File
import org.apache.spark.mllib.classification.{ LogisticRegressionWithLBFGS, LogisticRegressionModel }
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.linalg.SparseVector
import commlab.weejang.meta.TinyLRModelStruct
import commlab.weejang.SystemContext
import org.apache.spark.mllib.regression.LabeledPoint



class InferUserPreference(sc : SystemContext) {

  //Spark Context
  val sparkContext = sc.sparkContext
  
  /**
   * train the LR model
   * @param trainData : labeledPoint for training LR
   * @return model parameter wrapped TinyLRModelStruct
   */
  def trainModel(trainData : RDD[LabeledPoint]) : TinyLRModelStruct = {    
    //train model
    val model = new LogisticRegressionWithLBFGS()
      .setNumClasses(2)
      .run(trainData)
    
    TinyLRModelStruct(model.weights,model.intercept,model.getThreshold)
  }

  /**
   * predict the model using model
   * 
   * @param personaId  personaId is used to find the model parameter from db or file
   * @param feature    the feature of the sample which will be predicted
   * @return  the result of predictin
   */
  def predictWithModel(personaId: String, feature: RDD[Vector]): RDD[Double] = {
    //find the model path according to personaId
    //TODO  
    val lrmodelstruct = TinyLRModelStruct(weights = new SparseVector(10,Array[Int](3,2),Array(0.5,0.2)),
                                                      intercept = 0.4,threshould = None)
    //load model
    val lrmodel = new LogisticRegressionModel(lrmodelstruct.weights,lrmodelstruct.intercept)
    //predict the sample
    lrmodel.predict(feature)
  }

}