package com.example.quizbuilder.utils

import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import org.springframework.http.converter.json.MappingJacksonValue

class Util {

    companion object {
        fun applyFilterOut(jsonObject: Any, filterId: String, vararg propertyArray: String): MappingJacksonValue {
            val filter: SimpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(*propertyArray)
            val filters: FilterProvider = SimpleFilterProvider().addFilter(filterId, filter)
            val mapping = MappingJacksonValue(jsonObject)
            mapping.filters = filters
            return mapping
        }

        fun applyFilterIn(jsonObject: Any, filterId: String, vararg propertyArray: String): MappingJacksonValue {
            val filter: SimpleBeanPropertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(*propertyArray)
            val filters: FilterProvider = SimpleFilterProvider().addFilter(filterId, filter)
            val mapping = MappingJacksonValue(jsonObject)
            mapping.filters = filters
            return mapping
        }
    }
}