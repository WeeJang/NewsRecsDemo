package commlab.weejang.meta


import scala.collection.immutable.Vector
/**
 * 用户画像
 * 
 * @author jangwee
 */


class Persona(val id : String) extends Serializable {
   
    /**
     * the number of topic
     */
    private var topicNum_ = 100 //TODO : fix it : get from system.properties 
    
    /**
     * topic LR weight, whose length is the topicNum_
     */
    private var topicLRWeight_ : Vector[Double] = _
    
    /**
     * get the number of topic
     */
    def topicNum = topicNum_
    
    /**
     * get topic LR weight
     */
    def topicLRWeight : Vector[Double] = {
      if (topicLRWeight_ == null) 
          topicLRWeight_ = Vector((1 to topicNum_).map(_*0.0) : _*)
      topicLRWeight_
    }
    
    /**
     * update topic LR Weight
     */
    def updateTopicLRWeight(newTopicLRWeight : Vector[Double]) = {
      topicLRWeight_ = newTopicLRWeight
    }
   
    
    
    
}