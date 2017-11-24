package com.stq.service.stq.stats;

import com.alibaba.fastjson.JSONObject;
import com.stq.entity.stq.stats.WeixinAccountStat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class WeixinKolServiceTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetWeixinKol() throws Exception {
    WeixinAccountStatService service = new WeixinAccountStatService();
    List<WeixinAccountStat> result = service.getWeixinKol("2017-10-01", "2017-10-11", new String[]{
            "MzA4NDI3NjcyNA==",
            "MjM5MTgzMTAwMA==",
            "MzA3NTI4NjYxNw==",
            "MjM5MTMwMTc4NQ=="
    });
    result.stream().forEach(System.out::println);
  }

  @Test
  public void testGetWeixinKolFromEmptyBiz() throws Exception {
    WeixinAccountStatService service = new WeixinAccountStatService();
    List<WeixinAccountStat> result = service.getWeixinKol("2017-10-01", "2017-10-11", new String[]{});
    result.stream().forEach(System.out::println);
    assertEquals(0, result.size());
  }

  @Test
  public void testGetAccountInfo() {
    JSONObject obj = WeixinAccountStatService.getAccountInfo("MzA4NDI3NjcyNA==");
    System.out.println(obj);
  }

}
