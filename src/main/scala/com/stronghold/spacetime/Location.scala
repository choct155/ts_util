package com.stronghold.spacetime

trait Location {
  val lat: Double
  val long: Double
  val latRad: Double = lat * (math.Pi / 180)
  val longRad: Double = long * (math.Pi / 180)
  val earthRadius: Double = 6371e3

  /**
    * The distance between to coordinates pairs of latitude and
    * longitude is calculated via the haversine formula
    *
    * (src: https://en.wikipedia.org/wiki/Haversine_formula)
    *
    * For any two points on a sphere, the haversine of the central
    * angle is given by
    *
    * ''hav''(d / r) = ''hav''(lat,,2,, - lat,,2,,) + ''cos''(lat,,1,,)''cos''(lat,,2,,)''hav''(long,,2,, - long,,1,,)
    *
    * where ''hav'' is the haversine function
    *
    * ''hav''(angle) = ''sin''^2^(angle / 2) = (1 - ''cos''(angle)) / 2
    *
    * d is the distance between two points and r is the radius of the sphere. The estimated radius of the earth for
    * this purpose is r = 6371e3. Solving for d yields
    *
    * d = 2r''arcsin''(sqrt(sin^2^((lat,,2,, - lat,,1,,) / 2) + ''cos''(lat,,1,,)''cos''(lat,,2,,)''sin''^2^((long,,2,, - long,,1,,) / 2)))
    *
    * @param that
    * @return
    */
  def distanceTo(that: Location): Double = {
    // Capture average lat-long positions
    val latAvg: Double = (this.latRad - that.latRad) / 2
    val longAvg: Double = (this.longRad - that.longRad) / 2
    // Capture expression components under the square root
    val hav1: Double = math.pow(math.sin(latAvg), 2)
    val hav2: Double = math.cos(this.latRad) * math.cos(that.latRad) * math.pow(math.sin(longAvg), 2)
    // Calculate distance
    val d = 2 * this.earthRadius * math.asin(math.sqrt(hav1 + hav2))
    d
  }
}

case class Zip(lat: Double, long: Double, id: Int) extends Location
