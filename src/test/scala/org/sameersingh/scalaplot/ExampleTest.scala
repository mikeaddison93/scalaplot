package org.sameersingh.scalaplot

import gnuplot.GnuplotPlotter
import jfreegraph.JFGraphPlotter
import org.junit._

/**
 * Examples that demonstrate how to use the library. Not really tests.
 * @author sameer
 */
@Test
class ExampleTest {

  @Test
  def testExplicit(): Unit = {
    // seqs
    val x = (1 until 100).map(_.toDouble)
    val y = (1 until 100).map(j => math.pow(j, 2))

    // dataset
    val series = new MemXYSeries(x, y, "Square")
    val data = new XYData(series)

    // add cube
    data += new MemXYSeries(x, x.map(i => i * i * i), "Cube")

    // chart
    val chart = new XYChart("Powers!", data)
    chart.showLegend = true

    val file = java.io.File.createTempFile("example1", "pdf")
    println(file.getCanonicalPath)
    new JFGraphPlotter(chart).writeToPdf(file)
    new GnuplotPlotter(chart).writeToPdf(file)
  }

  @Test
  def testDataImplicit(): Unit = {
    import org.sameersingh.scalaplot.Implicits._
    // seqs
    val x = (1 until 100).map(_.toDouble)
    val y1 = (1 until 100).map(j => math.pow(j, 1))
    val y2 = (1 until 100).map(j => math.pow(j, 2))
    val y3 = (1 until 100).map(j => math.pow(j, 3))

    // series
    val s1: XYSeries = x -> y1
    val s2: XYSeries = x zip y2
    val s3: XYSeries = x -> Y(y3)

    // data using series
    val d1: XYData = s1
    val d2: XYData = Seq(s1, s2)

    // data without series
    val d3: XYData = x -> Seq(Y(y1), Y(y2), Y(y3))
    val d4: XYData = x -> Ys(y1, y2, y3)
  }

  @Test
  def testChartImplicit(): Unit = {
    import org.sameersingh.scalaplot.Implicits._
    // seqs
    val x = (1 until 100).map(_.toDouble)
    val y1 = (1 until 100).map(j => math.pow(j, 1))
    val y2 = (1 until 100).map(j => math.pow(j, 2))
    val y3 = (1 until 100).map(j => math.pow(j, 3))
    // series
    val s1: XYSeries = x -> y1
    val s2: XYSeries = x zip y2
    // data
    val d: XYData = x -> Seq(Y(y1, "1"), Y(y2, "1"), Y(y3, "1"))

    // chart with data
    val c1: XYChart = d

    // chart with series
    val c2 = plot(data = s1)
    val c3 = plot(s1)

    // chart without series
    val c5 = plot(data = x -> Ys(y1, y2, y3))
    val c6 = plot(x -> Ys(y1, y2, y3))
  }
}
