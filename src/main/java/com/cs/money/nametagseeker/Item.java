package com.cs.money.nametagseeker;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class Item {
	
	private List<String> id;
	private Double p;
	private Integer o;
	private List<String> n;
	private List<String> ui;
		
	public String toString() {
		return "\nPrice: " + p + ", Nametag: " + filterNametags();
	}

	private String filterNametags() {
		return n.stream().filter(elem -> !elem.equals(String.valueOf(0))).collect(Collectors.toList()).toString();
	}
	
}