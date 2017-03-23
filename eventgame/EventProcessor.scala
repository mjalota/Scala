package eventgame

import scala.collection.mutable.LinkedHashMap
/**
  * Created by munish on 22/03/2017.
  */
class EventProcessor {

  def getWinner(events: Array[Int]) : Int = {
    if (events.length == 0 )
      throw new RuntimeException("Empty Array passed")

    var cache =  LinkedHashMap[Int, Int]()
    events.foreach(x => {
      cache(x) = cache.getOrElse(x, 0) + 1
    })

    val maxValue = cache.values.max
    val winner =  cache.find(_._2 == maxValue).get._1
    winner
  }
}
