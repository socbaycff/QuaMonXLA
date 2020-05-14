package com.example.quamonxla.util

fun Array<Int>.showValue(): String {
    var string = "("
    this.forEach {
        string+= "$it "
    }
    string += ")"
    return string
}