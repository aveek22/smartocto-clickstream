import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.propspec.AnyPropSpec
import org.scalatest.refspec.RefSpec

object ScalaTestingStyles

class CalculatorSuite extends AnyFunSuite {

    val calculator = new Calculator

    test("Multiply by zero should be zero"){
        assert(calculator.multiply(7, 0) == 0)
        assert(calculator.multiply(3, 0) == 0)
    }

    test("Dividing by zero should throw math error"){
        assertThrows[ArithmeticException](calculator.divide(8, 0))
    }
}

// Test behaviours (Behavior Driven Development - BDD)
class CalculatorSpec extends AnyFunSpec {

    val calculator = new Calculator

    describe("Multiplication") {
        describe("Some other test") {
            it("should give back zero if multiplied by zero") {
                assert(calculator.multiply(7, 0) == 0)
            }
        }
    }

    describe("Division") {
        it("Should throw math error when divided by zero"){
            assertThrows[ArithmeticException](calculator.divide(8, 0))
        }
    }
}


class CalculatorWordSpec extends AnyWordSpec{
    val calculator = new Calculator

    "A calculator" should {
        "give back zero if multiplied by zero" in {
            assert(calculator.multiply(7, 0) == 0)
            assert(calculator.multiply(3, 0) == 0)
        }

        "throw a math error when divided by zero" in {
            assertThrows[ArithmeticException](calculator.divide(8, 0))
        }
    }
}

// This is the most flexible testing style in Scala
// Notice how should is replaced by -
class  CalculatorFreeSpec extends AnyFreeSpec{
    val calculator = new Calculator

    "A calculator" - {
        "give back zero if multiplied by zero" in {
            assert(calculator.multiply(7, 0) == 0)
            assert(calculator.multiply(3, 0) == 0)
        }

        "throw a math error when divided by zero" in {
            assertThrows[ArithmeticException](calculator.divide(8, 0))
        }
    }
}


// Property style checking
class CalculatorPropSpec extends AnyPropSpec{
    val calculator = new Calculator

    val multiplyByZeroExamples = List((1231,0),(-2323,0),(0,0))

    property("Calculator multiply by zero should be zero"){
        assert(multiplyByZeroExamples.forall{
            case (a , b) => calculator.multiply(a , b) == 0
        })
    }

    property("Calculator divide by zero should throw Math error"){
        assertThrows[ArithmeticException](calculator.divide(32432 , 0))
    }
}


class CalculatorRefSpec extends RefSpec { // Based on reflection to generate some test cases

    object `A calculator` {

        val calculator = new Calculator

        def `multiply by 0 should be 0` : Unit = {
            assert(calculator.multiply(7, 0) == 0)
            assert(calculator.multiply(3, 0) == 0)
        }

        def `divde by 0 should throw math error` : Unit = {
            assertThrows[ArithmeticException](calculator.divide(32432 , 0))
        }
    }
}


class Calculator {
    
    def add (a : Int, b : Int) : Int = a + b

    def subtract (a : Int, b : Int) : Int = a - b
    
    def multiply (a : Int, b : Int) : Int = a * b
    
    def divide (a : Int, b : Int) : Int = a / b

}