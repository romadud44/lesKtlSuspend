import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 *  Написать класс Person с полями в конструкторе name и age, класс. Создать список persons, в который включить
 *  несколько объектов этого класса. Написать класс Weather с полями city, description, temp, создать список из
 *  нескольких объектов этого класса. Создать список случайных чисел в диапазоне от 1 до 100 из 10 элементов.
 *  Представим, что эти три списка мы планируем загрузить с сервера. Необходимо написать обобщенную suspend –
 *  функцию, принимающую на вход список параметра T и в результат возвращающую скачанный этот же список. Она будет
 *  имитировать скачивание данных. Внутри функции с задержкой в 1000L выводить скачанные данные. В главной функции
 *  нужно вызвать функции загрузки трех имеющихся списков: personList, weatherList, randoms. Мы будем наблюдать,
 *  как идет процесс загрузки (элементы в функции выводятся). Главный поток при их загрузке останавливается. Когда
 *  загрузка заканчивается в консоль, выходит фраза «Данные загружены» и выводятся все три списка. В завершении
 *  выходит фраза «Программа завершена».
 */
suspend fun main() = coroutineScope {
    println("Начало работы программы")

    val personsList = listOf(
        Person("Иван", 25),
        Person("Анна", 24),
        Person("Игорь", 30),
        Person("Денис", 23),
        Person("Мария", 30),
        Person("Александр", 45),
        Person("Ольга", 29),
        Person("Михаил", 33),
        Person("Виктор", 28),
        Person("Ирина", 25)
    )

    val citiesWeatherList = listOf(
        Weather("Кострома", "облачно", 20),
        Weather("Ярославль", "ясно", 24),
        Weather("Иваново", "дождь", 17),
        Weather("Рыбинск", "ясно", 25),
        Weather("Вологда" ),
        Weather("Череповец", "облачно", 19),
        Weather("Котлас", "ясно", 24),
        Weather(),
        Weather("Дудинка", "ясно", -35),
        Weather(temp = 10)
    )

    val randomNumbersList: List<Byte> = listOf(1, 23, 29, 31, 45, 65, 87, 96, 99, 100)

    val tasks = listOf(
        launch {
            loadData(personsList)
        },
        launch {
            loadData(citiesWeatherList)
        },
        launch {
            loadData(randomNumbersList)
        }
    )
    tasks.joinAll()
    println(
        "Данные загружены:\n" +
                "$personsList\n" +
                "$citiesWeatherList\n" +
                "$randomNumbersList"
    )
    println("Программа завершена!")

}

data class Person(val name: String, val age: Byte = -1) {
    override fun toString(): String {
        return "\nИмя: $name, возраст: $age лет"
    }
}

data class Weather(val city: String = "Нет данных", val description: String = "Не определена", val temp: Byte = 0) {
    override fun toString(): String {
        return "\nПогода в городе - $city: $description, температура:$temp"
    }
}

private suspend fun <T> loadData(input: List<T>): List<T> {
    for (i in input.indices) {
        delay(1000L)
        println("Скачан: ${input[i]}")
    }
    return input
}