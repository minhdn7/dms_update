package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class ResultDSKHChuaDH {
    public String makh;
    public String tenkh;
    public String diachi;
    public String tansuat; //Tan suat chekin
    public String tentuyen; //tuyen
    public String doanhso; //Doanh so thang gan nhat
    public String lastorder; //Ngay dat hang gan nhat
    //

    public ResultDSKHChuaDH(String ma, String ten, String dc, String tansuat, String tentuyen, String doanhso, String lastorder) {
        // TODO Auto-generated constructor stub
        this.makh = ma;
        this.tenkh = ten;
        this.diachi = dc;
        this.tansuat = tansuat;
        this.tentuyen = tentuyen;
        this.doanhso = doanhso;
        this.lastorder = lastorder;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getTansuat() {
        return tansuat;
    }

    public void setTansuat(String tansuat) {
        this.tansuat = tansuat;
    }

    public String getTentuyen() {
        return tentuyen;
    }

    public void setTentuyen(String tentuyen) {
        this.tentuyen = tentuyen;
    }

    public String getDoanhso() {
        return doanhso;
    }

    public void setDoanhso(String doanhso) {
        this.doanhso = doanhso;
    }

    public String getLastorder() {
        return lastorder;
    }

    public void setLastorder(String lastorder) {
        this.lastorder = lastorder;
    }
}
