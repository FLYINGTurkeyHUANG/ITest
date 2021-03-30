package com.bj58.hds.util.test.java.com.bj58.hds.util.json;



import com.bj58.hds.util.json.JSONArray;
import com.bj58.hds.util.json.JSONObject;
import com.bj58.hds.util.json.JSONParser;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 单测原则:
 * 1、每一个测试方法上使用@Test进行修饰
 *
 * 2、每一个测试方法必须使用public void 进行修饰
 *
 * 3、每一个测试方法不能携带参数
 *
 * 4、测试代码和源代码在两个不同的项目路径下
 *
 * 5、测试类的包应该和被测试类保持一致
 *
 * 6、测试单元中的每个方法必须可以独立测试
 *
 * */
public class JSONParserTest {
//    public static void main(String[] args) {
//
//    }

    @Test
    public void testFromJSON() throws Exception {
        String path = this.getClass().getResource("/testjson.json").getFile();
        String json = new String(Files.readAllBytes(Paths.get(path)), "utf-8");
//       String json = getJSON();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.fromJSON(json);
        System.out.println(jsonObject);

        JSONObject playlist = jsonObject.getJSONObject("playlist");
        assertEquals(52, playlist.get("commentCount"));
        assertEquals(19208468137575293L, playlist.get("coverImgId"));
        assertEquals("2017年八月最热新歌TOP50", playlist.get("name"));
        assertFalse((Boolean) playlist.get("highQuality"));

        JSONArray trackIds = playlist.getJSONArray("trackIds");
        assertEquals(50, trackIds.size());
        JSONObject trackId = trackIds.getJSONObject(7);
        assertEquals(499274374, trackId.get("id"));
        assertEquals(14, trackId.get("v"));

        JSONArray tracks = playlist.getJSONArray("tracks");
        JSONObject track3 = tracks.getJSONObject(3);
        assertEquals("带你去旅行", track3.get("name"));
        assertEquals(4, track3.get("v"));
        JSONObject track17 = tracks.getJSONObject(17);
        assertEquals("EVERYDAY", track17.get("name"));
        assertEquals(null, track17.get("a"));
        assertEquals(5619229, track17.get("mv"));
    }

    @Test
    public void testFromJSON1() throws Exception {
        String json = "{\"a\": 1, \"b\": \"b\", \"c\": {\"a\": 1, \"b\": null, \"d\": [0.1, \"a\", 1,2, 123, 1.23e+10, true, false, null]}}";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.fromJSON(json);
        System.out.println(jsonObject);

        assertEquals(1, jsonObject.get("a"));
        assertEquals("b", jsonObject.get("b"));

        JSONObject c = jsonObject.getJSONObject("c");
        assertEquals(null, c.get("b"));

        JSONArray d = c.getJSONArray("d");
        assertEquals(0.1, d.get(0));
        assertEquals("a", d.get(1));
        assertEquals(123, d.get(4));
        assertEquals(1.23e+10, d.get(5));
        assertTrue((Boolean) d.get(6));
        assertFalse((Boolean) d.get(7));
        assertEquals(null, d.get(8));
    }

    @Test
    public void testFromJSON2() throws Exception {
        String json = "[[1,2,3,\"\u4e2d\"]]";
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray) jsonParser.fromJSON(json);
        System.out.println(jsonArray);
    }

    @Test
    public void testBeautifyJSON() throws Exception {
        String json = "{\"name\": \"狄仁杰\", \"type\": \"射手\", \"ability\":[\"六令追凶\",\"逃脱\",\"王朝密令\"],\"history\":{\"DOB\":630,\"DOD\":700,\"position\":\"宰相\",\"dynasty\":\"唐朝\"}}";
        System.out.println("原 JSON 字符串：");
        System.out.println(json);
        System.out.println("\n");
        System.out.println("美化后的 JSON 字符串：");
        JSONParser jsonParser = new JSONParser();
        JSONObject drj = (JSONObject) jsonParser.fromJSON(json);
        System.out.println(drj);
    }

}
