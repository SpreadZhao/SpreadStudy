package basic

class InitTest {

    private var a = 1

    init {
        test()
    }

    private fun test() {
        println("a: $a")
    }


}

fun main() {
    val test = InitTest()
}