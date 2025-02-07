import kotlin.random.Random

enum class CellState(val symbol: String) {
    EMPTY(" "),
    PLAYER_X("\u001B[31mX\u001B[0m"), // Red X
    PLAYER_O("\u001B[34mO\u001B[0m"); // Blue O

    override fun toString(): String = symbol
}

class Game(private val isAIEnabled: Boolean = true) {
    private val board: Array<Array<CellState>> = Array(6) { Array(7) { CellState.EMPTY } }
    private var currentPlayer: CellState = CellState.PLAYER_X

    fun play() {
        while (true) {
            displayBoard()

            val column = if (isAIEnabled && currentPlayer == CellState.PLAYER_O) {
                getAIMove() // AI's move
            } else {
                getPlayerMove()
            }

            if (!makeMove(column)) {
                println("\u001B[31m⚠ Column $column is full! Try another column.\u001B[0m")
                continue
            }

            if (checkWin()) {
                displayBoard()
                println("\u001B[32m🎉 Player ${currentPlayer.symbol} wins! 🎉\u001B[0m")
                break
            }

            if (isBoardFull()) {
                displayBoard()
                println("\u001B[33m🤝 It's a draw! 🤝\u001B[0m")
                break
            }

            switchPlayer()
        }

        println("\u001B[35mDo you want to play again? (y/n)\u001B[0m")
        val response = readLine()
        if (response.equals("y", ignoreCase = true)) {
            resetGame()
            play()
        }
    }

    private fun getPlayerMove(): Int {
        while (true) {
            print("\u001B[33mPlayer ${currentPlayer.symbol}, choose a column (0-6): \u001B[0m")
            val column = readLine()?.toIntOrNull()
            if (column != null && column in 0..6) return column
            println("\u001B[31m⚠ Invalid input! Enter a number between 0 and 6.\u001B[0m")
        }
    }

    private fun getAIMove(): Int {
        println("\u001B[35m🤖 AI is thinking...\u001B[0m")
        Thread.sleep(1000) // Simulate thinking time
        val availableColumns = (0..6).filter { board[0][it] == CellState.EMPTY }
        return availableColumns.random()
    }

    fun displayBoard() {
        println("\n\u001B[36mCurrent Board:\u001B[0m\n")
        for (row in board) {
            print("|")
            for (cell in row) {
                print(" ${cell.symbol} |")
            }
            println()
        }
        println("\u001B[36m-----------------------------\u001B[0m")
        println("  0   1   2   3   4   5   6  ")
    }

    fun makeMove(column: Int): Boolean {
        for (row in 5 downTo 0) {
            if (board[row][column] == CellState.EMPTY) {
                animateMove(row, column)
                board[row][column] = currentPlayer
                return true
            }
        }
        return false
    }

    private fun animateMove(row: Int, column: Int) {
        for (i in 0..row) {
            board[i][column] = currentPlayer
            displayBoard()
            Thread.sleep(150)
            board[i][column] = CellState.EMPTY
        }
        board[row][column] = currentPlayer
    }

    fun checkWin(): Boolean {
        return checkHorizontalWin() || checkVerticalWin() || checkDiagonalWin()
    }

    private fun checkHorizontalWin(): Boolean {
        for (row in board) {
            for (col in 0..3) {
                if (row[col] != CellState.EMPTY &&
                    row[col] == row[col + 1] &&
                    row[col] == row[col + 2] &&
                    row[col] == row[col + 3]) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkVerticalWin(): Boolean {
        for (col in 0..6) {
            for (row in 0..2) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row + 1][col] &&
                    board[row][col] == board[row + 2][col] &&
                    board[row][col] == board[row + 3][col]) {
                    return true
                }
            }
        }
        return false
    }

    private fun checkDiagonalWin(): Boolean {
        for (row in 0..2) {
            for (col in 0..3) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row + 1][col + 1] &&
                    board[row][col] == board[row + 2][col + 2] &&
                    board[row][col] == board[row + 3][col + 3]) {
                    return true
                }
            }
        }
        for (row in 3..5) {
            for (col in 0..3) {
                if (board[row][col] != CellState.EMPTY &&
                    board[row][col] == board[row - 1][col + 1] &&
                    board[row][col] == board[row - 2][col + 2] &&
                    board[row][col] == board[row - 3][col + 3]) {
                    return true
                }
            }
        }
        return false
    }

    fun isBoardFull(): Boolean {
        return board.all { row -> row.all { it != CellState.EMPTY } }
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == CellState.PLAYER_X) CellState.PLAYER_O else CellState.PLAYER_X
    }

    fun resetGame() {
        for (row in board.indices) {
            for (col in board[row].indices) {
                board[row][col] = CellState.EMPTY
            }
        }
        currentPlayer = CellState.PLAYER_X
    }
}

