package com.example.quamonxla.main

import android.util.Log
import ch.obermuhlner.math.big.BigDecimalMath
import com.example.quamonxla.util.showValue
import java.math.BigDecimal
import java.math.MathContext
import kotlin.math.pow
import kotlin.math.roundToInt

class FilterHelper(var colNum: Int) {
    var qValue: Int = 1
    var dValue = 0

    /**
     * chuyen doi tu ma tran sang mang
     *
     * A[x][y] -> B[ x*colNum + y ]
     *
     * chuyen doi mang sang ma tran
     *
     * B[z] -> A[ z/colNum ][ z%colNum ]
     *
     *
     */
    private fun layMauKhongGian(matrix: Array<IntArray>, x: Int, y: Int): Array<Int> {
        val pixelsRGB = Array<Int>(9) { 0 } // so 0

        pixelsRGB[0] = matrix[x - 1][y - 1]
        pixelsRGB[1] = matrix[x][y - 1]
        pixelsRGB[2] = matrix[x + 1][y - 1]
        pixelsRGB[3] = matrix[x - 1][y]
        pixelsRGB[5] = matrix[x + 1][y]
        pixelsRGB[6] = matrix[x - 1][y + 1]
        pixelsRGB[7] = matrix[x][y + 1]
        pixelsRGB[8] = matrix[x + 1][y + 1]
        pixelsRGB[4] = matrix[x][y]

        return pixelsRGB
    }


    fun locMin(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var min: Int
        min = 256
        for (j in 0..8) {
            if (mauKhongGian[j] < min) {
                min = mauKhongGian[j]
            }
        }
        var pixel = min
        mangPhepTinh[x * colNum + y] =
            "[$x,$y]: Min trong không gian ${mauKhongGian.showValue()} là $pixel"
        if (pixel < 0) pixel = 0
        if (pixel > 255) pixel = 255
        return pixel
    }

    fun locMax(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var max: Int
        max = 0
        for (j in 0..8) {
            if (mauKhongGian[j] > max) {
                max = mauKhongGian[j]
            }
        }
        var pixel = max
        mangPhepTinh[x * colNum + y] =
            "[$x,$y]: Max trong không gian ${mauKhongGian.showValue()} là $pixel"
        if (pixel < 0) pixel = 0
        if (pixel > 255) pixel = 255
        return pixel
    }

    fun locTB(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var sum = 0
        var str = ""
        for (j in 0..8) {
            str += "+ ${mauKhongGian[j].toString()}"
            sum += mauKhongGian[j]
        }
        var avg = sum / 9.0
        mangPhepTinh[x * colNum + y] = "\\([$x,$y]: \\frac{ ${str.substring(1)} }{9} = ${avg.roundToInt()}\\)"
        if (avg < 0) avg = 0.0
        if (avg > 255) avg = 255.0
        Log.i("infooooo",avg.toString())
        return avg.roundToInt()
    }

    fun locTrungVi(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        val arrayList = Array<Int>(9) { -1 }
        for (j in 0..8) {
            arrayList[j] = mauKhongGian[j]
        }
        val sortedArray = arrayList.sortedArray()
        mangPhepTinh[x * colNum + y] =
            "[$x,$y]: Giá trị trung vị trong ${sortedArray.showValue()} là ${sortedArray[4]} "
        var result = sortedArray[4]
        if (sortedArray[4] < 0) result = 0
        if (sortedArray[4] > 255) result = 255
        return result
    }


    fun locMidpoint(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        val min = mauKhongGian.min()!!
        val max = mauKhongGian.max()!!
        var midpoint = (max + min) / 2.0
        mangPhepTinh[x * colNum + y] =
            "\\([$x,$y]: Max:$max Min:$min => \\frac{$max + $min}{2} = ${midpoint.roundToInt()}\\)"

        if (midpoint < 0) midpoint = 0.0
        if (midpoint > 255) midpoint = 255.0
        return midpoint.roundToInt()

    }

    fun locGeo(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var product = BigDecimal(1)
        var str = ""
        for (j in 0..8) {
            str += ".${mauKhongGian[j]}"
            product = product.multiply(BigDecimal.valueOf(mauKhongGian[j].toLong()))
        }
        val mathContext = MathContext(25)
        var pixel = BigDecimalMath.root(product, BigDecimal.valueOf(9.0),mathContext).toDouble()
        mangPhepTinh[x * colNum + y] = "\\([$x,$y]: \\sqrt[9]{ ${str.substring(1)} } = ${pixel.roundToInt()} \\)"
        if (pixel < 0) pixel = 0.0
        if  (pixel > 255) pixel = 255.0
        return pixel.roundToInt()

    }


