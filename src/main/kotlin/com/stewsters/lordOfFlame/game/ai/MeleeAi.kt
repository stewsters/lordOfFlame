package com.stewsters.com.stewsters.lordOfFlame.game.ai

import com.stewsters.com.stewsters.lordOfFlame.game.Soldier
import com.stewsters.com.stewsters.lordOfFlame.game.action.Action
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.TurnInPlaceAction
import com.stewsters.com.stewsters.lordOfFlame.game.action.walk.WalkForwardAction
import com.stewsters.lordOfFlame.map.HexMap
import kotlin.random.Random

class MeleeAi: Ai {

    override fun getAction(
        soldier: Soldier,
        hexMap: HexMap
    ): Action? {


       return when(Random.nextInt(3)) {
            1-> TurnInPlaceAction(true)
            2-> TurnInPlaceAction(false)
            else -> WalkForwardAction()
        }
    }

}