class Block(val color: String) {
    object BlockProperties {
        var length: Int = 6
        var width = 4

        fun blocksInBox(boxLength: Int = 14, boxWidth: Int = 9): Int {
            return boxLength / length * (boxWidth / width)
        }

    }
}
