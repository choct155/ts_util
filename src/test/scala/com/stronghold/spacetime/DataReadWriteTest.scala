package com.stronghold.spacetime

import org.apache.spark.rdd.RDD
import org.apache.spark.sql._

import sys.process._

object DataReadWriteTest {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName("Data Source Read Write Test")
      .master("local [*]")
      .getOrCreate()

    val url: String = "http://files.zillowstatic.com/research/public/Neighborhood/Neighborhood_Zhvi_AllHomes.csv"
    val path_out: String = "./data/zillow_nbhd.csv"

    // Read data
    val src: DataSource = new DataSource(spark)
    val zdata: RDD[String] = src.read(url)

    println(zdata)

    // Write data
    //src.write(path_out, zdata)
    //s"cat $path_out" !!
  }

}
