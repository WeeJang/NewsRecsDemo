package commlab.weejang.meta

/**
 * 文档画像
 */

class Document(val id : String) extends Serializable {
    /**
     * the number of topic
     */
     private var topicNum_ = 100 // TODO
     /**
      * document-topic weight,whose length is the topicNum_
      */
     private var documentTopicWeight_ : Vector[Double] = _
     
     /**
      * get the number of topic
      */
     def topicNum = topicNum_
     
     /**
      * get documnet-topic weight 
      */
     def documentTopicWeight : Vector[Double] = {
       if(documentTopicWeight_ == null){
         documentTopicWeight_ = Vector((1 to topicNum_).map(_ * 0.0) : _*)
       }
       documentTopicWeight_
     }
     
     /**
      * update the document-topic weight
      */
     def updateDocumentTopicWeight(newWeight : Vector[Double]) = {
       documentTopicWeight_ = newWeight
     }
}