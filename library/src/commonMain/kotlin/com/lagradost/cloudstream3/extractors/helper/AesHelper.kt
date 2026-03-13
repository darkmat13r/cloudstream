package com.lagradost.cloudstream3.extractors.helper
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

import com.lagradost.cloudstream3.base64DecodeArray
import com.lagradost.cloudstream3.base64Encode
import com.lagradost.cloudstream3.utils.AppUtils
import com.lagradost.cloudstream3.utils.CryptoHelper

object AesHelper {

    private const val HASH = "AES/CBC/PKCS5PADDING"

    fun cryptoAESHandler(
        data: String,
        pass: ByteArray,
        encrypt: Boolean = true,
        padding: String = HASH,
    ): String? {
        val parse = AppUtils.tryParseJson<AesData>(data) ?: return null
        val (key, iv) = generateKeyAndIv(
            pass,
            parse.s.hexToByteArray(),
            ivLength = parse.iv.length / 2,
            saltLength = parse.s.length / 2
        ) ?: return null
        val useNoPadding = padding.contains("NoPadding", ignoreCase = true)
        return if (!encrypt) {
            val decrypted = if (useNoPadding) {
                CryptoHelper.aesCbcDecryptNoPadding(base64DecodeArray(parse.ct), key, iv)
            } else {
                CryptoHelper.aesCbcDecrypt(base64DecodeArray(parse.ct), key, iv)
            }
            decrypted.decodeToString()
        } else {
            val encrypted = if (useNoPadding) {
                CryptoHelper.aesCbcEncryptNoPadding(parse.ct.encodeToByteArray(), key, iv)
            } else {
                CryptoHelper.aesCbcEncrypt(parse.ct.encodeToByteArray(), key, iv)
            }
            base64Encode(encrypted)
        }
    }

    // https://stackoverflow.com/a/41434590/8166854
    fun generateKeyAndIv(
        password: ByteArray,
        salt: ByteArray,
        keyLength: Int = 32,
        ivLength: Int,
        saltLength: Int,
        iterations: Int = 1
    ): Pair<ByteArray,ByteArray>? {

        val digestLength = 16 // MD5 digest length
        val targetKeySize = keyLength + ivLength
        val requiredLength = (targetKeySize + digestLength - 1) / digestLength * digestLength
        val generatedData = ByteArray(requiredLength)
        var generatedLength = 0

        try {
            while (generatedLength < targetKeySize) {
                // Build the input for this round of MD5
                var input = ByteArray(0)

                if (generatedLength > 0) {
                    input += generatedData.copyOfRange(
                        generatedLength - digestLength,
                        generatedLength
                    )
                }

                input += password
                input += salt.copyOfRange(0, saltLength)

                var digest = CryptoHelper.md5(input)
                digest.copyInto(generatedData, destinationOffset = generatedLength)

                for (i in 1 until iterations) {
                    digest = CryptoHelper.md5(digest)
                    digest.copyInto(generatedData, destinationOffset = generatedLength)
                }

                generatedLength += digestLength
            }
            return generatedData.copyOfRange(0, keyLength) to generatedData.copyOfRange(keyLength, targetKeySize)
        } catch (e: Exception) {
            return null
        }
    }

    fun String.hexToByteArray(): ByteArray {
        check(length % 2 == 0) { "Must have an even length" }
        return chunked(2)
            .map { it.toInt(16).toByte() }
            .toByteArray()
    }

    @Serializable
    private data class AesData(
        @SerialName("ct") val ct: String,
        @SerialName("iv") val iv: String,
        @SerialName("s") val s: String
    )

}