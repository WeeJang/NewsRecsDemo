package commlab.weejang.ETL

import java.io._
import java.nio.file._
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import scala.collection.mutable.ArrayBuffer

import org.wltea.analyzer.lucene.IKAnalyzer

/**
 * Utils Objects
 *
 *
 * @author jangwee
 * @date 2015.8.1
 */

object Utils {

  /**
   * Using Unicode to check if it it chinese char
   *
   * @param c char need to be check
   * @return if is chinese char true else false
   */
  def isChineseChar(c: Char): Boolean = {
    val ub = Character.UnicodeBlock.of(c)
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
      || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
      || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
      || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
      || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
      || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
      || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
      return true;
    }
    return false;
  }

  /**
   * Is Pure Chinese String
   *
   * @param str string need to be check
   * @return if pure true else false
   */
  def isChineseString(str: String): Boolean = {
    if (str.exists { !isChineseChar(_) }) false else true
  }

  /**
   * Divide a documnet into a seq string using IKAnalyzer
   *
   * @param filePath the document absolutely path
   * @param isUsingSmart if you want to  use smart split mode ,the flag should be set to true else false.
   *         Default flag is true.
   *         [IKAnalyzer has 2 mode , see info : http://git.oschina.net/wltea/IK-Analyzer-2012FF]
   * @param fieldName the name of field which is be token in document
   * @return Option[Seq[String]]  if the document is not exit or empty,return None.
   *
   */

  def splitDocument2Seq(filePath: String, fieldName: String = "",
    isUsingSmart: Boolean = true): Option[Seq[String]] = {

    if (!new File(filePath).exists) return None

    import scala.collection.mutable.ArrayBuffer
    val arrayBuffer = new ArrayBuffer[String](1024)

    //initial IKAnalyzer
    val ikAnalyzer = new IKAnalyzer()
    ikAnalyzer.setUseSmart(isUsingSmart)
    val ts = ikAnalyzer.tokenStream(fieldName, new FileReader(new File(filePath)))
    val term = ts.getAttribute(classOf[CharTermAttribute])

    //token
    while (ts.incrementToken()) {
      arrayBuffer.append(term.toString)
    }
    ts.close()

    Some(arrayBuffer.toSeq)
  }

  /**
   * watch the file system 
   * 
   * @param watchLogDir directory which is watched
   */
  def watcher(watchLogDir : String): Unit = {
    val watchService = FileSystems.getDefault.newWatchService()
    Paths.get(watchLogDir).register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
      StandardWatchEventKinds.ENTRY_DELETE,
      StandardWatchEventKinds.ENTRY_MODIFY)

    var flag = true
    while (flag) {
      val key = watchService.take()
      val eventIter = key.pollEvents().iterator()
      while (eventIter.hasNext()) {
        val event = eventIter.next()
        println(event.context() + "courr : " + event.kind())//TODO :
      }
      if (!key.reset())
        flag = false
    }
  }
  
  /**
   * write the iterator to file 
   * 
   * @param fileName the name of file
   * @param fileInfo the iterator of String.
   *         Note : Each element of String will be end with "\n" which is writed into file
   */

  def fileWriterByLine(fileName: String, fileInfo: Iterator[String]): Unit = {
    var bufferedWriter: BufferedWriter = null
    var result: Boolean = false
    try {
      bufferedWriter = new BufferedWriter(new FileWriter(new File(fileName)))
      fileInfo.foreach(str => bufferedWriter.write(str + "\n"))
    } catch {
      case ex: Exception => println(ex.getMessage)
    } finally {
      bufferedWriter.close()
    }
  }
  
  

}