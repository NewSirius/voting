package com.newsirius.voting;

import com.newsirius.voting.web.json.JsonUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.newsirius.voting.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class TestUtil {

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

    public static <T> T readFromJson(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return JsonUtil.readValue(getContent(action), clazz);
    }

    public static <T> ResultMatcher contentJson(T expected) {
        return content().json(writeValue(expected));
    }

    public static <T> List<T> readFromJsonToList(ResultActions actions, Class<T> clazz) throws UnsupportedEncodingException, JSONException {
        JSONArray jsonArray = new JSONArray(getContent(actions));

        List<T> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(JsonUtil.readValue(jsonArray.getString(i), clazz));
        }
        return result;
    }
}
