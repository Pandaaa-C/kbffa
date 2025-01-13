package com.panda0day.kbffa.addons;

public enum AddonNames {
    DOUBLE_JUMP("DOUBLE_JUMP"),
    KNOCKBACK_TEN("KNOCKBACK_TEN");

    private final String displayName;

    AddonNames(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
