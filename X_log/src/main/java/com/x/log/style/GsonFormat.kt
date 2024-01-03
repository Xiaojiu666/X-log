package com.x.log.style

import org.json.JSONArray
import org.json.JSONObject

class GsonFormat(var indentSpaces: Int = 4) : AbsLoggerFormat() {

    override fun format(message: Any): String {
        return formatDataFromJson(message.toString())!!
    }

    private fun formatDataFromJson(response: String): String? {
        var data = StringBuilder()
        try {
            if (response.startsWith("{")) {
                val jsonObject = JSONObject(response)
                data.append(jsonObject.toString(indentSpaces))
            } else if (response.startsWith("[")) {
                val jsonArray = JSONArray(response)
                data.append(jsonArray.toString(indentSpaces))
            }else{
                data = data.append(response)
            }
        } catch (e: Exception) {
            data = data.append(response)
        }
        return data.toString()
    }

}