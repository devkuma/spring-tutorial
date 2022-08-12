//import com.fasterxml.jackson.annotation.JsonAutoDetect
//import com.fasterxml.jackson.databind.DeserializationFeature
//import com.fasterxml.jackson.databind.MapperFeature
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.SerializationFeature
//
//private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(){
//
//    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//    objectMapper.configure(
//        MapperFeature.USE_ANNOTATIONS,
//        false
//    ); objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    objectMapper.configure(
//        SerializationFeature.FAIL_ON_EMPTY_BEANS,
//        false
//    );        // This item must be configured, otherwise it will be reported java.lang.ClassCastException : java.util.LinkedHashMap  cannot be cast to XXX
//
//    objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_ FINAL, JsonTypeInfo.As.PROPERTY);
//    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ NULL);
//    jackson2 JsonRedisSerializer . setObjectMapper (objectMapper);
//    return jackson2JsonRedisSerializer;
//}