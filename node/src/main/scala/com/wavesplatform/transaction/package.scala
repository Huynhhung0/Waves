package com.wavesplatform

import cats.data.ValidatedNel
import com.wavesplatform.account.PrivateKey
import com.wavesplatform.block.{Block, MicroBlock}
import com.wavesplatform.common.state.ByteStr
import com.wavesplatform.lang.ValidationError
import com.wavesplatform.transaction.validation.TxValidator
import com.wavesplatform.utils.base58Length

package object transaction {
  val AssetIdLength: Int       = com.wavesplatform.crypto.DigestLength
  val AssetIdStringLength: Int = base58Length(AssetIdLength)

  type DiscardedTransactions = Seq[Transaction]
  type DiscardedBlocks       = Seq[(Block, ByteStr)]
  type DiscardedMicroBlocks  = Seq[MicroBlock]
  type AuthorizedTransaction = Authorized with Transaction

  type TxType = Byte

  type TxVersion = Byte
  object TxVersion {
    val V1: TxVersion = 1.toByte
    val V2: TxVersion = 2.toByte
    val V3: TxVersion = 3.toByte
  }
  type TxAmount    = Long
  type TxTimestamp = Long
  type TxByteArray = Array[Byte]

  implicit class TransactionValidationOps[T <: Transaction: TxValidator](tx: T) {
    def validatedNel: ValidatedNel[ValidationError, T] = implicitly[TxValidator[T]].validate(tx)
    def validatedEither: Either[ValidationError, T]    = this.validatedNel.toEither.left.map(_.head)
  }

  implicit class TransactionSignOps[T](tx: T)(implicit sign: (T, PrivateKey) => T) {
    def signWith(privateKey: PrivateKey): T = sign(tx, privateKey)
  }
}
