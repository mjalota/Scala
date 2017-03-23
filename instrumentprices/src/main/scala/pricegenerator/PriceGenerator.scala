package pricegenerator

import java.time.LocalDate

import datasource.DataSource
import org.slf4j.LoggerFactory
import scala.io.Source
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by munish on 15/03/2017.
  */

case class Price(date: LocalDate,
                 open: Double,
                 high: Double,
                 low: Double,
                 close: Double,
                 volume: Int,
                 adjClose: Double)


trait PriceGenerator {
  val logger = LoggerFactory.getLogger(this.getClass)
  val headerLines = 1
  val lineSplitToken = ","

  /* 1 - 1 year historic prices given a ticker */
  def dailyPrices(businessDate: LocalDate, ticker: String, days: Long): Seq[Price]

  /* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */
  def returns(businessDate: LocalDate, ticker: String, days: Long): Seq[Double]

  //
  /* 3 – 1 year mean returns */
  def meanReturn(ticker: String, days: Long): Double
}


class PriceGeneratorImpl(private var dataSource: DataSource) extends PriceGenerator {

  def dailyPrices(businessDate: LocalDate, ticker: String, days: Long): Seq[Price] = {
    logger.info(s"Processing $ticker,$businessDate")
    val urlString = dataSource.getDataSourceURL(businessDate: LocalDate, ticker: String, days: Long)

    val f = Future {
      Source.fromURL(urlString).getLines().toList
    }
    val response = Await.result(f, dataSource.getTimout() second)
    val prices = response.drop(headerLines).map(line => convertLineToPriceObject(line))
    prices
  }

  def convertLineToPriceObject(line: String): Price = {
    val tokens = line.split(lineSplitToken)
    val date = LocalDate.parse(tokens(0))
    val open = tokens(1).toDouble
    val high = tokens(2).toDouble
    val low = tokens(3).toDouble
    val close = tokens(4).toDouble
    val volume = tokens(5).toInt
    val adjClose = tokens(6).toDouble

    Price(date, open, high, low, close, volume, adjClose)
  }

  def returns(businessDate: LocalDate, ticker: String, days: Long): Seq[Double] = {
    if (days < 2) {
      throw new RuntimeException("for Daily returns number of days should be greater than 1 ")
    }
    val prices = dailyPrices(businessDate, ticker, days)
    val returns = prices.map(_.close).sliding(2).map { case Seq(x, y, _*) => (x - y) / y }.toList
    returns
  }

  def meanReturn(ticker: String, days: Long): Double = {
    val today = java.time.LocalDate.now()
    val returnsForDays = returns(today, ticker: String, days)
    val res = (returnsForDays.sum / returnsForDays.length)
    res
  }

}