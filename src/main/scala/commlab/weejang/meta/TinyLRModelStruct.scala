package commlab.weejang.meta

import org.apache.spark.mllib.linalg.Vector

/**
 * simple data structure for LR model saving
 */
case class TinyLRModelStruct(weights: Vector,intercept : Double,threshould : Option[Double]) extends Serializable

