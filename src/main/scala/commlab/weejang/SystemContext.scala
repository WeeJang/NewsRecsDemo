package commlab.weejang

/**
 * System Context
 * 
 * @author jangwee
 */

import akka.actor.ActorSystem

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

class SystemContext(val conf :SystemConf) {
  
     //akka system
     private val akkaSystem_ = ActorSystem(conf.akkaName)
     
     //spark configuration
     private val sparkConf_ = new SparkConf()
                   .setAppName(conf.sparkName)
                   .setMaster(conf.sparkMaster)
                   
     //spark context
     private val sparkContext_ = new SparkContext(sparkConf_)
     
     /**
      * getter of akkaSystem_
      */
     def akkaSystem = akkaSystem_
     
     /**
      * getter of sparkContext_
      */
     def sparkContext = sparkContext_
     
     /**
      * quit and clear the source
      */
     def quitAndClear() = {
       if(!akkaSystem_.isTerminated){
         akkaSystem_.shutdown()
       }      
       sparkContext_.stop() 
     }
     
     
}