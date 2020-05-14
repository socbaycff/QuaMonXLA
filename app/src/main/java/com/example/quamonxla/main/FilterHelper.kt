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
        mangPhepTinh[x*colNum + y] = "Min trong không gian ${mauKhongGian.showValue()} là $pixel"
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

        return pixel
    }

     fun locTB(x: Int, y: Int,matrix: Array<IntArray>,mangPhepTinh: Array<String>): Int {
         val mauKhongGian = layMauKhongGian(matrix,x,y)
        var sum = 0
        for (j in 0..8) {

            sum += mauKhongGian[j]
        }
        val avg = sum / 9

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

}