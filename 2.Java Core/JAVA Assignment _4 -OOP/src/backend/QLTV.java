package backend;

import entity.Bao;
import entity.Sach;
import entity.TaiLieu;
import entity.TapChi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLTV implements IQLTV {

    private List<TaiLieu> taiLieuList;
    private Scanner sc = new Scanner(System.in);

    public QLTV() {
        taiLieuList = new ArrayList<>();

        taiLieuList.add(new Sach("S01", "NXB Kim Dong", 100, "Nguyen Nhat Anh", 250));
        taiLieuList.add(new Sach("S02", "NXB Tre", 150, "To Hoai", 300));

        taiLieuList.add(new TapChi("TC01", "NXB Thanh Nien", 50, 12, 9));
        taiLieuList.add(new TapChi("TC02", "NXB Lao Dong", 70, 25, 8));

        taiLieuList.add(new Bao("B01", "NXB Ha Noi", 200, LocalDate.of(2026, 9, 27)));
    }

    // Question 1: Thêm tài liệu mới
    @Override
    public void themTaiLieu() {
        System.out.println("==== THÊM TÀI LIỆU MỚI ====");
        // Nhập dữ liệu chung
        System.out.print("Nhập mã tài liệu: ");
        String maTaiLieu = sc.nextLine();
        System.out.print("Nhập tên nhà xuất bản: ");
        String tenNhaXuatBan = sc.nextLine();
        System.out.print("Nhập số bản phát hành: ");
        int soBanPhatHanh = sc.nextInt();
        sc.nextLine();

        // Chọn loại tài liệu
        System.out.print("Nhập loại tài liệu: 1. Sách   2. Tạp chí   3. Báo");
        String loaiTaiLieu = sc.nextLine();
        TaiLieu taiLieu;
        switch (loaiTaiLieu) {
            // 1. SÁCH
            case "1":
                System.out.println("==== NHẬP THÔNG TIN SÁCH ====");
                System.out.print("Nhập tên tác giả: ");
                String tenTacGia = sc.nextLine();
                System.out.print("Nhập số trang: ");
                int soTrang = sc.nextInt();
                sc.nextLine();
                taiLieu = new Sach(maTaiLieu, tenNhaXuatBan, soBanPhatHanh, tenTacGia, soTrang);
                break;
            // 2. TẠP CHÍ
            case "2":
                System.out.println("==== NHẬP THÔNG TIN TẠP CHÍ ====");
                System.out.print("Nhập số phát hành: ");
                int soPhatHanh = sc.nextInt();
                System.out.print("Nhập tháng phát hành: ");
                int thangPhatHanh = sc.nextInt();
                sc.nextLine();
                taiLieu = new TapChi(maTaiLieu, tenNhaXuatBan, soBanPhatHanh, soPhatHanh, thangPhatHanh);
                break;
            // 3. BÁO
            case "3":
                System.out.println("==== NHẬP THÔNG TIN BÁO ====");
                System.out.print("Nhập ngày phát hành (dd/MM/yyyy): ");
                String ngayPhatHanh = sc.nextLine();
                LocalDate localDate = LocalDate.parse(ngayPhatHanh, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                taiLieu = new Bao(maTaiLieu, tenNhaXuatBan, soBanPhatHanh, localDate);
                break;
            // Nhập sai loại
            default:
                System.out.println("Loại tài liệu không hợp lệ!");
                return;
        }

        // Thêm tài liệu vào danh sách
        taiLieuList.add(taiLieu);
        System.out.println("Thêm tài liệu thành công!");
    }

    // Question 2: Xóa tài liệu theo mã
    @Override
    public void xoaTheoMa() {
        System.out.println("==== XÓA TÀI LIỆU THEO MÃ ====");
        // Nhập mã tài liệu cần xóa
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String maTaiLieu = sc.nextLine();

        // Tìm các tài liệu có mã cần xóa -> cho vào 1 list tạm
        List<TaiLieu> removes = new ArrayList<>();
        for (TaiLieu taiLieu : taiLieuList) {
            if (taiLieu.getMaTaiLieu().equals(maTaiLieu)) {
                removes.add(taiLieu);
            }
        }

        // Nếu không tìm thấy tài liệu
        if (removes.isEmpty()) {
            System.out.println("Không tìm thấy tài liệu có mã: " + maTaiLieu);
        } else {
            // Xóa các phần tử trong list tạm khỏi taiLieuList
            taiLieuList.removeAll(removes);
            System.out.println("Xóa thành công!");
        }
    }

    // Question 3: Hiển thị thông tin toàn bộ tài liệu
    @Override
    public void hienThiDanhSach() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ TÀI LIỆU ====");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");

        System.out.printf(
                "|%8s|%20s|%12s|%12s|%32s|\n",
                "Mã TL",
                "Nhà xuất bản",
                "Số bản PH",
                "Loại",
                "Thông tin thêm"
        );

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");

        for (TaiLieu taiLieu : taiLieuList) {
            String loai = "";
            String thongTinThem = "";
            if (taiLieu instanceof Sach) {
                Sach sach = (Sach) taiLieu;
                loai = "Sách";
                thongTinThem = "TG: " + sach.getTacGia() + ", " + sach.getSoTrang() + " trang";

            } else if (taiLieu instanceof TapChi) {
                TapChi tapChi = (TapChi) taiLieu;
                loai = "Tạp chí";
                thongTinThem = "Số PH: " + tapChi.getSoPhatHanh() + ", tháng: " + tapChi.getThangPhatHanh();

            } else if (taiLieu instanceof Bao) {
                Bao bao = (Bao) taiLieu;
                loai = "Báo";
                thongTinThem = "Ngày PH: " + bao.getNgayPhatHanh().format(formatter);
            }

            System.out.printf(
                    "|%8s|%20s|%12d|%12s|%32s|\n",
                    taiLieu.getMaTaiLieu(),
                    taiLieu.getTenNhaXuatBan(),
                    taiLieu.getSoBanPhatHanh(),
                    loai,
                    thongTinThem
            );
        }

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");
    }

    // Question 4: Tìm kiếm tài liệu theo loại

    @Override
    public void timKiemTheoLoai() {
        System.out.println("==== TÌM KIẾM TÀI LIỆU THEO LOẠI ====");
        System.out.println("1. Sách");
        System.out.println("2. Tạp chí");
        System.out.println("3. Báo");
        System.out.print("Nhập loại tài liệu muốn tìm: ");
        String choice = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        boolean found = false;

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");

        System.out.printf(
                "|%8s|%20s|%12s|%12s|%32s|\n",
                "Mã TL",
                "Nhà xuất bản",
                "Số bản PH",
                "Loại",
                "Thông tin thêm"
        );

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");

        for (TaiLieu taiLieu : taiLieuList) {
            String loai = "";
            String thongTinThem = "";
            switch (choice) {
                case "1":
                    if (taiLieu instanceof Sach) {
                        Sach sach = (Sach) taiLieu;
                        loai = "Sách";
                        thongTinThem = "TG: " + sach.getTacGia() + ", " + sach.getSoTrang() + " trang";
                        found = true;
                    }
                    break;

                case "2":
                    if (taiLieu instanceof TapChi) {
                        TapChi tapChi = (TapChi) taiLieu;
                        loai = "Tạp chí";
                        thongTinThem = "Số PH: " + tapChi.getSoPhatHanh() + ", tháng: " + tapChi.getThangPhatHanh();
                        found = true;
                    }
                    break;

                case "3":
                    if (taiLieu instanceof Bao) {
                        Bao bao = (Bao) taiLieu;
                        loai = "Báo";
                        thongTinThem = "Ngày PH: " + bao.getNgayPhatHanh().format(formatter);
                        found = true;
                    }
                    break;

                default:
                    System.out.println("Chọn sai loại tài liệu!");
                    return;
            }

            if (!loai.equals("")) {
                System.out.printf(
                        "|%8s|%20s|%12d|%12s|%32s|\n",
                        taiLieu.getMaTaiLieu(),
                        taiLieu.getTenNhaXuatBan(),
                        taiLieu.getSoBanPhatHanh(),
                        loai,
                        thongTinThem
                );
            }
        }

        System.out.println("+--------+--------------------+------------+------------+--------------------------------+");

        if (!found) {
            System.out.println("Không tìm thấy tài liệu thuộc loại này.");
        }
    }
}