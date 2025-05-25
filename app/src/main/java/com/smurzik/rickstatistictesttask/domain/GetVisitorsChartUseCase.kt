package com.smurzik.rickstatistictesttask.domain

import com.smurzik.rickstatistictesttask.domain.models.SortType
import com.smurzik.rickstatistictesttask.domain.models.SortTypeVisitors
import com.smurzik.rickstatistictesttask.domain.models.StatisticModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GetVisitorsChartUseCase(
    private val repository: StatisticRepository
) {

    suspend operator fun invoke(sortType: SortType): Map<String, Int> {

        // assume that current date is 11.09.2024
        val date = LocalDate.of(2024, 9, 11)

        val views = repository.getStatistic().filter { it.type == "view" }

        return when (sortType as SortTypeVisitors) {
            SortTypeVisitors.DAYS -> visitorsByDays(views, date)
            SortTypeVisitors.WEEKS -> visitorsByWeeks(views, date)
            SortTypeVisitors.MONTHS -> visitorsByMonths(views, date)
        }
    }

    private fun visitorsByDays(
        views: List<StatisticModel>,
        currentDate: LocalDate
    ): Map<String, Int> {
        val weekDates = mutableSetOf<String>()
        var tempDate = currentDate
        val datesMap = mutableMapOf<String, Int>()
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        val outputFormatter = DateTimeFormatter.ofPattern("dd.MM")

        repeat(7) {
            val stringDate = tempDate.format(formatter)
            weekDates.add(if (stringDate.startsWith('0')) stringDate.drop(1) else stringDate)
            tempDate = tempDate.minusDays(1)
            datesMap[stringDate] = 0
        }

        views.forEach { statistic ->
            statistic.dates.forEach { statisticDate ->
                if (statisticDate.toString() in weekDates) {
                    val formatDate =
                        if (statisticDate.toString().length == 7) "0$statisticDate" else statisticDate.toString()
                    val count = datesMap.getOrDefault(formatDate, 0)
                    datesMap[formatDate] = count + 1
                }
            }
        }

        val result = mutableMapOf<String, Int>()
        datesMap.forEach { (k, v) ->
            result[LocalDate.parse(k, formatter).format(outputFormatter)] = v
        }

        return result.toSortedMap()
    }

    private fun visitorsByWeeks(
        views: List<StatisticModel>,
        currentDate: LocalDate
    ): Map<String, Int> {
        val dates = mutableListOf<String>()
        var tempDate = currentDate
        val weeksMap = mutableMapOf<String, Int>()
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        views.forEach { view -> dates.addAll(view.dates.map { if (it.toString().length == 7) "0$it" else it.toString() }) }

        var weekNumber = 7

        repeat(7) {
            var count = 0
            val currentWeekDays = mutableSetOf<String>()

            repeat(7) {
                currentWeekDays.add(tempDate.format(formatter))
                tempDate = tempDate.minusDays(1)
            }

            dates.forEach {
                if (it in currentWeekDays) count++
            }

            weeksMap[weekNumber.toString()] = count
            weekNumber--
        }

        return weeksMap.toSortedMap()
    }

    private fun visitorsByMonths(
        views: List<StatisticModel>,
        currentDate: LocalDate
    ): Map<String, Int> {
        val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        val dates = mutableListOf<LocalDate>()
        val monthsMap = mutableMapOf<String, Int>()
        views.forEach { view ->
            dates.addAll(view.dates.map {
                if (it.toString().length == 7) LocalDate.parse("0$it", formatter)
                else LocalDate.parse(it.toString(), formatter)
            })
        }
        var currentMonth = currentDate.month
        repeat(7) {
            var count = 0
            dates.forEach {
                if (it.month == currentMonth) count++
            }
            monthsMap[currentMonth.toString().filterIndexed { index, _ -> index <= 2 }] = count
            currentMonth = currentMonth.minus(1)
        }

        val result = mutableMapOf<String, Int>()
        monthsMap.keys.reversed().forEach {
            result[it] = monthsMap[it]!!
        }

        return result
    }
}