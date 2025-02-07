fun main() {
    println("\u001B[35mWould you like to play against AI? (y/n)\u001B[0m")
    val response = readLine()
    val playAgainstAI = response.equals("y", ignoreCase = true)

    val game = Game(isAIEnabled = playAgainstAI)
    game.play()
}
