package com.stewsters.lordOfFlame.maths

import com.stewsters.lordOfFlame.game.Facing
import org.hexworks.mixite.core.api.CubeCoordinate
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


// Some coordinate math
fun CubeCoordinate.plus(facing: Facing): CubeCoordinate {
    return this.plus(facing.offset)
}

operator fun CubeCoordinate.plus(other: CubeCoordinate): CubeCoordinate {
    return CubeCoordinate.fromCoordinates(this.gridX + other.gridX, this.gridZ + other.gridZ)
}

operator fun CubeCoordinate.minus(other: CubeCoordinate): CubeCoordinate {
    return CubeCoordinate.fromCoordinates(this.gridX - other.gridX, this.gridZ - other.gridZ)
}

fun CubeCoordinate.distanceTo(other: CubeCoordinate): Int {
    val vec = this - other
    return (abs(vec.gridX) + abs(vec.gridY) + abs(vec.gridZ)) / 2
}

fun CubeCoordinate.scale(scale: Int): CubeCoordinate {
    return CubeCoordinate.fromCoordinates(gridX * scale, gridZ * scale)
}

fun CubeCoordinate.add(
    other: CubeCoordinate,
): CubeCoordinate {
    return CubeCoordinate.fromCoordinates(this.gridX + other.gridX, this.gridZ + other.gridZ)
}


fun CubeCoordinate.getNeighbors(): List<CubeCoordinate> {

    return listOf(
        CubeCoordinate.fromCoordinates(gridX + 1, gridZ - 1),
        CubeCoordinate.fromCoordinates(gridX + 1, gridZ),
        CubeCoordinate.fromCoordinates(gridX, gridZ + 1),
        CubeCoordinate.fromCoordinates(gridX - 1, gridZ + 1),
        CubeCoordinate.fromCoordinates(gridX - 1, gridZ),
        CubeCoordinate.fromCoordinates(gridX, gridZ - 1)
    )
}

fun CubeCoordinate.directionTo(target: CubeCoordinate): Facing? {
    if (this == target) return null

    // For hex grids, the direction is the normalized vector.
    // However, since we can be many tiles away, we find which neighbor
    // reduces the distance the most.
    return Facing.entries.minByOrNull { facing ->
        this.plus(facing).distanceTo(target)
    }
}

fun CubeCoordinate.toCoord(): String =
    "${gridX},${gridY},${gridZ}"


fun calculateRingFrom(center: CubeCoordinate, radius: Int): Set<CubeCoordinate> {
    var results = mutableSetOf<CubeCoordinate>()

    var hex = center.add(Facing.SOUTHWEST.offset.scale(radius))
    for (i in (0 until 6)) {
        for (j in (0 until radius)) {
            results.add(hex)
            hex = hex.add(Facing.entries.get(i).offset)
        }
    }
    return results.toSet()
}

fun calculateAllTilesInRangeFrom(center: CubeCoordinate, radius: Int): Set<CubeCoordinate> {
    var results = mutableSetOf<CubeCoordinate>()
    for (q in (-radius..radius)) {
        for (r in (max(-radius, -q - radius)..min(radius, -q + radius))) {
//                    var s = -q-r
            results.add(center.add(CubeCoordinate.fromCoordinates(q, r)))
        }
    }
    return results.toSet()
}