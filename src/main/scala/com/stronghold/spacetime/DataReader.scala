package com.stronghold.spacetime

import scala.io.Source
import org.apache.spark.sql._
import java.io._

import org.apache.spark.rdd.RDD

class DataSource(spark: SparkSession) {

  def read(url: String): RDD[String] ={
    println(s"Fetching data from $url")
    val result: scala.io.BufferedSource = Source.fromURL(url)
    val result_str: String = result.mkString
    val csvResult: RDD[String] = spark.sparkContext.parallelize(result_str.lines.toList)
    csvResult
  }

  def write(path_out: String, contents: String): Unit = {
    println(s"Writing data to $path_out")
    val file_out = new File(path_out)
    val writer = new BufferedWriter(new FileWriter(file_out))
    writer.write(contents)
    writer.close()
  }

}
