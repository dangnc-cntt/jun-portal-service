package com.jun.portalservice.domain.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.base.CaseFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An utility class for parsing json objects using Jackson data binding library
 *
 * @author Artemii Vishnevskii
 * @author Temaa.mann@gmail.com
 * @since 12.04.2016.
 */
@Log4j2
public class JsonParser {

  private static ObjectMapper mObjectMapper;

  /**
   * Creates an {@link ObjectMapper} for mapping json objects. Mapper can be configured here
   *
   * @return created {@link ObjectMapper}
   */
  private static ObjectMapper getMapper() {
    if (mObjectMapper == null) {
      mObjectMapper = new ObjectMapper();
      mObjectMapper
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .registerModule(new ParameterNamesModule())
          .registerModule(new Jdk8Module())
          .registerModule(new JavaTimeModule());
    }
    return mObjectMapper;
  }

  /**
   * Maps json string to specified class
   *
   * @param json string to parse
   * @param tClass class of object in which json will be parsed
   * @param <T> generic parameter for tClass
   * @return mapped T class instance
   * @throws IOException
   */
  public static <T> T entity(String json, Class<T> tClass) throws IOException {
    return getMapper().readValue(json, tClass);
  }

  /**
   * Maps json string to {@link ArrayList} of specified class object instances
   *
   * @param json string to parse
   * @param tClass class of object in which json will be parsed
   * @param <T> generic parameter for tClass
   * @return mapped T class instance
   * @throws IOException
   */
  public static <T> ArrayList<T> arrayList(String json, Class<T> tClass) throws IOException {
    TypeFactory typeFactory = getMapper().getTypeFactory();
    JavaType type = typeFactory.constructCollectionType(ArrayList.class, tClass);
    return getMapper().readValue(json, type);
  }

  /**
   * Writes specified object as string
   *
   * @param object object to write
   * @return result json
   * @throws IOException
   */
  public static String toJson(Object object) {
    try {
      return getMapper().writeValueAsString(object);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Convert int[] to ArrayList<Integer></>
   *
   * @param ints
   * @return
   */
  public static ArrayList<Integer> intArrayList(int[] ints) {
    return IntStream.of(ints).boxed().collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * @param object
   * @return
   */
  public static MultiValueMap<String, String> objectToMap(Object object) {
    MultiValueMap parameters = new LinkedMultiValueMap();
    Map<String, String> maps =
        getMapper().convertValue(object, new TypeReference<Map<String, String>>() {});
    parameters.setAll(maps);
    return parameters;
  }

  public static String toSnack(String camelCase) {
    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCase);
  }
  /**
   * @param object
   * @return
   */
  public static MultiValueMap<String, String> objectToMapSnackCase(Object object) {
    MultiValueMap parameters = new LinkedMultiValueMap();
    Map<String, String> maps =
        getMapper().convertValue(object, new TypeReference<Map<String, String>>() {});

    Map<String, String> mapSnack = new HashMap<>();
    for (Map.Entry<String, String> entry : maps.entrySet()) {
      mapSnack.put(toSnack(entry.getKey()), entry.getValue());
    }
    parameters.setAll(mapSnack);
    return parameters;
  }
}