    fun tinhHistogram(matrix: Array<IntArray>): MutableMap<Int, Int> {
        val pixelCount = Array<Int>(255) { 0 }
        matrix.forEach { row ->
            row.forEach { value ->
                pixelCount[value] += 1
            }
        }
        val result = mutableMapOf<Int, Int>()
        pixelCount.forEachIndexed { index, i ->
            if (i != 0) {
                result[index] = i
            }

        }
        return result.toSortedMap()
    }



    fun locDH(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var mauSo: Double = 0.0
        var str = ""
        for (j in 0..8) {
            str += "+ \\frac{1}{ ${mauKhongGian[j]} }"
            mauSo += (1.0 / mauKhongGian[j])
        }
        var result = (9 / mauSo).toFloat()
        mangPhepTinh[x * colNum + y] = "\\( [$x,$y]: \\frac{9}{ ${str.substring(2)} } = ${result.roundToInt()} \\)"
        if (result < 0) result = 0f
        if (result > 255) result = 255f
        return result.roundToInt()
    }

    fun locDHTP(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var tuSo = 0.0
        var mauSo = 0.0
        var strMau = ""
        var strTu = ""
        for (j in 0..8) {
            strMau += "+ ${mauKhongGian[j]}^${qValue}"
            strTu += "+ ${mauKhongGian[j]}^${qValue + 1}"
            mauSo += mauKhongGian[j].toDouble().pow(qValue)
            tuSo += mauKhongGian[j].toDouble().pow(qValue + 1)
        }
        var result = (tuSo / mauSo).toFloat()
        mangPhepTinh[x * colNum + y] =
            "\\( [$x,$y]: \\frac{ ${strTu.substring(1)} }{ ${strMau.substring(1)} } = ${result.roundToInt()} \\)"
        if (result < 0) result = 0f
        if (result > 255) result = 255f
        return result.roundToInt()
    }


    fun locTBTrongSo(
        x: Int,
        y: Int,
        matrix: Array<IntArray>,
        mangPhepTinh: Array<String>,
        filter: Array<Int>
    ): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        val sumWeight = filter.reduce { acc, i ->
            acc + i
        }
        var str = ""
        var sum = 0f
        for (j in 0..8) {
            str += "+ ${filter[j]}.${mauKhongGian[j]}"
            sum += filter[j] * mauKhongGian[j] // nhan voi trong so
        }
        var avg = sum / sumWeight
        mangPhepTinh[x * colNum + y] =
            "\\( [$x,$y]: \\frac{ ${str.substring(2)} }{ $sumWeight } = ${avg.roundToInt()} \\)"
        if (avg < 0) avg = 0f
        if (avg > 255) avg = 255f
        return avg.roundToInt()
    }

    fun locCustom(
        x: Int,
        y: Int,
        matrix: Array<IntArray>,
        mangPhepTinh: Array<String>,
        filter: Array<Int>
    ): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var result = 0f
        var str = ""
        for (j in 0..8) {
            str += "+ ${filter[j]}.${mauKhongGian[j]}"
            result += filter[j] * mauKhongGian[j] // nhan voi trong so
        }

        mangPhepTinh[x * colNum + y] = "[$x,$y]: ${str.substring(2)} = ${result.roundToInt()}"
        if (result <0) result = 0f
        if (result > 255) result = 255f
        return result.roundToInt()
    }


    fun alphaTrim(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        val arrayList = Array<Int>(9) { -1 }
        for (j in 0..8) {
            arrayList[j] = mauKhongGian[j]
        }
        val sortedArray = arrayList.sortedArray()
        var sum = 0
        var str = ""
        for (i in (0+dValue/2)..(8-dValue/2)  ) {
            str += "+ ${sortedArray[i]}"
           sum += sortedArray[i]
        }

        var result = sum/ (9f-dValue)
        mangPhepTinh[x * colNum + y] =
            "\\([$x,$y]:  \\frac{ ${str.substring(1)} }{ ${9-dValue} } = ${result.roundToInt()} \\)"
        if (result <0) result = 0f
        if (result > 255) result = 255f
        return result.roundToInt()
    }

}