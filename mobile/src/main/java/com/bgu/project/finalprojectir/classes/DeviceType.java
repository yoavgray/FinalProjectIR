package com.bgu.project.finalprojectir.classes;

/**
 * Created by Boaz on 05/04/2015.
 */
public enum DeviceType {
    TV("tv"), AC("ac");

    private final String type;

    DeviceType(String type) {
        this.type = type;
    }

    public static DeviceType fromString(String type) {
        if (type != null) {
            for (DeviceType deviceType : DeviceType.values()) {
                if (type.equalsIgnoreCase(deviceType.type)) {
                    return deviceType;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}
