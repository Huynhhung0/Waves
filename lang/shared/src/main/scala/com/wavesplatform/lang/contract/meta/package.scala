package com.wavesplatform.lang.contract

import com.wavesplatform.lang.v1.compiler.Types.{BOOLEAN, BYTESTR, LONG, STRING}

package object meta {
  private val definedTypes =
    List(LONG, BYTESTR, BOOLEAN, STRING)

  private[meta] val singleTypeMapper =
    SingleTypeMapper(definedTypes)

  private[meta] val unionTypeMapper =
    UnionTypeMapper(singleTypeMapper)

  private[meta] val listTypeMapper =
    ListTypeMapper(unionTypeMapper)

  object MetaMapperStrategyV1
    extends FunctionTypeMapper(unionTypeMapper, V1)
    with TypesTree
    with MetaMapperStrategy[V1.Self]

  object MetaMapperStrategyV2
    extends FunctionTypeMapper(listTypeMapper, V2)
    with TypesTree
    with MetaMapperStrategy[V2.Self]
}
