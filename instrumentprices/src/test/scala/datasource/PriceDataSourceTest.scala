package datasource

import configdata.ConfigData
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

/**
  * Created by munish on 15/03/2017.
  */
class PriceDataSourceTest extends FunSuite with BeforeAndAfterEach with MockitoSugar {

  val configData = mock[ConfigData]
  var priceDataSource: PriceDataSource = null

  override def beforeEach() {
    priceDataSource = new PriceDataSource(configData);
    when(configData.getURLParams()).thenReturn("URLParams")
    when(configData.getExtraParams()).thenReturn("ExtraParams")
    when(configData.getTimeout).thenReturn(2)
  }

  override def afterEach() {
    priceDataSource = null
  }

  test("testGetTimeout") {
    val timeout = priceDataSource.getTimout()
    assert(timeout == 2)
  }

  test("testGetDataSourceURL") {
    val today = java.time.LocalDate.now()

    val urlString = priceDataSource.getDataSourceURL(today, "abc", 2)
    assert(urlString.contains("URLParams"))
    assert(urlString.contains("ExtraParams"))
  }

}
