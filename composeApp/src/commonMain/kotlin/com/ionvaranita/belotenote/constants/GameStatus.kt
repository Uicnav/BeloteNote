package com.ionvaranita.belotenote.constants


enum class GameStatus(val id: Byte) {
    TO_START(0),
    FINISHED(1),
    CONTINUE(2),
    EXTENDED(3),
    EXTENDED_MANDATORY(4);

    companion object {
        private val map = entries.associateBy(GameStatus::id)
        fun fromId(id: Byte): GameStatus? = map[id]
    }
}
