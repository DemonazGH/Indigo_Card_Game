fun main() {
    try {
        var t = 2 / 0
    } catch (e: RuntimeException) {
        println("Well")
    } catch (e: Exception) {
        println("Wrong")
    }
}
