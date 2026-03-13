package com.lagradost.cloudstream3.utils

/**
 * KMP-compatible crypto operations.
 * Wraps javax.crypto/java.security on JVM/Android, CommonCrypto on iOS.
 */
expect object CryptoHelper {
    /**
     * AES decrypt in CBC mode with PKCS5Padding.
     */
    fun aesCbcDecrypt(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray

    /**
     * AES encrypt in CBC mode with PKCS5Padding.
     */
    fun aesCbcEncrypt(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray

    /**
     * AES decrypt in CBC mode with NoPadding.
     */
    fun aesCbcDecryptNoPadding(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray

    /**
     * AES encrypt in CBC mode with NoPadding.
     */
    fun aesCbcEncryptNoPadding(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray

    /**
     * AES decrypt in ECB mode with PKCS5Padding.
     */
    fun aesEcbDecrypt(data: ByteArray, key: ByteArray): ByteArray

    /**
     * AES encrypt in ECB mode with PKCS5Padding.
     */
    fun aesEcbEncrypt(data: ByteArray, key: ByteArray): ByteArray

    /**
     * AES decrypt in GCM mode.
     */
    fun aesGcmDecrypt(data: ByteArray, key: ByteArray, iv: ByteArray, tagLength: Int = 128): ByteArray

    /**
     * MD5 hash.
     */
    fun md5(data: ByteArray): ByteArray

    /**
     * MD5 digest with update/digest cycle support.
     * Processes [input] through MessageDigest-style update then digest with [salt].
     * Returns the digest result.
     */
    fun md5Digest(input: ByteArray, salt: ByteArray): ByteArray

    /**
     * Generate random bytes.
     */
    fun randomBytes(size: Int): ByteArray
}
