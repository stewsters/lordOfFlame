package com.stewsters.lordOfFlame

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.SoldierType
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BankLeftAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BankRightAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.BreathFireAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.ClimbAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.DiveAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.flight.FlyForwardAction
import com.stewsters.com.stewsters.lordOfFlame.game.ai.PlayerAi
import com.stewsters.com.stewsters.lordOfFlame.generator.populate
import com.stewsters.lordOfFlame.generator.generateMap
import com.stewsters.lordOfFlame.map.HexMap
import com.stewsters.lordOfFlame.types.TerrainType
import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.HexagonOrientation
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.HexagonalGridLayout
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PGraphics
import processing.core.PImage
import java.io.File

class DragonGame : PApplet() {


    private val radius = 40.0
    private lateinit var background: PGraphics

    private val camera = Camera()
    private lateinit var hexMap: HexMap
    var mainCharacter: Soldier? = null

    override fun settings() {
        size(1200, 800)
    }

    private fun loadTextures() {
        //  load all the images in
        TerrainType.URBAN.icons = loadImages("assets/mapParts/cities")
        TerrainType.TOWN.icons = loadImages("assets/mapParts/towns")
        TerrainType.HILL.icons = loadImages("assets/mapParts/hills")
        TerrainType.MOUNTAIN.icons = loadImages("assets/mapParts/mountains")
        TerrainType.FOREST.icons = loadImages("assets/mapParts/trees")
        TerrainType.FIELDS.icons = loadImages("assets/mapParts/fields")

        SoldierType.entries.forEach {
            it.icon = loadImage("assets/soldiers/${it.displayName}.png")
        }
    }

    override fun setup() {
        frameRate(30f)
        loadTextures()

        val widthTiles = 60
        val heightTiles = 40

        //Create grid
        val builder = HexagonalGridBuilder<TileData>()
            .setGridWidth(widthTiles)
            .setGridHeight(heightTiles)
            .setGridLayout(HexagonalGridLayout.RECTANGULAR)
            .setOrientation(HexagonOrientation.FLAT_TOP)
            .setRadius(radius)

        hexMap = HexMap(builder)
        generateMap(hexMap)

        mainCharacter = populate(hexMap)


        // set initial camera pos
        camera.position.x = -widthTiles / 2f * radius.toFloat()
        camera.position.y = -heightTiles / 2f * radius.toFloat()

        background = createGraphics(width, height)
        background.beginDraw()
        background.background(200)
        background.endDraw()

    }


    private fun loadImages(path: String): List<PImage> {
        return File(path)
            .listFiles()
            ?.map { loadImage(it.path) }
            .orEmpty()
    }


    override fun keyTyped() {

        PlayerAi.nextAction = when (key) {
            'w' -> FlyForwardAction()
            'e' -> BankRightAction()
            'q' -> BankLeftAction()
//                'd' -> TurnRightAction()
//                'a' -> TurnLeftAction()
                'x' -> DiveAction()
                's' -> ClimbAction()
                'f' -> BreathFireAction()
//                'r' -> RoarAction()
            else -> null
        }

//            val ai = mainCharacter?.ai
//        if(ai!=null && ai is PlayerAi){
//
//        }
    }

    override fun draw() {

        // Do turn
        hexMap.takeTurn()

        // handle focus on character
        val focus = mainCharacter?.pos
        if (focus != null) {
            val grid = hexMap.grid.getByCubeCoordinate(focus)
            if (grid.isPresent) {
                val hex = grid.get()
                camera.position.set(-hex.centerX.toFloat(), -hex.centerY.toFloat(), 1f)

            }

        }


        // Draw the background
        imageMode(PConstants.CORNER)
        image(background, 0f, 0f)

        push()
        // handle camera
        camera.draw(this)

        //
        val hexHighlight = camera.screenToWorld(
            this,
            hexMap.grid,
            mouseX.toDouble(),
            mouseY.toDouble()
        )

        //edges of vision
        val lower = camera.screenToWorld(this, hexMap.grid, -1.0, -1.0)
        val higher = camera.screenToWorld(this, hexMap.grid, width.toDouble() - 1, height.toDouble() - 1)

        hexMap.grid.hexagons.forEach { hex ->

            // Don't render off screen
            if (lower.isPresent && (hex.centerX < lower.get().centerX - 1 || hex.centerY < lower.get().centerY - radius)) {
                return@forEach
            }
            if (higher.isPresent && (hex.centerX > higher.get().centerX + 1 || hex.centerY > higher.get().centerY + 1)) {
                return@forEach
            }

            val satelliteData: TileData = hex.satelliteData.get()
            // Fill
            if (hexHighlight.isPresent && hexHighlight.get() == hex) {
                fill(255f, 1f, 1f)
            } else if (satelliteData.type?.color != null)
                fill(satelliteData.type!!.color)
            else
                noFill()

            // Draw the shape
            beginShape()
            hex.points.forEach {
                vertex(it.coordinateX.toFloat(), it.coordinateY.toFloat())
            }
            endShape(CLOSE)

            // Icon
            val icons = satelliteData.icons
            if (icons != null) {
                imageMode(CENTER)

                if (icons.size == 1) {
                    image(icons.first(), hex.centerX.toFloat(), hex.centerY.toFloat())
                } else {
                    // need a seeded location?
                    val cnt = icons.size
                    icons.forEachIndexed { index, it ->
                        val angle = 2 * PI / cnt * index
                        image(
                            it,
                            hex.centerX.toFloat() + radius.toFloat() / 2f * cos(angle),
                            hex.centerY.toFloat() + radius.toFloat() / 2f * sin(angle)
                        )
                    }

                }

            }
            // sort by height
            satelliteData.soldiers.forEach { soldier ->
                pushMatrix();
                translate(hex.centerX.toFloat(), hex.centerY.toFloat())
                rotate(radians(soldier.facing.angle()));
                tint(soldier.faction.color.rgb);
                image(soldier.soldierType.icon, 0f, 0f)
                popMatrix()
            }

            fill(0xff000000.toInt())
            textAlign(CENTER)
            // Coordinates
            text(hex.cubeCoordinate.toCoord(), hex.centerX.toFloat(), hex.centerY.toFloat() + 30f)

            if (satelliteData.tileTitle != null) {
                text(satelliteData.tileTitle, hex.centerX.toFloat(), hex.centerY.toFloat() - 25f)
            }

        }

        // Draw Roads
        strokeWeight(8f)
        // draw water
        stroke(TerrainType.DEEP_WATER.color.toInt())
        hexMap.rivers.forEach {
            line(
                it.first.centerX.toFloat(), it.first.centerY.toFloat(),
                it.second.centerX.toFloat(), it.second.centerY.toFloat()
            )
        }

        stroke(0x88DAA06D.toInt())

        hexMap.roads.forEach {
            line(
                it.first.centerX.toFloat(), it.first.centerY.toFloat(),
                it.second.centerX.toFloat(), it.second.centerY.toFloat()
            )
        }
        stroke(1f)
        strokeWeight(1f)

        color(0)
        pop()
    }

}

private fun CubeCoordinate.toCoord(): String =
    "${gridX},${gridY},${gridZ}"

