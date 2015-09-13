/**
 * @author
 *         jangwee
 */
package commlab.weejang.db

import akka.actor.Actor

import com.mongodb.casbah.Imports._

import commlab.weejang.SystemContext

class DBExecutor(sc: SystemContext) extends Actor {

  val conf = sc.conf
  //Mongo Client
  val mongoClient = MongoClient(MongoClientURI(conf.mongodbUri))
  //Mongo DB for this Application
  val db = mongoClient(conf.mongodbName)
  //collection for persona 
  val personaCollection = db(conf.personaCollection)
  //collection for document
  val documentCollection = db(conf.documentCollection)

  def receive = {

    case findPersonaTopicLRWeight(personaId) => {
      val query = MongoDBObject("personaId" -> personaId)
      val ret = personaCollection.find(query)
      val backMsg = if (ret.count == 0) {
        failMessage("not find the persona #{personaId} topic LR Weights")
      } else if (ret.count > 1) {
        failMessage("find more than on persona #{personaId} topic LR Weights")
      } else {
        ret.next()
      }
      sender ! backMsg
    }

    case addNewPersona(personaId, model) => {
      require(personaId != null && model != null)
      val newElem = MongoDBObject("personaId" -> personaId,
        "model" -> model)
      personaCollection.insert(newElem)
    }

    case updatePersonaTopicLRWeight(personaId, model) => {
      require(personaId != null && model != null)
      val qurey = MongoDBObject("personaId" -> personaId)
      val newElem = MongoDBObject("personaId" -> personaId,
        "model" -> model)
      personaCollection.update(qurey, newElem)
    }

    case deletePersonaTopicLRWeight(personaId) => {
      val newElem = MongoDBObject("personId" -> personaId)
      personaCollection.remove(newElem)
    }

    case findDocumentTopicWeight(documentId) => {
      val query = MongoDBObject("documentId" -> documentId)
      val ret = documentCollection.find(query)
      val backMsg = if (ret.count == 0) {
        failMessage("not find the document #{documentId} topic LR Weights")
      } else if (ret.count > 1) {
        failMessage("find more than on document #{documentId} topic LR Weights")
      } else {
        ret.next()
      }
      sender ! backMsg
    }

    case addNewDocument(documentId, weights) => {
      require(documentId != null && weights != null)
      val newElem = MongoDBObject("documentId" -> documentId,
        "weights" -> weights)
      println(newElem)
      personaCollection += newElem
    }

    case updateDocumentTopicWeight(documentId, weights) => {
      require(documentId != null && weights != null)
      val qurey = MongoDBObject("documentId" -> documentId)
      val newElem = MongoDBObject("documentId" -> documentId,
        "weights" -> weights)
      documentCollection.update(qurey, newElem)
    }

    case deleteDocumentTopicWeight(documentId) => {
      val newElem = MongoDBObject("documentId" -> documentId)
      documentCollection.remove(newElem)
    }
  }

}

object DBExecutor {
  def apply(sc: SystemContext) = new DBExecutor(sc)
  def unapply(dbexec: DBExecutor) = dbexec.conf
}
