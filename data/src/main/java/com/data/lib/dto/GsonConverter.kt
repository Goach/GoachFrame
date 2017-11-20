package com.data.lib.dto

import android.text.TextUtils
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * Gson的一些格式化处理
 */
class GsonConverter {

    companion object {
        val mGsonBuilder: GsonBuilder = GsonBuilder()
                .registerTypeAdapter(String::class.java, NullStringAdapter())
                .registerTypeAdapter(Long::class.java, LongDeserializer())
                .registerTypeAdapter(Double::class.java, DoubleDeserializer())
                .registerTypeAdapter(Date::class.java, DateSerializer())
                .registerTypeAdapter(Date::class.java, DateDeserializer())
    }
    fun <T> createGson():Gson{
        val java = ResponseWrapper::class.java
        mGsonBuilder.registerTypeAdapter(java,ResponseWrapperDeserializer<T>())
        return mGsonBuilder.create()
    }
}

private class NullStringAdapter : TypeAdapter<String>() {

    override fun read(reader: JsonReader): String {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ""
        }

        return reader.nextString()
    }

    override fun write(writer: JsonWriter, value: String?) {
        if (value == null) {
            writer.nullValue()
            return
        }

        writer.value(value)
    }
}

private class DateSerializer : JsonSerializer<Date> {
    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement? {
        return if (src == null) null else JsonPrimitive(src.time / 1000)
    }
}

private class DateDeserializer : JsonDeserializer<Date> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        return if (json == null || json.asLong == 0L) null else Date(json.asLong * 1000)
    }
}

private class DoubleDeserializer : JsonDeserializer<Double> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Double {
        return if (json == null || TextUtils.isEmpty(json.asString)) 0.0 else json.asDouble
    }
}

private class LongDeserializer : JsonDeserializer<Long> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Long {
        return if (json == null || TextUtils.isEmpty(json.asString)) 0 else json.asLong
    }
}

private class ResponseWrapperDeserializer<T>:JsonDeserializer<ResponseWrapper<T>>{
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ResponseWrapper<T> {
        val jsonObj = json.asJsonObject
        val code = jsonObj.get("code").asInt
        val msg = jsonObj.get("msg").asString
        val version = jsonObj.get("version").asString
        val timestamp = jsonObj.get("timestamp").asLong
        val data = context.deserialize<T>(jsonObj.get("data"), (typeOfT as ParameterizedType).actualTypeArguments[0])
        return ResponseWrapper(code,msg,version,timestamp,data)
    }
}