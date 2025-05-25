package com.smurzik.rickstatistictesttask.domain.models

interface SortType {
    val label: String
}

enum class SortTypeVisitors(private val type: String) : SortType {

    DAYS("По дням"), WEEKS("По неделям"), MONTHS("По месяцам");

    override val label: String
        get() = this.type
}

enum class SortTypeAge(private val type: String) : SortType {
    TODAY("Сегодня"), WEEK("Неделя"), MONTH("Месяц"), ALL_TIME("Все время");

    override val label: String
        get() = this.type
}