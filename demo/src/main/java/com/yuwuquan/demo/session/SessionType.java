package com.yuwuquan.demo.session;

/**
 * @author lenovo
 *
 */
public enum SessionType {
	/**
	 * 不需要
	 */
	NONE,
	/**
	 * 必须
	 */
	MUST,
	/**
	 * 允许任何访问
	 */
	ANY,
	/**
	 * 只允许局域网
	 */
	INNER
}
