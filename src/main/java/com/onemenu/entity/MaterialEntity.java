package com.onemenu.entity;

public class MaterialEntity {
    private long id;
    private String name;
    private int quality;
    private int material0;
    private int material1;
    private int material2;
    private int material3;
    private int material4;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }
    public int getMaterial0() {
        return material0;
    }

    public void setMaterial0(int material0) {
        this.material0 = material0;
    }

    public int getMaterial1() {
        return material1;
    }

    public void setMaterial1(int material1) {
        this.material1 = material1;
    }

    public int getMaterial2() {
        return material2;
    }

    public void setMaterial2(int material2) {
        this.material2 = material2;
    }

    public int getMaterial3() {
        return material3;
    }

    public void setMaterial3(int material3) {
        this.material3 = material3;
    }

    public int getMaterial4() {
        return material4;
    }

    public void setMaterial4(int material4) {
        this.material4 = material4;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", quality=" + quality +
                '}';
    }
}
