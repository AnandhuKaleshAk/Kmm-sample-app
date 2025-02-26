package common.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TestData(
    @SerialName("testData")
    var testVariable: String?=null
)
