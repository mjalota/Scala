package eventgame

import org.scalatest.{BeforeAndAfterEach, FunSuite}

/**
  * Created by munish on 22/03/2017.
  */
class EventProcessorTest extends FunSuite with BeforeAndAfterEach {
  var eventProcessor: EventProcessor = null


  override def beforeEach() {
    eventProcessor = new EventProcessor()
  }

  override def afterEach() {
  eventProcessor = null
  }

  test("testGetWinnerWithEmptyArray") {
    val input = Array[Int]()
    val winner = intercept[Exception] { eventProcessor.getWinner(input)}
    assert(winner.getMessage == "Empty Array passed")
  }

  test("testGetWinner") {
    val input =  Array(1, 2, 3, 4, 4)
    val winner = eventProcessor.getWinner(input)
    assert(winner == 4)
  }

  test("testGetWinnerWhenAllAreUnique") {
    val input =  Array(1, 2, 3, 4, 5)
    val winner = eventProcessor.getWinner(input)
    assert(winner == 1)
  }

  test("testGetWinnerWhen2Winners") {
    val input =  Array(1, 2, 3, 4, 4, 5, 6,7, 5)
    val winner = eventProcessor.getWinner(input)
    assert(winner == 4)
  }

  test("testGetWinnerWhen3Winners") {
    val input =  Array(4, 4, 6, 6, 6, 3, 3, 3, 2, 2, 2)
    val winner = eventProcessor.getWinner(input)
    assert(winner == 6)
  }

  test("testGetWinnerWhenOnly1TypeOfEvent") {
    val input =  Array(4, 4, 4, 4, 4, 4)
    val winner = eventProcessor.getWinner(input)
    assert(winner == 4)
  }

}
