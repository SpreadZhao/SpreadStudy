package basic

fun main() {
    var a: String? = "a"
    a.let {
        a = null
        println("a: ${a.toString()}")
        println("it: ${it.toString()}")
    }
    var b: String? = "b"
    b.apply {
        b = null
        println("b: ${b.toString()}")
        println("this: ${this.toString()}")
    }
}
