class Task(val name: String)

object Manager {
    var solvedTask: Int = 2
    fun solveTask(nam: Task) {
        println("Task ${nam.name} solved!")
        solvedTask++
    }
}