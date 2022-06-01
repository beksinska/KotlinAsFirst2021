@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { null }

    /**
     * Число элементов в хеш-таблице
     */
    val size: Int
        get() {
            for (i in 0 until capacity) {
                when {
                    elements[i] == null -> {
                        return i
                    }
                }
            }
            return capacity
        }


    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */

    fun add(element: T): Boolean {
        for (i in 0 until capacity) when {
            elements[i] == element -> {
                return false
            }
            elements[i] == null -> {
                elements[i] = element
                return true
            }
        }
        return false
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean {
        for (i in 0 until capacity) when {
            elements[i] == element -> {
                return true
            }
            elements[i] == null -> {
                return false
            }
        }
        return false
    }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if ((other !is OpenHashSet<*>) || (size != other.size)) {
            return false
        }
        for (i in 0 until size) {
            when {
                elements[i] != other.elements[i] -> {
                    return false
                }
            }

        }
        return true
    }

    override fun hashCode(): Int {
        var result = 0
        for (i in elements) {
            if (i != null) {
                result += i.hashCode()
            }
        }
        return result
    }
}