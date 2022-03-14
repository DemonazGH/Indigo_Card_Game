class ChristmasTree(var color: String) {

    fun putTreeTopper(color: String) {
        val t = TreeTopper(color)
        t.sparkle()
    }

    inner class TreeTopper(var color: String) {
        fun sparkle() {
            println("The sparkling $color tree topper looks stunning on the ${this@ChristmasTree.color} Christmas tree!")
        }
    }
}
