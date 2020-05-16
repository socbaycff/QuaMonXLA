package com.example.quamonxla.main

import com.example.quamonxla.util.showValue
import java.util.concurrent.atomic.LongAdder
import kotlin.math.pow
import kotlin.math.sqrt

class FilterHelper(var colNum: Int) {
    var qValue: Int = 1

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
        val pixel = min
        mangPhepTinh[x * colNum + y] =
            "[$x,$y]: Min trong không gian ${mauKhongGian.showValue()} là $pixel"
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
        val pixel = max
        mangPhepTinh[x * colNum + y] =
            "[$x,$y]: Max trong không gian ${mauKhongGian.showValue()} là $pixel"
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
        val avg = sum / 9
        mangPhepTinh[x * colNum + y] = "\\([$x,$y]: \\frac{ ${str} }{9} = $avg\\)"
        return avg
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
        return sortedArray[4]
    }


    fun locMidpoint(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        val min = mauKhongGian.min()!!
        val max = mauKhongGian.max()!!
        val midpoint = (max + min) / 2
        mangPhepTinh[x * colNum + y] =
            "\\([$x,$y]: Max:$max Min:$min => \\frac{$max + $min}{2} = $midpoint\\)"
        return midpoint

    }

    fun locGeo(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var product: Long = 1
        var str = ""
        for (j in 0..8) {
            str += ".${mauKhongGian[j]}"
            product *= mauKhongGian[j]
        }
        val pixel = product.toDouble().pow((1.0 / 9)).toInt()
        mangPhepTinh[x * colNum + y] = "\\([$x,$y]: \\sqrt[9]{ ${str.substring(1)} } = $pixel \\)"
        return pixel

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
        var sum = 0
        for (j in 0..8) {
            str += "+ ${filter[j]}.${mauKhongGian[j]}"
            sum += filter[j] * mauKhongGian[j] // nhan voi trong so
        }
        val avg = sum / sumWeight
        mangPhepTinh[x * colNum + y] =
            "\\( [$x,$y]: \\frac{ ${str.substring(2)} }{ $sumWeight } = $avg \\)"
        return avg
    }

    fun locDH(x: Int, y: Int, matrix: Array<IntArray>, mangPhepTinh: Array<String>): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var mauSo: Double = 0.0
        var str = ""
        for (j in 0..8) {
            str += "+ \\frac{1}{ ${mauKhongGian[j]} }"
            mauSo += (1.0 / mauKhongGian[j])
        }
        val result = (9 / mauSo).toInt()
        mangPhepTinh[x * colNum + y] = "\\( [$x,$y]: \\frac{9}{ ${str.substring(2)} } = $result \\)"
        return result
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
        val result = (tuSo / mauSo).toInt()
        mangPhepTinh[x * colNum + y] =
            "\\( [$x,$y]: \\frac{ ${strTu.substring(1)} }{ ${strMau.substring(1)} } = $result \\)"
        return result
    }

    fun locCustom(
        x: Int,
        y: Int,
        matrix: Array<IntArray>,
        mangPhepTinh: Array<String>,
        filter: Array<Int>
    ): Int {
        val mauKhongGian = layMauKhongGian(matrix, x, y)
        var result = 0
        var str = ""
        for (j in 0..8) {
            str += "+ ${filter[j]}.${mauKhongGian[j]}"
            result += filter[j] * mauKhongGian[j] // nhan voi trong so
        }

        mangPhepTinh[x * colNum + y] = "[$x,$y]: ${str.substring(2)} = $result"
        return result
    }

}