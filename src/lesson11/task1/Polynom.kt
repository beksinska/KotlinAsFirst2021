@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1

import kotlin.math.pow

/**
 * Класс "полином с вещественными коэффициентами".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 16.
 * Объект класса -- полином от одной переменной (x) вида 7x^4+3x^3-6x^2+x-8.
 * Количество слагаемых неограничено.
 *
 * Полиномы можно складывать -- (x^2+3x+2) + (x^3-2x^2-x+4) = x^3-x^2+2x+6,
 * вычитать -- (x^3-2x^2-x+4) - (x^2+3x+2) = x^3-3x^2-4x+2,
 * умножать -- (x^2+3x+2) * (x^3-2x^2-x+4) = x^5+x^4-5x^3-3x^2+10x+8,
 * делить с остатком -- (x^3-2x^2-x+4) / (x^2+3x+2) = x-5, остаток 12x+16
 * вычислять значение при заданном x: при x=5 (x^2+3x+2) = 42.
 *
 * В конструктор полинома передаются его коэффициенты, начиная со старшего.
 * Нули в середине и в конце пропускаться не должны, например: x^3+2x+1 --> Polynom(1.0, 2.0, 0.0, 1.0)
 * Старшие коэффициенты, равные нулю, игнорировать, например Polynom(0.0, 0.0, 5.0, 3.0) соответствует 5x+3
 */
class Polynom(vararg coeffs: Double) {
    private val listOfCoeffs = coeffs.toList().reversed()

    /**
     * Геттер: вернуть значение коэффициента при x^i
     */
    fun coeff(i: Int): Double = listOfCoeffs[i]

    /**
     * Расчёт значения при заданном x
     */
    fun getValue(x: Double): Double {
        var res = 0.0
        for (i in 0..listOfCoeffs.lastIndex) {
            res += listOfCoeffs[i] * x.pow(i)
        }
        return res
    }

    /**
     * Степень (максимальная степень x при ненулевом слагаемом, например 2 для x^2+x+1).
     *
     * Степень полинома с нулевыми коэффициентами считать равной 0.
     * Слагаемые с нулевыми коэффициентами игнорировать, т.е.
     * степень 0x^2+0x+2 также равна 0.
     */
    fun degree(): Int {
        var res = 0
        for (i in 0..listOfCoeffs.lastIndex) {
            if (listOfCoeffs[i] != 0.0) res = i
        }
        return res
    }

    /**
     * Сложение
     */
    operator fun plus(other: Polynom): Polynom {
        val res = mutableListOf<Double>()
        val min = minOf(other.degree(), listOfCoeffs.lastIndex)
        for (i in 0..min) {
            res += other.coeff(i) + listOfCoeffs[i]
        }
        if (min == listOfCoeffs.lastIndex) {
            for (i in listOfCoeffs.lastIndex + 1..other.degree())
                res += other.coeff(i)
        } else {
            for (i in other.degree() + 1..listOfCoeffs.lastIndex)
                res += listOfCoeffs[i]
        }
        return Polynom(*res.reversed().toDoubleArray())
    }


    /**
     * Смена знака (при всех слагаемых)
     */
    operator fun unaryMinus(): Polynom {
        var res = doubleArrayOf()
        listOfCoeffs.reversed().forEach { item ->
            res += item * (-1)
        }
        return Polynom(*res)
    }

    /**
     * Вычитание
     */
    operator fun minus(other: Polynom): Polynom = this + (-other)

    /**
     * Умножение
     */
    operator fun times(other: Polynom): Polynom {
        val coeffs = listOfCoeffs.toMutableList()
        val otherCoeffs = other.listOfCoeffs.toMutableList()
        val max = (coeffs.size - 1) + (otherCoeffs.size - 1)
        var listOfCalculations = doubleArrayOf()
        while (listOfCalculations.size != max + 1) {
            listOfCalculations += 0.0
        }
        coeffs.forEachIndexed { ind1, c1 ->
            otherCoeffs.forEachIndexed { ind2, c2 ->
                listOfCalculations[ind1 + ind2] += c1 * c2
            }
        }
        var res = doubleArrayOf()
        listOfCalculations.reversed().forEach { now ->
            res += now
        }
        return Polynom(*res)
    }

    /**
     * Деление
     *
     * Про операции деления и взятия остатка см. статью Википедии
     * "Деление многочленов столбиком". Основные свойства:
     *
     * Если A / B = C и A % B = D, то A = B * C + D и степень D меньше степени B
     */
    operator fun div(other: Polynom): Polynom {
        if (this.degree() < other.degree()) return Polynom(0.0)
        var res = mutableListOf<Double>()
        val dividend = listOfCoeffs.toDoubleArray()
        for (i in 0..this.degree() - other.degree()) {
            val degreeOfAnswer = dividend[i] / other.coeff()
            res.add(degreeOfAnswer)
            val curSubtrahend = DoubleArray(other.degree() + 1)
            for (i in 0..curSubtrahend.size) {
                curSubtrahend = other.[other.degree() - i] * curSubtrahend
            }
            res += Polynom(*answer.toDoubleArray())
            dividend -= divider * Polynom(*answer.toDoubleArray())
            i -= 1
        }
        return res
    }


    /**
     * Взятие остатка
     */
    operator fun rem(other: Polynom): Polynom = this - (other * (this / other))


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polynom

        if (listOfCoeffs != other.listOfCoeffs) return false

        return true
    }


    override fun hashCode(): Int = listOfCoeffs.hashCode()


}