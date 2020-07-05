package com.crackpot.easy_permission;

public class ModelPermission {
    private String color, title, description, permissionSwitch, permissionName;
    private int image, permissionCode;
    public ModelPermission(int image, String color, String title, String description, String permissionSwitch, String permissionName, int permissionCode) {
        this.image = image;
        this.color = color;
        this.title = title;
        this.description = description;
        this.permissionSwitch = permissionSwitch;
        this.permissionName = permissionName;
        this.permissionCode = permissionCode;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissionSwitch() {
        return permissionSwitch;
    }

    public void setPermissionSwitch(String permissionSwitch) {
        this.permissionSwitch = permissionSwitch;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(int permissionCode) {
        this.permissionCode = permissionCode;
    }
}
