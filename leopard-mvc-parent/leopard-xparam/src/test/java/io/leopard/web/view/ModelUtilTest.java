package io.leopard.web.view;

import io.leopard.json.Json;
import io.leopard.web.view.ModelUtil;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class ModelUtilTest {

	@Test
	public void ModelUtil() {
		new ModelUtil();
	}

	@Test
	public void get() {
		ModelAndView model = new ModelAndView();
		model.addObject("message", null);
		Assert.assertNull(ModelUtil.get(model, "message"));
		model.addObject("message", "message");
		Assert.assertEquals("message", ModelUtil.get(model, "message"));
		try {
			ModelUtil.get(model, "message1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void toObject() {
		String json = Json.toJson("text");

		ModelAndView model = new ModelAndView();
		model.addObject("message", json);
		Assert.assertEquals("text", ModelUtil.toObject(model, String.class));
	}

	@Test
	public void toJson() {
		String json = Json.toJson("text");
		ModelAndView model = new ModelAndView();
		model.addObject("message", json);
		Assert.assertEquals(json, ModelUtil.toJson(model));

		model.addObject("message", "");

		try {
			ModelUtil.toJson(model);
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void isSuccess() {
		ModelAndView model = new ModelAndView();
		model.addObject("success", true);
		Assert.assertTrue(ModelUtil.isSuccess(model));
	}

	@Test
	public void getInt() {
		ModelAndView model = new ModelAndView();
		model.addObject("num", 1);
		Assert.assertEquals(1, ModelUtil.getInt(model, "num"));
	}

	// @Test
	// public void getList() {
	//
	// ModelAndView model = new ModelAndView();
	// model.addObject("list", ListUtil.makeList("a,b"));
	// Assert.assertEquals(2, ModelUtil.getList(model, String.class, "list").size());
	// }
	//
	// @Test
	// public void getListForJson() {
	// String json = Json.toJson(ListUtil.makeList("a,b"));
	// ModelAndView model = new ModelAndView();
	// model.addObject("list", json);
	// Assert.assertEquals(2, ModelUtil.getListForJson(model, String.class, "list").size());
	// }

}