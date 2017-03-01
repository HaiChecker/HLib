package com.haichecker.lib.widget.settinglist.item;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-6 14:38
 */

public enum ItemGravity {
    TOP("TOP", 48), BOTTOM("BOTTOM", 80), LEFT("LEFT", 3), RIGHT("RIGHT", 5);
    private String name;
    private int value;

    private ItemGravity(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // 普通方法
    public static String getName(int index) {
        for (ItemGravity c : ItemGravity.values()) {
            if (c.getValue() == index) {
                return c.getName();
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
