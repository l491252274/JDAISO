package com.unlimited.oj.enums;

/**
* 小于100的为目录节点，大于等于100的为叶子节点
* 目录节点有：
* 1一般目录项；
 *
* 叶子节点有：
* 100题目叶子
*/
public enum TreeNodeType {
    NormalCatalog("enums.TreeNodeType.NormalCatalog", 1),
    ProblemNode("enums.TreeNodeType.ProblemNode", 100);

	private String name;
	private int value;

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	TreeNodeType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public static TreeNodeType getTreeNodeType(int i) {
		switch (i) {
		case 1:
			return NormalCatalog;
		case 100:
			return ProblemNode;
		default:
			return null;
		}
	}
}
