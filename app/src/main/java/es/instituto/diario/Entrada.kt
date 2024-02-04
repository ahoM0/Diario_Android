package es.instituto.diario

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
@Serializable
class Entrada(
    @SerialName("fecha")
    var fecha: String,
    @SerialName("texto")
    var texto: String
)