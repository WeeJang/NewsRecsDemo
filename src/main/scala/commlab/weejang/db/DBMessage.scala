package commlab.weejang.db

import commlab.weejang.meta.TinyLRModelStruct


sealed trait DBMessage

//message about CRUD on persona 
case class findPersonaTopicLRWeight(personaId : String) extends DBMessage

case class addNewPersona(personaId :String,model : TinyLRModelStruct) extends DBMessage

case class updatePersonaTopicLRWeight(personaId : String,model :TinyLRModelStruct) extends DBMessage

case class deletePersonaTopicLRWeight(personId : String) extends DBMessage

//message about CRUD on documents
case class findDocumentTopicWeight(documentId : String) extends DBMessage

case class addNewDocument(documentId : String,topicWeight : Vector[Double]) extends DBMessage

case class updateDocumentTopicWeight(documentId : String,topicWeight: Vector[Double]) extends DBMessage

case class deleteDocumentTopicWeight(documentId : String) extends DBMessage

case class failMessage(failInfo : String) extends DBMessage

case class successMessage(successInfo : String) extends DBMessage
