package com.stewsters.lordOfFlame.types

import com.stewsters.lordOfFlame.TileData
import org.hexworks.mixite.core.api.Hexagon

class Critter(val name: String, val fitness: (Hexagon<TileData>) -> Double)