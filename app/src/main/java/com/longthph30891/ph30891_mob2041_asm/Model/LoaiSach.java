package com.longthph30891.ph30891_mob2041_asm.Model;

public class LoaiSach {
    private int maTheLoai;
    private String tenTheLoai;

    public LoaiSach(int maTheLoai, String tenTheLoai) {
        this.maTheLoai = maTheLoai;
        this.tenTheLoai = tenTheLoai;
    }

    public LoaiSach(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public LoaiSach() {
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public LoaiSach setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
        return this;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public LoaiSach setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
        return this;
    }
}
