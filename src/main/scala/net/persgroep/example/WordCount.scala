//package net.persgroep.example
//
//import org.apache.flink.api.scala.ExecutionEnvironment
//
///**
// * Implements the "WordCount" program that computes a simple word occurrence histogram
// * over some sample data
// *
// * This example shows how to:
// *
// *   - write a simple Flink program.
// *   - use Tuple data types.
// *   - write and use user-defined functions.
// */
//object WordCount {
//  def main(args: Array[String]): Unit = {
//
//    // set up the execution environment
//    val env = ExecutionEnvironment.getExecutionEnvironment
//
//    // get input data
//    val text = env.fromElements("To be, or not to be,--that is the question:--",
//      "Whether 'tis nobler in the mind to suffer", "The slings and arrows of outrageous fortune",
//      "Or to take arms against a sea of troubles,")
//
//    val counts = text.flatMap {
//      _.toLowerCase.split("\\W+")
//    }
//      .map {
//        (_, 1)
//      }
//      .groupBy(0)
//      .sum(1)
//
//    // execute and print result
//    counts.print()
//
//  }
//}
