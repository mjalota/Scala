package configdata

import com.typesafe.config.ConfigFactory

/**
  * Created by munish on 15/03/2017.
  */


trait ConfigData {
  def getURLParams(): String
  def getExtraParams(): String
  var getTimeout = 0
}


class YahooConfigData(val filename: String) extends ConfigData {

  def getURLParams(): String = {
    val URLParam = ConfigFactory.load(filename).getString("PROPERTIES.YAHOO.URLValue")
    URLParam
  }

  def getExtraParams(): String = {
    val extraParams = ConfigFactory.load(filename).getString("PROPERTIES.YAHOO.extraParams")
    extraParams
  }

  getTimeout = ConfigFactory.load(filename).getInt("PROPERTIES.YAHOO.timeOut")
}