package com.avaje.tests.json.include;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.config.JsonConfig;
import com.avaje.ebean.text.json.JsonWriteOptions;
import com.avaje.tests.json.transientproperties.EJsonTransientList;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestJsonExcludeTransientEmptyList {

  @Test
  public void testToBeanToJson_NonNull() throws Exception {

    EJsonTransientList bean = new EJsonTransientList();
    bean.setId(99L);
    bean.setFileNames(new ArrayList<String>());

    JsonWriteOptions options = new JsonWriteOptions();
    options.setInclude(JsonConfig.Include.NON_NULL);

    String asJson = Ebean.json().toJson(bean, options);

    String expectedJson = "{\"id\":99,\"fileNames\":[]}";

    assertEquals(expectedJson, asJson);
  }

  @Test
  public void testToBeanToJson_NonEmpty() throws Exception {

    EJsonTransientList bean = new EJsonTransientList();
    bean.setId(99L);
    bean.setFileNames(new ArrayList<String>());

    JsonWriteOptions options = new JsonWriteOptions();
    options.setInclude(JsonConfig.Include.NON_EMPTY);

    String asJson = Ebean.json().toJson(bean, options);

    String expectedJson = "{\"id\":99}";

    assertEquals(expectedJson, asJson);
  }
}
