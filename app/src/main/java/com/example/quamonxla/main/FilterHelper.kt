package com.example.quamonxla.main

import com.example.quamonxla.util.showValue

class FilterHelper(var colNum: Int) {

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
    private fun layMauKhongGian(matrix: Array<IntArray>,x: Int, y: Int): Array<Int> {
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


     fun locMin(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)
        var min: Int
        min = 256
        for (j in 0..8) {
            if (mauKhongGian[j] < min) {
                min = mauKhongGian[j]
            }
        }
        val pixel = min
        mangPhepTinh[x*colNum + y] = "[$x,$y]: Min trong không gian ${mauKhongGian.showValue()} là $pixel"
        return pixel
    }

     fun locMax(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)
        var max: Int
        max = 0
        for (j in 0..8) {
            if (mauKhongGian[j] > max) {
                max = mauKhongGian[j]
            }
        }
        val pixel = max
         mangPhepTinh[x*colNum + y] = "[$x,$y]: Max trong không gian ${mauKhongGian.showValue()} là $pixel"
        return pixel
    }

     fun locTB(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)
        var sum = 0
         var str = ""
        for (j in 0..8) {
            str += "+ ${mauKhongGian[j].toString()}"
            sum += mauKhongGian[j]
        }
        val avg = sum / 9
         mangPhepTinh[x*colNum + y] = "\\([$x,$y]: \\frac{ ${str} }{9} = $avg\\)"
        return avg
    }

     fun locTrungVi(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)

        val arrayList = Array<Int>(9) {-1}
        for (j in 0..8) {

            arrayList[j] = mauKhongGian[j]
        }
        val sortedArray = arrayList.sortedArray()
        return sortedArray[4]
    }


     fun locTBTrongSo(x: Int, y: Int,matrix: Array<IntArray>, trongSo: Array<Int>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)
        val sumWeight = trongSo.reduce { acc, i ->
            acc + i
        }
        var sum = 0
        for (j in 0..7) {
            sum += trongSo[j] * mauKhongGian[j] // nhan voi trong so
        }
        val avg = sum / sumWeight

        return avg
    }


    fun locMidpoint(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
        return 0
    }


    fun canBangHistogram(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
        return 0
    }

    fun tinhHistogram(matrix: Array<IntArray>): MutableMap<Int,Int> {
        val pixelCount = Array<Int>(255) {0}
        matrix.forEach { row ->
            row.forEach { value ->
                pixelCount[value] += 1
            }
        }
        val result = mutableMapOf<Int,Int>()
        pixelCount.forEachIndexed { index, i ->
            if (i != 0) {
                result[index] = i
            }

        }
        return result.toSortedMap()
    }


    fun locCustom(): Int {
        return 0
    }

}