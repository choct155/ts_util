package com.stronghold.spacetime

import org.apache.spark.sql._
import org.apache.spark.sql.types._

object LocationTest {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession.builder()
      .appName("Location Class Test")
      .getOrCreate()
    import spark.implicits._

    // trait Person {
    //   val id: Int
    //   val age: Int
    // }

    //case class GeoPerson(id: Int, age: Int, loc: Zip) extends Person(id, age) with Location
    case class GeoPerson(id: Int, age: Int, loc: Zip)

    // Set file path
    val fpath: String = "/home/choct155/data/shp/2017_Gaz_zcta_national.txt"

    // Capture ZIPs
    val zipDF_tmp: DataFrame = spark.read.option("sep", "\\t").option("header", "true").csv(fpath)
    val zipCols: Array[String] = zipDF_tmp.columns.map(col => col.trim)
    val zipsDF: DataFrame = zipDF_tmp.toDF(zipCols: _*)
    val zips: Dataset[Zip] = zipsDF.toDF(zipCols: _*)
      .withColumn("id", zipsDF("GEOID").cast(IntegerType))
      .withColumn("lat", zipsDF("INTPTLAT").cast(DoubleType))
      .withColumn("long", zipsDF("INTPTLONG").cast(DoubleType))
      .select("id", "lat", "long").as[Zip]

    // Determine the distance between the first ZIP and all others in a sample
    val zipSub: Dataset[Zip] = zips.limit(11)
    val zipFirst: Zip = zipSub.first()
    println(s"Primary ZIP: ${zipFirst.toString}")
    zipSub.foreach {
      z =>
        var dist_tmp: Double = zipFirst.distanceTo(z)
        println(s"Distance to the following ZIP: ${z.toString}: $dist_tmp km")
    }

  }
}
