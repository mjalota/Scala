package datasource

import java.time.LocalDate
import configdata.ConfigData

/**
  * Created by munish on 15/03/2017.
  */
trait DataSource {
  def getDataSourceURL(businessDate: LocalDate, ticker: String, days: Long): String
  def getTimout(): Long
}


class PriceDataSource(private var configData: ConfigData) extends DataSource {

  def getDataSourceURL(businessDate: java.time.LocalDate, ticker: String, days: Long): String = {
    val histDate = businessDate.minusDays(days)
    val histURL = f"&a=${histDate.getMonthValue - 1}&b=${histDate.getDayOfMonth}&c=${histDate.getYear}"
    val busineesDateURL = f"&d=${businessDate.getMonthValue - 1}&e=${businessDate.getDayOfMonth}&f=${businessDate.getYear}"

    val URLParam = configData.getURLParams()
    val extraParams = configData.getExtraParams()

    val URL = URLParam + f"$ticker" + f"$histURL" + f"$busineesDateURL" + f"$extraParams"
    URL
  }

  def getTimout(): Long = {
    configData.getTimeout
  }
}