package cinema

const val DEADLINE_AMOUNT = 60
const val TICKET_PRICE_1 = 10
const val TICKET_PRICE_2 = 8

fun showMenuAndReceiveOption(): Int {
    println("""
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent())
    return when(readln().toInt()) {
        1 -> {
            println()
            1
        }
        2 -> {
            println()
            2
        }
        3 -> {
            println()
            3
        }
        else -> {
            println()
            0
        }
    }
}

fun createCinemaRoom(): MutableList<MutableList<Char>> {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val numSeats = readln().toInt()
    println()
    return MutableList(rows) { MutableList(numSeats) {'S'} }
}

fun checkPlace(chart: MutableList<MutableList<Char>>, rowNum: Int, seatNum: Int): Unit {
    if (rowNum !in 1 .. chart.size || seatNum !in 1 .. chart[0].size) {
        throw Exception("\nWrong input!\n")
    }
    if (chart[rowNum - 1][seatNum - 1] == 'B') {
        throw Exception("\nThat ticket has already been purchased!\n")
    }
    chart[rowNum - 1][seatNum - 1] = 'B'
}

fun buyTickets(chart: MutableList<MutableList<Char>>): Int {
    var rowNum: Int
    var seatNum: Int
    while (true) {
        println("Enter a row number:")
        rowNum = readln().toInt()
        println("Enter a seat number in that row:")
        seatNum = readln().toInt()
        try {
            checkPlace(chart, rowNum, seatNum)
            break
        }
        catch (e: Exception) {
            println(e.message)
        }
    }
    val income =  if (chart.size * chart[0].size <= DEADLINE_AMOUNT) TICKET_PRICE_1
    else if (rowNum <= chart.size / 2) TICKET_PRICE_1
    else TICKET_PRICE_2

    println("\nTicket price: $$income\n")
    return income
}

fun printSeats(chart: MutableList<MutableList<Char>>) {
    print("Cinema:\n  ")
    for (i in 1..chart[0].size) print("$i ")
    for (i in 1..chart.size) {
        print("\n$i ${chart[i-1].joinToString(" ")}")
    }
    print("\n".repeat(2))
}

fun printStatistics(boughtTickets: Int, amountOfSeats: Int, curIncome: Int, totalIncome: Int) {
    val formatPercentage = "%.2f".format(boughtTickets.toDouble()/amountOfSeats * 100)
    println("Number of purchased tickets: $boughtTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$curIncome")
    println("Total income: $$totalIncome\n")
}

fun main() {
    val seatingChart = createCinemaRoom()
    var boughtSeats = 0
    val amountOfSeats = seatingChart[0].size * seatingChart.size
    var curIncome = 0
    val totalIncome = if (amountOfSeats <= DEADLINE_AMOUNT) {
        amountOfSeats * TICKET_PRICE_1
    }
    else {
        (seatingChart.size / 2 * (TICKET_PRICE_1 - TICKET_PRICE_2) + TICKET_PRICE_2 * seatingChart.size) * seatingChart[0].size
    }
    while (true) {
        when (showMenuAndReceiveOption()) {
            0 -> break
            1 -> printSeats(seatingChart)
            2 -> {
                curIncome += buyTickets(seatingChart)
                boughtSeats++
            }
            3 -> printStatistics(boughtSeats, amountOfSeats, curIncome, totalIncome)
        }
    }
}