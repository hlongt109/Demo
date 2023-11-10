package com.longthph30891.ph30891_mob2041_asm.Model;

public class Sach {
    private int maSach;
    private int maTheLoai;
    private String tenSach;
    private int giaThue;

    public Sach(int maSach, int maTheLoai, String tenSach, int giaThue) {
        this.maSach = maSach;
        this.maTheLoai = maTheLoai;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
    }

    public Sach( String tenSach, int giaThue,int maTheLoai) {
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maTheLoai = maTheLoai;
    }

    public Sach() {
    }

    public int getMaSach() {
        return maSach;
    }

    public Sach setMaSach(int maSach) {
        this.maSach = maSach;
        return this;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public Sach setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
        return this;
    }

    public String getTenSach() {
        return tenSach;
    }

    public Sach setTenSach(String tenSach) {
        this.tenSach = tenSach;
        return this;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public Sach setGiaThue(int giaThue) {
        this.giaThue = giaThue;
        return this;
    }
}
