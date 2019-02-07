package com.tcmpinheiro.raytracerchallenge.features

import org.junit.Assert.*
import org.junit.Test

class MatrixTest{

    val delta = 0.00001

    /**
     * Scenario: Constructing and inspecting a 4x4 matrix
     * Given the following 4x4 matrix M:
     * |1|2|3|4|
     * | 5.5| 6.5| 7.5| 8.5|
     * | 9 | 10 | 11 | 12 |
     * | 13.5 | 14.5 | 15.5 | 16.5 |
     * Then M[0,0] = 1
     * And M[0,3] = 4
     * And M[1,0] = 5.5
     * And M[1,2] = 7.5
     * And M[2,2] = 11
     * And M[3,0] = 13.5
     * And M[3,2] = 15.5
     */
    @Test
    fun matrixConstruction() {
        val m = Matrix(4, 4,
            doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.5, 6.5, 7.5, 8.5, 9.0, 10.0, 11.0, 12.0, 13.5, 14.5, 15.5, 16.5))
        assertEquals(1.0, m[0, 0], delta)
        assertEquals(4.0, m[0, 3], delta)
        assertEquals(5.5, m[1, 0], delta)
        assertEquals(7.5, m[1, 2], delta)
        assertEquals(11.0, m[2, 2], delta)
        assertEquals(13.5, m[3, 0], delta)
        assertEquals(15.5, m[3, 2], delta)
    }

    /**
     * Scenario: A 2x2 matrix ought to be representable
     * Given the following 2x2 matrix M:
     * | -3 | 5 |
     * | 1 | -2 |
     * Then M[0,0] = -3
     * And M[0,1] = 5
     * And M[1,0] = 1
     * And M[1,1] = -2
     */
    @Test
    fun matrix2x2Construction() {
        val m = Matrix(2, 2, doubleArrayOf(-3.0, 5.0, 1.0, -2.0))
        assertEquals(-3.0, m[0, 0], delta)
        assertEquals(5.0, m[0, 1], delta)
        assertEquals(1.0, m[1, 0], delta)
        assertEquals(-2.0, m[1, 1], delta)
    }
    /**
     *  Scenario: A 3x3 matrix ought to be representable
     *  Given the following 3x3 matrix M:
     *  |-3| 5| 0|
     *  | 1|-2|-7|
     *  |0|1|1|
     *  Then M[0,0] = -3
     *  And M[1,1] = -2
     *  And M[2,2] = 1
     */
    @Test
    fun matrix3x3Construction() {
        val m = Matrix(3, 3, doubleArrayOf(-3.0, 5.0, 0.0, 1.0, -2.0, -7.0, 0.0, 1.0, 1.0))
        assertEquals(-3.0, m[0, 0], delta)
        assertEquals(-2.0, m[1, 1], delta)
        assertEquals(1.0, m[2, 2], delta)
    }

    /**
     *  Scenario: Matrix equality with identical matrices
     *  Given the following matrix A:
     *  |1|2|3|4|
     *  |5|6|7|8|
     *  |9|8|7|6|
     *  |5|4|3|2|
     *  And the following matrix B:
     *  |1|2|3|4|
     *  |5|6|7|8|
     *  |9|8|7|6|
     *  |5|4|3|2|
     *  Then A = B
     */
    @Test
    fun matrixEquality() {
        val a = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0))
        val b = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0))
        assertEquals(b, a)
    }

    /**
     *  Scenario: Matrix equality with different matrices
     *  Given the following matrix A:
     *  |1|2|3|4|
     *  |5|6|7|8|
     *  |9|8|7|6|
     *  |5|4|3|2|
     *  And the following matrix B:
     *  |2|3|4|5|
     *  |6|7|8|9|
     *  |8|7|6|5|
     *  |4|3|2|1|
     *  Then A != B
     */
    @Test
    fun matrixInequality() {
        val a = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0))
        val b = Matrix(4, 4, doubleArrayOf(2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0))
        assertNotEquals(b, a)

    }

    /**
     * Scenario: Multiplying two matrices
     * Given the following matrix A:
     * |1|2|3|4|
     * |5|6|7|8|
     * |9|8|7|6|
     * |5|4|3|2|
     * And the following matrix B:
     * |-2|1|2| 3|
     * | 3|2|1|-1|
     * | 4|3|6| 5|
     * | 1|2|7| 8|
     * Then A * B is the following 4x4 matrix:
     * |20| 22| 50| 48|
     * |44| 54|114|108|
     * |40| 58|110|102|
     * |16| 26| 46| 42|
     */
    @Test
    fun multiplyingMatrices() {
        val a = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0,
                                                         5.0, 6.0, 7.0, 8.0,
                                                         9.0, 8.0, 7.0, 6.0,
                                                         5.0, 4.0, 3.0, 2.0))
        val b = Matrix(4, 4, doubleArrayOf(-2.0, 1.0, 2.0, 3.0,
                                                         3.0, 2.0, 1.0, -1.0,
                                                         4.0, 3.0, 6.0, 5.0,
                                                         1.0, 2.0, 7.0, 8.0))
        val result = Matrix(4, 4, doubleArrayOf(20.0, 22.0, 50.0, 48.0,
                                                              44.0, 54.0, 114.0, 108.0,
                                                              40.0, 58.0, 110.0, 102.0,
                                                              16.0, 26.0, 46.0, 42.0))
        assertEquals(result, a * b)
    }


    /**
     * Scenario: A matrix multiplied by a tuple
     * Given the following matrix A:
     * |1|2|3|4|
     * |2|4|4|2|
     * |8|6|4|1|
     * |0|0|0|1|
     * And b ← tuple(1, 2, 3, 1)
     * Then A * b = tuple(18, 24, 33, 1)
     */
    @Test
    fun multiplyMatrixByTuple() {
        val a = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0,
            2.0, 4.0, 4.0, 2.0,
            8.0, 6.0, 4.0, 1.0,
            0.0, 0.0, 0.0, 1.0))
        val b = Tuple(1.0, 2.0, 3.0, 1.0)
        assertEquals(Tuple(18.0, 24.0, 33.0, 1.0), a * b)
    }

    /**
     * Scenario: Multiplying a matrix by the identity matrix
     * Given the following matrix A:
     * |0|1| 2| 4|
     * |1|2| 4| 8|
     * |2|4| 8|16|
     * | 4 | 8 | 16 | 32 |
     * Then A * IdentityMatrix = A
     */
    @Test
    fun multiplyMatrixByIdentity() {
        val a = Matrix(4, 4, doubleArrayOf(1.0, 2.0, 3.0, 4.0,
            1.0, 2.0, 4.0, 8.0,
            2.0, 4.0, 8.0, 16.0,
            4.0, 8.0, 16.0, 32.0))
        assertEquals(a, a * identityMatrix())
    }

    /**
     *  Scenario: Multiplying the identity matrix by a tuple
     *  Given a ← tuple(1, 2, 3, 4)
     *  Then IdentityMatrix * a = a
     */
    @Test
    fun multiplyTuplebyIdentity() {
        val a = Tuple(1.0, 2.0, 3.0, 4.0)
        assertEquals(a, identityMatrix() * a)
    }

    /**
     * Scenario: Transposing a matrix
     * Given the following matrix A:
     * |0|9|3|0|
     * |9|8|0|8|
     * |1|8|5|3|
     * |0|0|5|8|
     * Then transpose(A) is the following matrix:
     * |0|9|1|0|
     * |9|8|8|0|
     * |3|0|5|5|
     * |0|8|3|8|
     */
    @Test
    fun transposingMatrix() {
        val a = Matrix(4, 4,
            doubleArrayOf(0.0, 9.0, 3.0, 0.0,
            9.0, 8.0, 0.0, 8.0,
            1.0, 8.0, 5.0, 3.0,
            0.0, 0.0, 5.0, 8.0))
        assertEquals(Matrix(4, 4,
            doubleArrayOf(0.0, 9.0, 1.0, 0.0,
                9.0, 8.0, 8.0, 0.0,
                3.0, 0.0, 5.0, 5.0,
                0.0, 8.0, 3.0, 8.0)), a.transpose())
    }


    /**
     * Scenario: Transposing the identity matrix
     * Given A ← transpose(identity_matrix)
     * Then A = identity_matrix
     */
    @Test
    fun transposingMatrixIdentity() {
        assertEquals(identityMatrix(), identityMatrix().transpose())
    }

    /**
     * Scenario: Calculating the determinant of a 2x2 matrix
     * Given the following 2x2 matrix A:
     * | 1|5|
     * | -3 | 2 |
     * Then determinant(A) = 17
     */
    @Test
    fun matrixDeterminat() {
        val a = Matrix(2, 2, doubleArrayOf(1.0, 5.0, -3.0, 2.0))
        assertEquals(17.0 , a.determinant(), delta)
    }

    /**
     * Scenario: A submatrix of a 3x3 matrix is a 2x2 matrix
     * Given the following 3x3 matrix A:
     * | 1|5| 0|
     * |-3|2| 7|
     * | 0|6|-3|
     * Then submatrix(A, 0, 2) is the following 2x2 matrix:
     * | -3 | 2 |
     * | 0|6|
     */
    @Test
    fun submatrixOfThreeByThree() {
        val a = Matrix(3, 3, doubleArrayOf(1.0, 5.0, 0.0, -3.0, 2.0, 7.0, 0.0, 6.0, -3.0))
        assertEquals(Matrix(2, 2, doubleArrayOf(-3.0, 2.0, 0.0, 6.0)), a.submatrix(0, 2))
    }

    /**
     * Scenario: A submatrix of a 4x4 matrix is a 3x3 matrix
     * Given the following 4x4 matrix A:
     * |-6| 1| 1| 6|
     * |-8| 5| 8| 6|
     * |-1| 0| 8| 2|
     * |-7| 1|-1| 1|
     * Then submatrix(A, 2, 1) is the following 3x3 matrix:
     * |-6| 1|6|
     * |-8| 8|6|
     * | -7 | -1 | 1 |
     */
    @Test
    fun submatrixOfFourByFour() {
        val a = Matrix(4, 4,
            doubleArrayOf(-6.0, 1.0, 1.0, 6.0,
                -8.0, 5.0, 8.0, 6.0,
                -1.0, 0.0, 8.0, 2.0,
                -7.0, 1.0, -1.0, 1.0))
        assertEquals(Matrix(3, 3, doubleArrayOf(-6.0, 1.0, 6.0, -8.0, 8.0, 6.0, -7.0, -1.0, 1.0)), a.submatrix(2, 1))
    }

    /**
     * Scenario: Calculating a minor of a 3x3 matrix
     * Given the following 3x3 matrix A:
     * |3|5|0|
     * | 2|-1|-7|
     * | 6|-1| 5|
     * And B ← submatrix(A, 1, 0)
     * Then determinant(B) = 25
     * And minor(A, 1, 0) = 25
     */
    @Test
    fun matrixMinor() {
        val a = Matrix(3, 3, doubleArrayOf(3.0, 5.0, 0.0, 2.0, -1.0, -7.0, 6.0, -1.0, 5.0))
        val b = a.submatrix(1, 0)
        assertEquals(25.0, b.determinant(), delta)
        assertEquals(25.0, a.minor(1, 0), delta)
    }

    /**
     * Scenario: Calculating a cofactor of a 3x3 matrix
     * Given the following 3x3 matrix A:
     * |3|5|0|
     * | 2|-1|-7|
     * | 6|-1| 5|
     * Then minor(A, 0, 0) = -12
     * And cofactor(A, 0, 0) = -12
     * And minor(A, 1, 0) = 25
     * And cofactor(A, 1, 0) = -25
     */
    @Test
    fun matrixCofactor() {
        val a = Matrix(3, 3, doubleArrayOf(3.0, 5.0, 0.0, 2.0, -1.0, -7.0, 6.0, -1.0, 5.0))
        assertEquals(-12.0, a.minor(0, 0), delta)
        assertEquals(-12.0, a.cofactor(0, 0), delta)
        assertEquals(25.0, a.minor(1, 0), delta)
        assertEquals(-25.0, a.cofactor(1, 0), delta)
    }

    /**
     * Scenario: Calculating the determinant of a 3x3 matrix
     * Given the following 3x3 matrix A:
     * |1|2|6|
     * |-5| 8|-4|
     * |2|6|4|
     * Then cofactor(A, 0, 0) = 56
     * And cofactor(A, 0, 1) = 12
     * And cofactor(A, 0, 2) = -46
     * And determinant(A) = -196
     */
    @Test
    fun determinantForThreeByThreeMatrix() {
        val a = Matrix(3, 3,
            doubleArrayOf(1.0, 2.0, 6.0,
                -5.0, 8.0, -4.0,
                2.0, 6.0, 4.0))
        assertEquals(56.0, a.cofactor(0, 0), delta)
        assertEquals(12.0, a.cofactor(0, 1), delta)
        assertEquals(-46.0, a.cofactor(0, 2), delta)
        assertEquals(-196.0, a.determinant(), delta)
    }

    /**
     *  Scenario: Calculating the determinant of a 4x4 matrix
     *  Given the following 4x4 matrix A:
     *  |-2|-8| 3| 5|
     *  |-3| 1| 7| 3|
     *  | 1| 2|-9| 6|
     *  |-6| 7| 7|-9|
     *  Then cofactor(A, 0, 0) = 690
     *  And cofactor(A, 0, 1) = 447
     *  And cofactor(A, 0, 2) = 210
     *  And cofactor(A, 0, 3) = 51
     *  And determinant(A) = -4071
     */
    @Test
    fun determinantForFourByFourMatrix() {
        val a = Matrix(4, 4,
            doubleArrayOf(-2.0, -8.0, 3.0, 5.0,
                -3.0, 1.0, 7.0, 3.0,
                1.0, 2.0, -9.0, 6.0,
                -6.0, 7.0, 7.0, -9.0))
        assertEquals(690.0, a.cofactor(0, 0), delta)
        assertEquals(447.0, a.cofactor(0, 1), delta)
        assertEquals(210.0, a.cofactor(0, 2), delta)
        assertEquals(51.0, a.cofactor(0, 3), delta)
        assertEquals(-4071.0, a.determinant(), delta)
    }

    /**
     * Scenario: Testing an invertible matrix for invertibility
     * Given the following 4x4 matrix A:
     * |6|4|4|4|
     * |5|5|7|6|
     * | 4|-9| 3|-7|
     * | 9| 1| 7|-6|
     * Then determinant(A) = -2120
     * And A is invertible
     */
    @Test
    fun invertibleMatrix() {
        val a = Matrix(4, 4,
            doubleArrayOf(6.0, 4.0, 4.0, 4.0,
                5.0, 5.0, 7.0, 6.0,
                4.0, -9.0, 3.0, -7.0,
                9.0, 1.0, 7.0, -6.0))
        assertEquals(-2120.0, a.determinant(), delta)
        assertTrue(a.isInvertible())
    }

    /**
     *  Scenario: Testing a non-invertible matrix for invertibility
     *  Given the following 4x4 matrix A:
     *  |-4| 2|-2|-3|
     *  |9|6|2|6|
     *  | 0|-5| 1|-5|
     *  |0|0|0|0|
     *  Then determinant(A) = 0
     *  And A is not invertible
     */
    @Test
    fun notInvertibleMatrix() {
        val a = Matrix(4, 4,
            doubleArrayOf(-4.0, 2.0, -2.0, -3.0,
                9.0, 6.0, 2.0, 6.0,
                0.0, -5.0, 1.0, -5.0,
                0.0, 0.0, 0.0, 0.0))
        assertEquals(0.0, a.determinant(), delta)
        assertFalse(a.isInvertible())
    }

    /**
     * Scenario: Calculating the inverse of a matrix
     * Given the following 4x4 matrix A:
     * |-5| 2| 6|-8|
     * | 1|-5| 1| 8|
     * | 7| 7|-6|-7|
     * | 1|-3| 7| 4|
     * And B ← inverse(A)
     * Then determinant(A) = 532
     * And cofactor(A, 2, 3) = -160
     * And B[3,2] = -160/532
     * And cofactor(A, 3, 2) = 105
     * And B[2,3] = 105/532
     * And B is the following 4x4 matrix:
     * | 0.21805 | 0.45113 | 0.24060 | -0.04511 |
     * | -0.80827 | -1.45677 | -0.44361 | 0.52068 |
     * | -0.07895 | -0.22368 | -0.05263 |  0.19737 |
     * | -0.52256 | -0.81391 | -0.30075 |  0.30639 |
     */
    @Test
    fun invertMatrix() {
        val a = Matrix(4, 4,
            doubleArrayOf(-5.0, 2.0, 6.0, -8.0,
                1.0, -5.0, 1.0, 8.0,
                7.0, 7.0, -6.0, -7.0,
                1.0, -3.0, 7.0, 4.0))
        val b = a.inverse()
        assertEquals(532.0, a.determinant(), delta)
        assertEquals(-160.0, a.cofactor(2, 3), delta)
        assertEquals(-160.0/532, b.get(3, 2), delta)
        assertEquals(105.0, a.cofactor(3, 2), delta)
        assertEquals(105.0/532, b.get(2, 3), delta)
        assertEquals(Matrix(4, 4,
            doubleArrayOf(0.21805, 0.45113, 0.24060, -0.04511,
                -0.80827, -1.45677, -0.44361, 0.52068,
                -0.07895, -0.22368, -0.05263, 0.19737,
                -0.52256, -0.81391, -0.30075, 0.30639)), b)

    }

    /**
     * Scenario: Multiplying a product by its inverse
     * Given the following 4x4 matrix A:
     * | 3|-9| 7| 3|
     * | 3|-8| 2|-9|
     * |-4| 4| 4| 1|
     * |-6| 5|-1| 1|
     * And the following 4x4 matrix B:
     * |8|2|2|2|
     * | 3|-1| 7| 0|
     * |7|0|5|4|
     * | 6|-2| 0| 5|
     * And C ← A * B
     * Then C * inverse(B) = A
     */
    @Test
    fun multiplyAProductByItsInverse() {
        val a = Matrix(4, 4,
            doubleArrayOf(3.0, -9.0, 7.0, 3.0,
                3.0, -8.0, 2.0, -9.0,
                -4.0, 4.0, 4.0, 1.0,
                -6.0, 5.0, -1.0, 1.0))
        val b = Matrix(4, 4,
            doubleArrayOf(8.0, 2.0, 2.0, 2.0,
                3.0, -1.0, 7.0, 0.0,
                7.0, 0.0, 5.0, 4.0,
                6.0, -2.0, 0.0, 5.0))
        val c = a * b
        assertEquals(a, c * b.inverse())
    }
}
