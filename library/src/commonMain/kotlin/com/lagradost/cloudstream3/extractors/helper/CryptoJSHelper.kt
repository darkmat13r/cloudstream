package com.lagradost.cloudstream3.extractors.helper

import com.lagradost.cloudstream3.base64DecodeArray
import com.lagradost.cloudstream3.base64Encode
import com.lagradost.cloudstream3.utils.CryptoHelper
import kotlin.math.min

/**
 * Conforming with CryptoJS AES method
 */
// see https://gist.github.com/thackerronak/554c985c3001b16810af5fc0eb5c358f
@Suppress("unused", "FunctionName", "SameParameterValue")
object CryptoJS {

    private const val KEY_SIZE    = 256
    private const val IV_SIZE     = 128

    // Seriously crypto-js, what's wrong with you?
    private const val APPEND      = "Salted__"

    /**
     * Encrypt
     * @param password passphrase
     * @param plainText plain string
     */
    fun encrypt(password: String, plainText: String): String {
        val saltBytes = generateSalt(8)
        val key       = ByteArray(KEY_SIZE / 8)
        val iv        = ByteArray(IV_SIZE / 8)
        evpkdf(password.encodeToByteArray(), KEY_SIZE, IV_SIZE, saltBytes, key, iv)

        val cipherText = CryptoHelper.aesCbcEncrypt(plainText.encodeToByteArray(), key, iv)
        // Thanks kientux for this: https://gist.github.com/kientux/bb48259c6f2133e628ad
        // Create CryptoJS-like encrypted!
        val sBytes     = APPEND.encodeToByteArray()
        val b          = ByteArray(sBytes.size + saltBytes.size + cipherText.size)
        sBytes.copyInto(b, destinationOffset = 0)
        saltBytes.copyInto(b, destinationOffset = sBytes.size)
        cipherText.copyInto(b, destinationOffset = sBytes.size + saltBytes.size)

        return base64Encode(b)
    }

    /**
     * Decrypt
     * Thanks Artjom B. for this: http://stackoverflow.com/a/29152379/4405051
     * @param password passphrase
     * @param cipherText encrypted string
     */
    fun decrypt(password: String, cipherText: String): String {
        val ctBytes         = base64DecodeArray(cipherText)
        val saltBytes       = ctBytes.copyOfRange(8, 16)
        val cipherTextBytes = ctBytes.copyOfRange(16, ctBytes.size)

        val key = ByteArray(KEY_SIZE / 8)
        val iv  = ByteArray(IV_SIZE / 8)
        evpkdf(password.encodeToByteArray(), KEY_SIZE, IV_SIZE, saltBytes, key, iv)

        val plainText = CryptoHelper.aesCbcDecrypt(cipherTextBytes, key, iv)
        return plainText.decodeToString()
    }

    private fun evpkdf(password: ByteArray, keySize: Int, ivSize: Int, salt: ByteArray, resultKey: ByteArray, resultIv: ByteArray): ByteArray {
        return evpkdf(password, keySize, ivSize, salt, 1, resultKey, resultIv)
    }

    @Suppress("NAME_SHADOWING")
    private fun evpkdf(password: ByteArray, keySize: Int, ivSize: Int, salt: ByteArray, iterations: Int, resultKey: ByteArray, resultIv: ByteArray): ByteArray {
        val keySize              = keySize / 32
        val ivSize               = ivSize / 32
        val targetKeySize        = keySize + ivSize
        val derivedBytes         = ByteArray(targetKeySize * 4)
        var numberOfDerivedWords = 0
        var block: ByteArray?    = null

        while (numberOfDerivedWords < targetKeySize) {
            val input = (block ?: ByteArray(0)) + password
            block = CryptoHelper.md5Digest(input, salt)

            // Iterations
            for (i in 1 until iterations) {
                block = CryptoHelper.md5(block!!)
            }

            block!!.copyInto(
                derivedBytes,
                destinationOffset = numberOfDerivedWords * 4,
                startIndex = 0,
                endIndex = min(block.size, (targetKeySize - numberOfDerivedWords) * 4)
            )

            numberOfDerivedWords += block.size / 4
        }

        derivedBytes.copyInto(resultKey, destinationOffset = 0, startIndex = 0, endIndex = keySize * 4)
        derivedBytes.copyInto(resultIv, destinationOffset = 0, startIndex = keySize * 4, endIndex = keySize * 4 + ivSize * 4)

        return derivedBytes // key + iv
    }

    private fun generateSalt(length: Int): ByteArray {
        return CryptoHelper.randomBytes(length)
    }
}