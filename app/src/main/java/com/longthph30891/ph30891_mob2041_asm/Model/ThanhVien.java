package com.longthph30891.ph30891_mob2041_asm.Model;

public class ThanhVien {
    private int  maTV;
    private String hoTenTV;
    private String namSinh;

    public ThanhVien(int maTV, String hoTenTV, String namSinh) {
        this.maTV = maTV;
        this.hoTenTV = hoTenTV;
        this.namSinh = namSinh;
    }

    public ThanhVien(String hoTenTV, String namSinh) {
        this.hoTenTV = hoTenTV;
        this.namSinh = namSinh;
    }

    public ThanhVien() {
    }

    public int getMaTV() {
        return maTV;
    }

    public ThanhVien setMaTV(int maTV) {
        this.maTV = maTV;
        return this;
    }

    public String getHoTenTV() {
        return hoTenTV;
    }

    public ThanhVien setHoTenTV(String hoTenTV) {
        this.hoTenTV = hoTenTV;
        return this;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public ThanhVien setNamSinh(String namSinh) {
        this.namSinh = namSinh;
        return this;
    }
}
