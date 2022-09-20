package test.karpenko.newsapp.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import test.karpenko.newsapp.models.Article
import test.karpenko.newsapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name:String?): Source{
        return Source(name = name, id = name)
    }

}