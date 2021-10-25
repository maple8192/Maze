package io.github.maple8192.maze

enum class MoveDirection(val dx: Int, val dy: Int) {
    UP(0, 2),
    RIGHT(2, 0),
    DOWN(0, -2),
    LEFT(-2, 0)
}