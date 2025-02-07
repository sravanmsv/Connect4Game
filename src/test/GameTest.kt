import kotlin.test.*

class GameTest {

    @Test
    fun testMakeValidMove() {
        val game = Game()
        assertTrue(game.makeMove(3))  // Should place a token in column 3
    }

    @Test
    fun testMakeInvalidMove() {
        val game = Game()
        assertFalse(game.makeMove(7))  // Invalid column (out of range)
        assertFalse(game.makeMove(-1)) // Invalid column (negative)
    }

    @Test
    fun testColumnFull() {
        val game = Game()
        repeat(6) { game.makeMove(0) }  // Fill column 0
        assertFalse(game.makeMove(0))   // Should return false
    }

    @Test
    fun testWinHorizontal() {
        val game = Game()
        for (col in 0..3) {
            game.makeMove(col)
            if (col < 3) game.makeMove(col)  // Alternate turns
        }
        assertTrue(game.checkWin())
    }
}
