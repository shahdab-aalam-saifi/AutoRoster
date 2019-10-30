package com.saalamsaifi.auto.roster.model;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class LogEvent {
	private String type;
	private String modifier;
	private String name;
	private String returns;
	private List<String> args;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = Modifier.toString(modifier);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = new ArrayList<>();

		if (args.length > 0) {
			for (Object arg : args) {
				this.args.add(arg.getClass().getTypeName());
			}
		}
	}

	public String getReturns() {
		return returns;
	}

	public void setReturns(String returns) {
		this.returns = returns;
	}

	@Override
	public String toString() {
		return new JSONObject(this).toString();
	}
}
