package commlab.weejang.ETL

import scala.util.{ Try, Success, Failure }

import java.io._

import org.json4s._
import org.json4s.jackson.JsonMethods._

object Extractor {

  /**
   * part main
   */
  def part_main(args: Array[String]): Unit = {

    //    extractAccessInfo("./data/log/discoverysvr.log.8").foreach(println)
    val protocalFile = "./data/log/"

    val userInfoWithLocationFile = "/home/jangwee/userInfoWithLocation.txt"
    val accessInfo = "/home/jangwee/accessInfo.txt"

    val protocalLogFile = fileFilter(protocalFile, "2015").map { str => "./data/log/" + str }.toIterator
    val output1iter = protocalLogFile.map { extractUserInfoWithLocation }.flatMap {
      tupleIter =>
        val stringIter = {
          tupleIter.map { IterElem =>
            IterElem._1 + "," + IterElem._2 + "," + IterElem._3 + "," + IterElem._4
          }
        }   
        stringIter
    }
     Utils.fileWriterByLine(userInfoWithLocationFile, output1iter)
      
      val accessLogFile = fileFilter(protocalFile, "discover").map { str => "./data/log/" + str }.toIterator
      val output2iter = accessLogFile.map{ extractAccessInfo }.flatMap{
        tupleIter =>
          val stringIter = {
            tupleIter.map( iterElem => iterElem._1 + "," + iterElem._2 + "," + iterElem._3)
          }
          stringIter
      }
       Utils.fileWriterByLine(accessInfo, output2iter)
  }

  /**
   * filter the access file
   * @param rootDir  root of directory
   * @return
   *      list of file
   */
  def fileFilter(rootDir: String, fileNameStartWithString: String): List[String] = {
    val file = new File(rootDir)
    if (!file.isDirectory)
      Nil
    else {
      List(file.list(): _*).filter { _.contains(fileNameStartWithString) }
    }
  }

  /**
   * extract user info with location
   *
   * @param fileName the fileName
   * @return
   *     the iterator of (date,devid,mod,location)
   *     eg : (Mon, 27 Jul 2015 10:08:46,IMEI:865982024903877,MI NOTE LTE,113.959007-22.541658)
   */

  def extractUserInfoWithLocation(fileName: String): Iterator[(String, String, String, String)] = {

    val iter = extractProtocalInfo(fileName)

    iter.filter(_.isDefined).map { opt =>
      val info = opt.get
      val jsonInfo = parseUserJson(info._2)
      if (jsonInfo.isDefined) {
        val infoTuple = jsonInfo.get
        Some(info._1, infoTuple._1, infoTuple._2, infoTuple._3)
      } else None
    }.filter(_.isDefined).map(_.get)
  }

  /**
   * extractAccessInfo access new number
   *
   * @param fileName the fileName
   * @return Iterator[(TimeStamp,Cid,Devid)]
   *          TimeStamp :   the access time stamp(eg:2015-07-18 21:22:41 )
   *          Cid       :   Id of News
   *          Devid     :   Id of device(IMEI or MAC)
   */
  def extractAccessInfo(fileName: String): Iterator[(String, String, String)] = {
    val iter = scala.io.Source.fromFile(new File(fileName)).getLines()

    val accessLogExtractorRE = "^(.+)\\+0800.*GET /discoverynews\\?cid=(.+)&devid=(\\S+).*".r

    iter.map {
      filteredStr =>
        filteredStr match {
          case accessLogExtractorRE(timeStamp, cid, devid) =>
            Some(timeStamp, cid, devid)
          case _ => None
        }
    }.filter(_.isDefined).map(_.get)
  }

  /**
   * extractProtocalInfoString
   *
   * @param fileName file name
   * @return
   *        Iterator[Option[(date,userInfoJson)]]
   */

  def extractProtocalInfo(fileName: String): Iterator[Option[(String, String)]] = {
    val fileLinesIter = scala.io.Source.fromFile(new File(fileName)).getLines()

    val userInfoExtrator1RE = "^(.*) helper\\.py\\[line:126\\] INFO decode json (.*)$".r
    val userInfoExtrator2RE = "^(.*) helper\\.py\\[line:128\\] INFO encode json len (.*)$".r

    val userInfoOption = fileLinesIter.map {
      line =>
        line match {
          case userInfoExtrator1RE(date, infoJson) =>
            Some(date, infoJson)
          case userInfoExtrator2RE(date, infoJson) =>
            Some(date, infoJson)
          case _ => None
        }
    }
    userInfoOption
  }

  /**
   * parse user json
   *
   * @param json string
   * @return
   *      (devid,mod,location)
   */
  def parseUserJson(jsonStr: String): Option[(String, String, String)] = {
    //com.fasterxml.jackson.core.JsonParseException: Unrecognized token
    val jValue: Try[JValue] = Try { parse(jsonStr) }
    jValue match {
      case Success(jobj) => {
        val listObj = for {
          JObject(obj) <- jobj
          JField("devid", JString(devidStr)) <- obj
          JField("mod", JString(modStr)) <- obj
          JField("location", JString(locationStr)) <- obj
        } yield ((devidStr, modStr, locationStr))
        if (listObj.length == 0) None else Some(listObj.head)
      }
      case Failure(e) => {
        None //TODO : log exception
      }
    }
  }
}