package configdata

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * Created by munish on 15/03/2017.
  */
class YahooConfigDataTest extends FunSuite with BeforeAndAfterEach with MockitoSugar {
  var yahoo: YahooConfigData = null

  override def beforeEach(): Unit = {
    yahoo = new YahooConfigData("properties")
  }

  override def afterEach(): Unit = {
    yahoo = null
  }

  test("testGetExtraParams") {
    val s = yahoo.getExtraParams()
    assert(!s.equals(""))
  }

  test("testGetURLParams") {
    val s = yahoo.getURLParams()
    assert(!s.equals(""))
  }

  test("testGetTimeout") {
    val timeout = yahoo.getTimeout
    assert(timeout > 0 )
  }
}
