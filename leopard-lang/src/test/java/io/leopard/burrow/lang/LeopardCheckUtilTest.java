package io.leopard.burrow.lang;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import io.leopard.burrow.util.ListUtil;
import io.leopard.core.exception.ForbiddenException;
import io.leopard.core.exception.forbidden.IpForbiddenException;
import io.leopard.core.exception.invalid.DateInvalidException;
import io.leopard.core.exception.invalid.DateTimeInvalidException;
import io.leopard.core.exception.invalid.IpInvalidException;
import io.leopard.core.exception.invalid.UidInvalidException;
import io.leopard.core.exception.invalid.UsernameInvalidException;
import io.leopard.core.exception.other.NotLoginException;

public class LeopardCheckUtilTest {

	@Test
	public void isUsername() {
		LeopardCheckUtil.isUsername("username");
		try {
			LeopardCheckUtil.isUsername("Abc");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UsernameInvalidException e) {

		}
	}

	@Test
	public void isLogined() {
		LeopardCheckUtil.isLogined("username", "127.0.0.1");
		try {
			LeopardCheckUtil.isLogined("", "127.0.0.1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (NotLoginException e) {

		}
	}

	@Test
	public void isAllowByUsername() throws ForbiddenException {
		LeopardCheckUtil.isAllowByUsername(true, "127.0.0.1");
		try {
			LeopardCheckUtil.isAllowByUsername(false, "127.0.0.1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (ForbiddenException e) {

		}
	}

	@Test
	public void isAllowByIp() throws IpForbiddenException {
		LeopardCheckUtil.isAllowByIp(true, "127.0.0.1");
		try {
			LeopardCheckUtil.isAllowByIp(false, "127.0.0.1");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IpForbiddenException e) {

		}
	}

	@Test
	public void isValidUsername() {
		LeopardCheckUtil.isValidUsername("username");
		try {
			LeopardCheckUtil.isValidUsername("Abc");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UsernameInvalidException e) {

		}
	}

	@Test
	public void isValidUid() {
		LeopardCheckUtil.isValidUid(1);
		try {
			LeopardCheckUtil.isValidUid(0);
			Assert.fail("怎么没有抛异常?");
		}
		catch (UidInvalidException e) {

		}
	}

	@Test
	public void isValidPassport() {
		LeopardCheckUtil.isValidPassport("username");
		try {
			LeopardCheckUtil.isValidPassport("Abc");
			Assert.fail("怎么没有抛异常?");
		}
		catch (UsernameInvalidException e) {

		}
	}

	@Test
	public void isValidJson() {
		LeopardCheckUtil.isValidJson("{}");
		try {
			LeopardCheckUtil.isValidJson(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}

	@Test
	public void isValidList() {
		LeopardCheckUtil.isValidList(ListUtil.makeList("a,b"));
		try {
			LeopardCheckUtil.isValidList(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
		try {
			LeopardCheckUtil.isValidList(new ArrayList<String>());
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkIp() {
		LeopardCheckUtil.checkIp("127.0.0.1");
		try {
			LeopardCheckUtil.checkIp("");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IpInvalidException e) {

		}

	}

	@Test
	public void isValidIp() {
		LeopardCheckUtil.isValidIp("127.0.0.1");
		try {
			LeopardCheckUtil.isValidIp("");
			Assert.fail("怎么没有抛异常?");
		}
		catch (IpInvalidException e) {

		}
	}

	@Test
	public void isValidUsernameList() {
		LeopardCheckUtil.isValidUsernameList(ListUtil.makeList("a,b"));
		try {
			LeopardCheckUtil.isValidUsernameList(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (NullPointerException e) {

		}
		try {
			LeopardCheckUtil.isValidUsernameList(ListUtil.makeList("a,B"));
			Assert.fail("怎么没有抛异常?");
		}
		catch (UsernameInvalidException e) {

		}
	}

	@Test
	public void isValidPageId() {
		LeopardCheckUtil.isValidPageId(1);
		try {
			LeopardCheckUtil.isValidPageId(null);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			LeopardCheckUtil.isValidPageId(0);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void isDate() {
		LeopardCheckUtil.isDate("2013-01-01");
		try {
			LeopardCheckUtil.isDate("2013-01-01 00:00:00");
			Assert.fail("怎么没有抛异常?");
		}
		catch (DateInvalidException e) {

		}
	}

	@Test
	public void isValidPageParameter() {
		LeopardCheckUtil.isValidPageParameter(0, 10);
		try {
			LeopardCheckUtil.isValidPageParameter(-1, 10);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
		try {
			LeopardCheckUtil.isValidPageParameter(0, 0);
			Assert.fail("怎么没有抛异常?");
		}
		catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void isValidDateTime() {
		LeopardCheckUtil.isValidDateTime("2013-01-01 00:00:00");
		try {
			LeopardCheckUtil.isValidDateTime("2013-01-01");
			Assert.fail("怎么没有抛异常?");
		}
		catch (DateTimeInvalidException e) {

		}
	}

	@Test
	public void LeopardCheckUtil() {
		new LeopardCheckUtil();
	}
}