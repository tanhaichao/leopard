package io.leopard.burrow.lang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class AssertUtilTest {

	
	@Test
	public void maxLength() {
		try {
			AssertUtil.maxLength("", 3, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		AssertUtil.maxLength("str", 3, "");
		try {
			AssertUtil.maxLength("str", 2, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void notNull() {
		AssertUtil.notNull("", "");
		try {
			AssertUtil.notNull(null, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertNotNull() {
		AssertUtil.assertNotNull("", "");
		try {
			AssertUtil.assertNotNull(null, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertNotEmpty() {
		AssertUtil.assertNotEmpty("abc", "");
		try {
			AssertUtil.assertNotEmpty((String) null, "");

			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			AssertUtil.assertNotEmpty("", "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertNotEmpty2() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		AssertUtil.assertNotEmpty(list, "");
		try {
			AssertUtil.assertNotEmpty((List<String>) null, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			AssertUtil.assertNotEmpty(new ArrayList<String>(), "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertNotEmpty3() {
		Set<String> set = new HashSet<String>();
		set.add("a");
		AssertUtil.assertNotEmpty(set, "");
		try {
			AssertUtil.assertNotEmpty((Set<String>) null, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			AssertUtil.assertNotEmpty(new HashSet<String>(), "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void empty() {
		AssertUtil.empty("abc", "message");
		try {
			AssertUtil.empty((String) null, "");

			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void isEmpty() {
		List<String> list = new ArrayList<String>();
		list.add("a");
		AssertUtil.isEmpty(list, "");
		try {
			AssertUtil.isEmpty(null, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertTrue() {
		AssertUtil.assertTrue(true, "");
		try {
			AssertUtil.assertTrue(false, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void greatZero() {
		AssertUtil.greatZero(new Integer(1), "");
		AssertUtil.greatZero(new Long(1), "");

		{
			try {
				AssertUtil.greatZero((Integer) null, "");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
			try {
				AssertUtil.greatZero(new Integer(0), "");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
		{
			try {
				AssertUtil.greatZero((Long) null, "");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
			try {
				AssertUtil.greatZero(new Long(0), "");
				Assert.fail("怎么没有抛异常?");
			}
			catch (IllegalArgumentException e) {

			}
		}
	}

	@Test
	public void isGreaterZero() {
		AssertUtil.isGreaterZero(1, "");
	}

	@Test
	public void isGreaterEqualZero() {
		AssertUtil.isGreaterEqualZero(0, "");
		AssertUtil.isGreaterEqualZero(1, "");
		try {
			AssertUtil.isGreaterEqualZero(-1, "");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void equalsIgnoreCase() {
		AssertUtil.equalsIgnoreCase("a", "a", "message");
		AssertUtil.equalsIgnoreCase("a", "A", "message");
		try {
			AssertUtil.equalsIgnoreCase("b", "a", "message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void isEquals() {
		AssertUtil.isEquals(1, 1, "message");
		try {
			AssertUtil.isEquals(2, 1, "message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void assertFieldName() {
		AssertUtil.assertFieldName("abc", "message");
		try {
			AssertUtil.assertFieldName(null, "message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			AssertUtil.assertFieldName("_a", "message");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void AssertUtil() {
		new AssertUtil();
	}
}