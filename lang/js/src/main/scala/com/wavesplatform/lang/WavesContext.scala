package com.wavesplatform.lang

import com.wavesplatform.common.state.ByteStr
import com.wavesplatform.lang.v1.traits.domain.{BlockHeader, Recipient, Tx}
import com.wavesplatform.lang.v1.traits.{DataType, Environment}

object WavesContext {
  val env = new Environment {
    override def height: Long                                         = impl.Environment.height
    override def chainId: Byte                                        = impl.Environment.chainId
    override def inputEntity: Environment.InputEntity                 = impl.Environment.inputEntity
    override def transactionById(id: Array[Byte]): Option[Tx]         = impl.Environment.transactionById(id)
    override def transactionHeightById(id: Array[Byte]): Option[Long] = impl.Environment.transactionHeightById(id)
    override def data(addressOrAlias: Recipient, key: String, dataType: DataType): Option[Any] =
      impl.Environment.data(addressOrAlias, key, dataType)
    override def resolveAlias(name: String): Either[String, Recipient.Address] = impl.Environment.resolveAddress(name)
    override def accountBalanceOf(addressOrAlias: Recipient, assetId: Option[Array[Byte]]): Either[String, Long] =
      impl.Environment.accountBalanceOf(addressOrAlias, assetId)
    override def tthis: Recipient.Address                                               = ???
    override def transactionParser(bytes: Array[Byte]): Option[Tx]                      = impl.Environment.transactionParser(bytes)
    override def blockHeaderParser(bytes: Array[Byte]): Option[BlockHeader]             = impl.Environment.blockHeaderParser(bytes)
    override def calculatePoSDelay(hit: ByteStr, baseTarget: Long, balance: Long): Long = impl.Environment.calculatePoSDelay(hit, baseTarget, balance)
    override def accountScriptHash(address: Recipient): Option[Array[Byte]] = impl.Environment.accountScriptHash(address)
  }
}
