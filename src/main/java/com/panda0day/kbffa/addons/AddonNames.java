package com.panda0day.kbffa.addons;

public enum AddonNames {
    DOUBLE_JUMP("DOUBLE_JUMP");

    private final String displayName;

    AddonNames(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
