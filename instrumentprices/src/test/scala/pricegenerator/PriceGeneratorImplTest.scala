package pricegenerator

import java.time.LocalDate
import datasource.{DataSource, PriceDataSource}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * Created by munish on 15/03/2017.
  */
class PriceGeneratorImplTest extends FunSuite with BeforeAndAfterEach with MockitoSugar {

  val dataSource = mock[DataSource]
  var priceGenerator: PriceGenerator = null

  override def beforeEach() {
    val today = java.time.LocalDate.now()

    priceGenerator = new PriceGeneratorImpl(dataSource);
    when(dataSource.getDataSourceURL(today, "abc", 2))
      .thenReturn("http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=2&b=12&c=2017&d=2&e=15&f=2017&g=d&ignore=.csv")
    when(dataSource.getTimout()).thenReturn(2)
  }

  override def afterEach() {
    priceGenerator = null
  }

  test("testReturns") {
    val today = java.time.LocalDate.now()

    val returns = priceGenerator.returns(today, "abc", 2)
    assert( returns.head == 0.0018684716649823281)
  }

  test("testMeanReturn") {
    val today = java.time.LocalDate.now()

    val meanReturn = priceGenerator.meanReturn( "abc", 2)
    assert( meanReturn == 9.815529328542163E-4)
  }

  test("testConvertLineToPriceObject") {
    val testString = "2017-03-14,843.640015,847.23999,840.799988,845.619995,779900,845.619995"
    val priceGeneratorImpl = priceGenerator.asInstanceOf[PriceGeneratorImpl]
    val priceObjectFromConvertLine = priceGeneratorImpl.convertLineToPriceObject(testString)

    val date = LocalDate.parse("2017-03-14");
    val priceObjectFromPriceClass = Price(date,843.640015,847.23999,840.799988,845.619995,779900,845.619995)
    assert(priceObjectFromPriceClass == priceObjectFromConvertLine)
  }


  test("testDailyPrices") {
    val today = java.time.LocalDate.now()

    val prices = priceGenerator.dailyPrices(today, "abc", 2)
    val closePrices = prices.map(_.close).mkString(",")
    assert(prices.length == 3)
    assert(closePrices == "847.200012,845.619995,845.539978")
  }

}
