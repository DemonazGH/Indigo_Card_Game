val notPredicate: (Char) -> Boolean = {
    c: Char -> !originalPredicate(c)
}
