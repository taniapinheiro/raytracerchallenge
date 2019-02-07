package com.tcmpinheiro.raytracerchallenge.features



data class Matrix(var rows: Int, var columns: Int, var array: DoubleArray) {

    operator fun get(i: Int, j: Int): Double {
        return array[i * rows + j]
    }

    private val delta: Double = 0.00001

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Matrix

        if (rows != other.rows) return false
        if (columns != other.columns) return false
        if (array.size != other.array.size) return false
        if (array.zip(other.array).map { it.first - it.second < delta }.any { !it }) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rows
        result = 31 * result + columns
        result = 31 * result + array.contentHashCode()
        return result
    }

    operator fun times(other: Matrix): Matrix {
        val result = DoubleArray(rows * columns)
        for (i in 0..3) {
            for (j in 0..3){
                result[i * rows + j] = get(i, 0) * other[0, j] +
                              get(i, 1) * other[1, j] +
                              get(i, 2) * other[2, j] +
                              get(i, 3) * other[3, j]
            }
        }
        return Matrix(rows, columns, result)
    }

    operator fun times(other: Tuple): Tuple {
        val result = DoubleArray(rows)
        for (i in 0..3){
            result[i] = get(i, 0) * other.x +
                    get(i, 1) * other.y +
                    get(i, 2) * other.z +
                    get(i, 3) * other.w
        }
       return Tuple(result[0], result[1], result[2], result[3])
    }

    fun submatrix(i: Int, j: Int): Matrix {
        val result = DoubleArray((rows -1) * (columns -1))
        var index = 0
        for (a in 0 until rows) {
            for (b in 0 until columns){
                if (a != i && b != j){
                    result[index] = get(a, b)
                    index ++
                }
            }
        }
        return Matrix(rows-1, columns -1, result)
    }

    fun minor(i: Int, j: Int): Double {
        return submatrix(i,j).determinant()
    }

    fun cofactor(i: Int, j: Int): Double {
        return if ((i + j) % 2 == 0) minor(i,j) else minor(i,j) * (-1.0)
    }

    fun isInvertible(): Boolean {
        return determinant() != 0.0
    }

    fun inverse(): Matrix {
       assert(isInvertible())
        val m = identityMatrix()
        for (i in 0 until rows) {
            for (j in 0 until columns){
                val c = cofactor(i, j)
                m[j, i] = c / determinant()
            }
        }
        return m
    }

    private operator fun set(i: Int, j: Int, value: Double) {
        array[i * rows + j] = value
    }

    fun transpose(): Matrix {
        val result = array
        for (y in 0 until rows)
            for (x in y + 1 until rows){
                val a = result[x * rows + y]
                val b = result[y * rows + x]
                result[x * rows + y] = b
                result[y * rows + x] = a
            }
        return Matrix(4 ,4, result)
    }


    fun determinant(): Double {
        var det = 0.0
        if (rows == 2){
            det = array[0] * array[3] - array[1]* array[2]
        } else {
            for (i in 0 until rows) {
                det += get(0, i) * cofactor(0, i)
            }
        }
        return det
    }

}

fun identityMatrix(): Matrix{
    return Matrix(4, 4,
        doubleArrayOf(1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0))
}

