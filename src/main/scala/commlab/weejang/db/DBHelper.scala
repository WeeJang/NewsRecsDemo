package commlab.weejang.db

import scala.collection.immutable.HashMap
import scala.concurrent.Future
import scala.concurrent.duration._
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import commlab.weejang.meta.Persona
import commlab.weejang.SystemContext
import commlab.weejang.meta.TinyLRModelStruct
import commlab.weejang.meta.TinyLRModelStruct

/**
 * 数据库
 */
class DBHelper(val sc : SystemContext){
   
 /**
  * instance the excutor to operate db
  */
  private val executor_ = sc.akkaSystem.actorOf(Props[DBExecutor])
  
  /**
   * timeout of operate 
   */
  implicit val timeout = Timeout(5 seconds)
  
  /**
   * research persona information
   */
  def findPersona(personaId : String) : Future[TinyLRModelStruct] = {
      ask(executor_,findPersonaTopicLRWeight(personaId)).mapTo[TinyLRModelStruct]
  }

  /**
   * create new persona
   */
  def createNewPersona(personaId : String,model :TinyLRModelStruct) : Unit = {
      ask(executor_,addNewPersona(personaId,model))
  }
  
  /**
   * delete persona 
   */
  def deletePersona(personaId : String) : Unit = {
      ask(executor_,deletePersonaTopicLRWeight(personaId))
  }
  
  /**
   * update persona
   */
  def updatePersona(personaId : String,model : TinyLRModelStruct) : Unit = {
      ask(executor_,updatePersonaTopicLRWeight(personaId,model))
  }
  
  /**
   * research document information
   */
   def findDocument(documentId : String) : Future[Vector[Double]]  = {
     ask(executor_,findDocumentTopicWeight(documentId)).mapTo[Vector[Double]]
   } 
   
   /**
    * create new document
    */
   def createNewDocument(documentId : String,weights : Vector[Double]) : Unit = {
     ask(executor_,addNewDocument(documentId,weights))
   }
   
   /**
    * delete document 
    */
   def deleteDocuemt(documentId : String) : Unit = {
     ask(executor_,deleteDocumentTopicWeight(documentId))
   }
   
   /**
    * update document
    */
   def updateDocument(documentId : String,weights : Vector[Double]) : Unit = {
     ask(executor_,updateDocumentTopicWeight(documentId,weights))
   }
   
 
}