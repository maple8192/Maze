package io.github.maple8192.maze

import java.awt.Point
import java.util.concurrent.CopyOnWriteArrayList

class MazeGenerator(private val width: Int, private val height: Int) {
    private val nodes = mutableListOf<Point>()
    private val path = mutableListOf<Point>()

    var field = Array(width) { Array(height) { 0 } }

    init {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    field[x][y] = 1
                } else if (x % 2 == 0 && y % 2 == 0) {
                    nodes.add(Point(x, y))
                    field[x][y] = 1
                }
            }
        }

        makeMaze()
    }

    fun run() {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (field[x][y] == 1) {
                    print("@@")
                } else if (field[x][y] == 0) {
                    print("  ")
                } else {
                    print("??")
                }
            }
            println()
        }
    }

    private fun makeMaze() {
        nodes.shuffle()
        while (nodes.size > 0) {
            path.add(nodes[0])
            nodes.removeAt(0)
            while (true) {
                val here = path.last()

                val movableDir = getCanMoveDirections(here)
                if (movableDir.size <= 0) {
                    nodes.add(here)
                    path.removeLast()
                    continue
                }

                movableDir.shuffle()

                val moveDir = movableDir[0]
                val nextPoint = Point(here.x + moveDir.dx, here.y + moveDir.dy)
                if (nodes.contains(nextPoint)) {
                    nodes.remove(nextPoint)
                    path.add(nextPoint)
                } else {
                    path.add(nextPoint)
                    for(i in 0 until path.size - 1) {
                        val current = path[i]
                        val next = path[i + 1]
                        field[(current.x + next.x) / 2][(current.y + next.y) / 2] = 1
                    }
                    path.clear()
                    break
                }
            }
        }
    }

    private fun getMoveDirections(): CopyOnWriteArrayList<MoveDirection> {
        return CopyOnWriteArrayList(MoveDirection.values())
    }

    private fun getCanMoveDirections(point: Point): CopyOnWriteArrayList<MoveDirection> {
        val directions = getMoveDirections()
        for (dir in directions) {
            if (path.contains(Point(point.x + dir.dx, point.y + dir.dy))) {
                directions.remove(dir)
            }
        }

        return directions
    }
}