package com.longthph30891.ph30891_mob2041_asm.Model;

public class PhieuMuon {
    private int maPhieu;
    private int maSach;
    private String maTT;
    private int maTV;
    private int tienThue;
    private String ngayMuon;
    private int trangThai;

    public PhieuMuon(int maPhieu, int maSach, String maTT, int maTV, int tienThue, String ngayMuon, int trangThai) {
        this.maPhieu = maPhieu;
        this.maSach = maSach;
        this.maTT = maTT;
        this.maTV = maTV;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
    }

    public PhieuMuon() {
    }

    public PhieuMuon(int maSach, String maTT, int maTV, int tienThue, String ngayMuon, int trangThai) {
        this.maSach = maSach;
        this.maTT = maTT;
        this.maTV = maTV;
        this.tienThue = tienThue;
        this.ngayMuon = ngayMuon;
        this.trangThai = trangThai;
    }

    public int getMaPhieu() {

        return maPhieu;
    }

    public PhieuMuon setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
        return this;
    }

    public int getMaSach() {
        return maSach;
    }

    public PhieuMuon setMaSach(int maSach) {
        this.maSach = maSach;
        return this;
    }

    public String getMaTT() {
        return maTT;
    }

    public PhieuMuon setMaTT(String maTT) {
        this.maTT = maTT;
        return this;
    }

    public int getMaTV() {
        return maTV;
    }

    public PhieuMuon setMaTV(int maTV) {
        this.maTV = maTV;
        return this;
    }

    public int getTienThue() {
        return tienThue;
    }

    public PhieuMuon setTienThue(int tienThue) {
        this.tienThue = tienThue;
        return this;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public PhieuMuon setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
        return this;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public PhieuMuon setTrangThai(int trangThai) {
        this.trangThai = trangThai;
        return this;
    }
}
