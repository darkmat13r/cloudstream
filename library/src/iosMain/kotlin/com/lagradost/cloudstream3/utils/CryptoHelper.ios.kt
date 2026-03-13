package com.lagradost.cloudstream3.utils

import kotlinx.cinterop.*
import platform.CoreCrypto.*
import platform.Security.SecRandomCopyBytes
import platform.Security.kSecRandomDefault
import platform.posix.uint8_tVar

@OptIn(ExperimentalForeignApi::class)
actual object CryptoHelper {
    actual fun aesCbcDecrypt(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray {
        return aesCrypt(data, key, iv, kCCDecrypt, kCCOptionPKCS7Padding)
    }

    actual fun aesCbcEncrypt(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray {
        return aesCrypt(data, key, iv, kCCEncrypt, kCCOptionPKCS7Padding)
    }

    actual fun aesCbcDecryptNoPadding(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray {
        return aesCrypt(data, key, iv, kCCDecrypt, 0.toUInt())
    }

    actual fun aesCbcEncryptNoPadding(data: ByteArray, key: ByteArray, iv: ByteArray): ByteArray {
        return aesCrypt(data, key, iv, kCCEncrypt, 0.toUInt())
    }

    actual fun aesEcbDecrypt(data: ByteArray, key: ByteArray): ByteArray {
        return aesCrypt(data, key, null, kCCDecrypt, kCCOptionPKCS7Padding or kCCOptionECBMode)
    }

    actual fun aesEcbEncrypt(data: ByteArray, key: ByteArray): ByteArray {
        return aesCrypt(data, key, null, kCCEncrypt, kCCOptionPKCS7Padding or kCCOptionECBMode)
    }

    actual fun aesGcmDecrypt(data: ByteArray, key: ByteArray, iv: ByteArray, tagLength: Int): ByteArray {
        throw UnsupportedOperationException("AES-GCM not yet implemented for iOS")
    }

    actual fun md5(data: ByteArray): ByteArray {
        val result = UByteArray(CC_MD5_DIGEST_LENGTH.toInt())
        data.asUByteArray().usePinned { dataPin ->
            result.usePinned { resultPin ->
                CC_MD5(dataPin.addressOf(0), data.size.toUInt(), resultPin.addressOf(0).reinterpret())
            }
        }
        return result.asByteArray()
    }

    actual fun md5Digest(input: ByteArray, salt: ByteArray): ByteArray {
        return memScoped {
            val context = alloc<CC_MD5_CTX>()
            CC_MD5_Init(context.ptr)
            input.asUByteArray().usePinned { inputPin ->
                CC_MD5_Update(context.ptr, inputPin.addressOf(0), input.size.toUInt())
            }
            val result = UByteArray(CC_MD5_DIGEST_LENGTH.toInt())
            salt.asUByteArray().usePinned { saltPin ->
                CC_MD5_Update(context.ptr, saltPin.addressOf(0), salt.size.toUInt())
            }
            result.usePinned { resultPin ->
                CC_MD5_Final(resultPin.addressOf(0).reinterpret(), context.ptr)
            }
            result.asByteArray()
        }
    }

    actual fun randomBytes(size: Int): ByteArray {
        val bytes = ByteArray(size)
        bytes.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, size.toULong(), pinned.addressOf(0))
        }
        return bytes
    }

    @Suppress("UNUSED_VARIABLE")
    private fun aesCrypt(
        data: ByteArray,
        key: ByteArray,
        iv: ByteArray?,
        operation: CCOperation,
        options: CCOptions
    ): ByteArray {
        return memScoped {
            val outLength = alloc<ULongVar>()
            val bufferSize = data.size.toULong() + kCCBlockSizeAES128.toULong()
            val outBuffer = ByteArray(bufferSize.toInt())

            data.usePinned { dataPin ->
                key.usePinned { keyPin ->
                    outBuffer.usePinned { outPin ->
                        if (iv != null) {
                            iv.usePinned { ivPin ->
                                val status = CCCrypt(
                                    operation, kCCAlgorithmAES128.toUInt(), options,
                                    keyPin.addressOf(0), key.size.toULong(),
                                    ivPin.addressOf(0),
                                    dataPin.addressOf(0), data.size.toULong(),
                                    outPin.addressOf(0), bufferSize.toULong(),
                                    outLength.ptr
                                )
                                if (status != kCCSuccess) {
                                    throw RuntimeException("CCCrypt failed with status: $status")
                                }
                            }
                        } else {
                            val status = CCCrypt(
                                operation, kCCAlgorithmAES128.toUInt(), options,
                                keyPin.addressOf(0), key.size.toULong(),
                                null,
                                dataPin.addressOf(0), data.size.toULong(),
                                outPin.addressOf(0), bufferSize.toULong(),
                                outLength.ptr
                            )
                            if (status != kCCSuccess) {
                                throw RuntimeException("CCCrypt failed with status: $status")
                            }
                        }
                    }
                }
            }

            outBuffer.copyOf(outLength.value.toInt())
        }
    }
}
