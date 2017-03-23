package mainprog

import datasource.PriceDataSource
import configdata.YahooConfigData
import pricegenerator.PriceGeneratorImpl

/**
  * Created by munish on 15/03/2017.
  */
object mainprog extends App {
  val now = java.time.LocalDate.now()
  val yahooConfigData = new YahooConfigData("properties");
  val yahooDataSource = new PriceDataSource(yahooConfigData);

  val yahooPriceGenerator = new PriceGeneratorImpl(yahooDataSource)

  val googleDailyPrices = yahooPriceGenerator.dailyPrices(now, "GOOG", 3)
  val googleDailyReturns = yahooPriceGenerator.returns(now, "GOOG", 2)
  val googleAverageReturns =yahooPriceGenerator.meanReturn("GOOG", 2)
}
