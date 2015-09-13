package commlab.weejang

import java.util.Properties

/**
 * System configure
 */

class SystemConf {

  /**
   * load property file
   */
  private val prop_ : Properties = {
    val prop = new Properties()
    val inputStream = getClass.getResourceAsStream("/newsRecs.properties")
    prop.load(inputStream)
    prop
  }

  /**
   * get property .If it not exists,return the default string.
   */
  def getPropertyOrElse(propertyName: String, default: String): String = {
    val property = prop_.getProperty(propertyName)
    if (property == null) default else property
  }

  //corpus path
  val rawCorpusPath = getPropertyOrElse("corpus.rawInPath", "/home/jangwee/application/ChineseSplit/rawData/utf/Reduced/")
  //load  formated corpus path
  val formatCorpusPath = getPropertyOrElse("corpus.formatOutPath", "/home/jangwee/application/ChineseSplit/rawData/utf/Reduced/format.txt")

  //LDA model Dir
  val ldamodelDir = getPropertyOrElse("gibbsLDA.modelDir", "/home/jangwee/application/ChineseSplit/rawData/utf/Reduced/model/")
  val ldamodelName = getPropertyOrElse("gibbsLDA.modelName", "model-final")
  val ldamodelParamAlpha = getPropertyOrElse("gibbsLDA.alpha", "-1.0")
  val ldamodelParamBeta = getPropertyOrElse("gibbsLDA.beta", "-1.0")
  val ldamodelParamK = getPropertyOrElse("gibbsLDA.K", "100")

  //watched Log Dir
  val watchLogDir = getPropertyOrElse("watchedLogDir", "/home/jangwee/application/ChineseSplit/rawData/utf/Reduced/log/")

  //raw test file
  val rawTestDataPath = ldamodelDir + "casestudy/test.txt"
  //formated test file
  val formatFilePath = ldamodelDir + "casestudy/testFormat.txt"

  //mongodb
  val mongodbUri = getPropertyOrElse("mongodb.uri", "mongodb://localhost:5586/")
  val mongodbName = getPropertyOrElse("mongodb.name", "NewsRecsDemo")
  val personaCollection = getPropertyOrElse("mongodb.personaCollection", "personaCollection")
  val documentCollection = getPropertyOrElse("mongodb.documentCollection", "documentCollection")
  //akka
  val akkaName = getPropertyOrElse("akka.name", "NewsRecsDemo")

  //spark
  val sparkName = getPropertyOrElse("spark.appName", "NewsRecsDemo")
  val sparkMaster = getPropertyOrElse("spark.master", "local[2]")

  /**
   * toString function
   */
  override def toString(): String = {
    "rawCorpusPath : " + rawCorpusPath + "\n" +
      "formatCorpusPath : " + formatCorpusPath + "\n" +
      "ldamodelDir : " + ldamodelDir + "\n" + 
      "ldamodelName : " + ldamodelName + "\n" +
      "ldamodelParamAlpha : " + ldamodelParamAlpha + "\n" +
      "ldamodelParamBeta : " + ldamodelParamBeta + "\n" +
      "ldamodelParamK : " + ldamodelParamK + "\n" +
      "watchLogDir : " + watchLogDir + "\n" +
      "rawTestDataPath : " + rawTestDataPath + "\n" +
      "formatFilePath : " + formatFilePath + "\n" +
      "mongodbUri : " + mongodbUri + "\n" +
      "mongodbName : " + mongodbName + "\n" +
      "personaCollection : " + personaCollection + "\n " + 
      "documentCollection : " + documentCollection + "\n" +
      "akkaName : " + akkaName + "\n" +
      "sparkName : " + sparkName + "\n" +
      "sparkMaster : " + sparkMaster + "\n"
  }
}