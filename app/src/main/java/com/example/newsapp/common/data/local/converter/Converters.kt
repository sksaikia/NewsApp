package com.example.newsapp.common.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.newsapp.common.data.remote.entity.Source

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun fromSource(source : Source) : String {
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source {
        return Source(name,name)
    }
}