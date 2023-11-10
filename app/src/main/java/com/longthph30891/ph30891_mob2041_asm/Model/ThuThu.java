package com.longthph30891.ph30891_mob2041_asm.Model;

public class ThuThu {
    private String maTT;
    private String hoTenTT;
    private String matKhau;

    public ThuThu() {
    }

    public ThuThu(String maTT, String hoTenTT, String matKhau) {
        this.maTT = maTT;
        this.hoTenTT = hoTenTT;
        this.matKhau = matKhau;
    }

    public String getMaTT() {
        return maTT;
    }

    public ThuThu setMaTT(String maTT) {
        this.maTT = maTT;
        return this;
    }

    public String getHoTenTT() {
        return hoTenTT;
    }

    public ThuThu setHoTenTT(String hoTenTT) {
        this.hoTenTT = hoTenTT;
        return this;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public ThuThu setMatKhau(String matKhau) {
        this.matKhau = matKhau;
        return this;
    }
}
