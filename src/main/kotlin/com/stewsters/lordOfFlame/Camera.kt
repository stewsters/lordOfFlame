package com.stewsters.lordOfFlame

import org.hexworks.mixite.core.api.Hexagon
import org.hexworks.mixite.core.api.HexagonalGrid
import org.hexworks.mixite.core.vendor.Maybe
import processing.core.PApplet
import processing.core.PVector


class Camera(
    val position: PVector = PVector(10f, 10f, 1f),
    var scrollSpeed: Float = 10.0f,
    var zoomSpeed: Float = 0.01f,
) {

    // TODO: zoom to target
//    fun track(entity) {
//        this.position.x = (-entity.position.x) * this.position.z + (width / 2);
//        this.position.y = (-entity.position.y) * this.position.z + (height / 2);
//    }

    // Translate & scale based on camera position and zoom
    fun draw(cntx: PApplet) {
        // Update
//        if (cntx.keyPressed) {
//            when (cntx.key) {
//                'a' -> position.x += scrollSpeed
//                'd' -> position.x -= scrollSpeed
//                'w' -> position.y += scrollSpeed
//                's' -> position.y -= scrollSpeed
//
//                'e' -> position.z -= zoomSpeed
//                'q' -> position.z += zoomSpeed
//            }
//        }
        // TODO: follow

        // Draw
        cntx.scale(position.z)
        cntx.translate(
            position.x + cntx.width / 2f,
            position.y + cntx.height / 2f
        )
    }

    fun screenToWorld(cntx: PApplet, grid: HexagonalGrid<TileData>, x: Double, y: Double): Maybe<Hexagon<TileData>> {
        val screenX = x / position.z - position.x - cntx.width / 2f
        val screenY = y / position.z - position.y - cntx.height / 2f
        return grid.getByPixelCoordinate(screenX, screenY)
    }

//    fun zoomToGrid(coordinate: CubeCoordinate?) {
//
//    }

}