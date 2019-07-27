package ru.holofox.anicoubs.data.provider

import ru.holofox.anicoubs.internal.enums.UnitSystem

interface UnitProvider {
    fun getUnitProvider(): UnitSystem
}