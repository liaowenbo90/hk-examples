package com.hk.example2;

import com.hk.commons.poi.excel.annotations.NestedProperty;
import com.hk.commons.poi.excel.annotations.ReadExcelField;
import com.hk.commons.poi.excel.annotations.WriteExcelField;
import com.hk.example1.ExcelVo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author kevin
 * @date 2018年1月16日上午10:48:15
 */
public class NestedExcelVo {

	@WriteExcelField(index = 5, value = "id")
	@ReadExcelField(start = 5)
	@NotBlank(message = "id不能为空")
	private String id;

	/**
	 * 注意，如果嵌套属性也需要验证，必须加上 {@link Valid} 注解
	 */
	@Valid
	@NestedProperty
	private ExcelVo user;

	/**
	 * 
	 */
	public NestedExcelVo() {
	}

	/**
	 * @param id
	 * @param user
	 */
	public NestedExcelVo(String id, ExcelVo user) {
		this.id = id;
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public ExcelVo getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(ExcelVo user) {
		this.user = user;
	}

}
