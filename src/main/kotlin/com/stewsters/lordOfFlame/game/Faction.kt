package com.stewsters.com.stewsters.lordOfFlame.game

import java.awt.Color

enum class Faction(val displayName: String, val color: Color) {

    PLAYER("Player", Color.CYAN),
    ENEMY("Enemy", Color.MAGENTA)
}