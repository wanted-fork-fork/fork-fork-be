package com.fork.forkfork.image.util

import java.security.MessageDigest

const val BYTE_ARRAY_EMPTY = "byteArray should not be empty"
const val MD5 = "MD5"
const val JOIN_EMPTY_SEPARATOR = ""

fun ByteArray.toMd5HexString(): String {
    val md5ByteArray = this.toMd5Byte()
    return md5ByteArray.toHex()
}

fun ByteArray.toMd5Byte(): ByteArray {
    require(this.isNotEmpty()) { BYTE_ARRAY_EMPTY }
    val md = MessageDigest.getInstance(MD5)
    return md.digest(this)
}

fun ByteArray.toHex(): String = joinToString(separator = JOIN_EMPTY_SEPARATOR) { eachByte -> "%02x".format(eachByte) }

fun ByteArray.getImageId(): String = String.format("%s_%06x", this.toMd5HexString(), this.size)
