package com.moyerun.moyeorun_android.network

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.moyerun.moyeorun_android.network.api.Success
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.Charset

class MoyeorunJsonConverterFactory private constructor(
    private val gson: Gson
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MoyeorunResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return MoyeorunRequestBodyConverter(gson, adapter)
    }

    companion object {
        fun create(): MoyeorunJsonConverterFactory {
            return MoyeorunJsonConverterFactory(Gson())
        }
    }

    class MoyeorunResponseBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<ResponseBody, T> {

        override fun convert(value: ResponseBody): T? {
            val jsonReader = gson.newJsonReader(value.charStream())
            try {
                val result: Success<T> = gson.fromJson(jsonReader, Success::class.java)
                val data = gson.toJson(result.data)
                return adapter.fromJson(data)
            } catch (e: Exception) {
                throw IOException(e)
            } finally {
                value.close()
            }
        }
    }

    class MoyeorunRequestBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ): Converter<T, RequestBody> {

        @Throws(IOException::class)
        override fun convert(value: T): RequestBody {
            val buffer = Buffer()
            val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
            val jsonWriter = gson.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.close()
            return buffer.readByteString().toRequestBody(MEDIA_TYPE)
        }

        companion object {
            private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
            private val UTF_8 = Charset.forName("UTF-8")
        }
    }
}