package commlab.weejang

import java.io._
import java.util.Properties
import java.nio.file._

import scala.collection.mutable.LinkedList
import akka.actor.Props

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.wltea.analyzer.lucene.IKAnalyzer

import commlab.weejang.gibbslda._
import commlab.weejang.ETL.Utils
import commlab.weejang.db.DBExecutor
import commlab.weejang.db.addNewDocument
import commlab.weejang.meta.Document

object SystemMain {
 
  def main(args: Array[String]): Unit = {

    //System Configure
    val systemConf = new SystemConf()
    //System Context 
    val systemContext = new SystemContext(systemConf)
    
    val akkaSystem = systemContext.akkaSystem
    val dbExecutor = akkaSystem.actorOf(Props(classOf[DBExecutor],systemContext), "DBExecutor")

    val testDoc = new Document("jangwee")
    println(systemConf)
    Thread.sleep(3000)
    systemContext.quitAndClear()
  }
  
  def done() : Unit = {
    val rawTestDataPath =  "test"
    val formatFilePath  = "formatedtest"
    println(rawTestDataPath)
    val lineDocument = Utils.splitDocument2Seq(rawTestDataPath).map { seqString =>  
       seqString.filter { Utils.isChineseString }.reduce(_ + "\t" + _)  
    }
    
    val bw = new BufferedWriter(new FileWriter(formatFilePath))
    bw.write("\n")
    lineDocument.foreach(bw.write)
    bw.write("\n")
    bw.close()
  }
  
  
  
}