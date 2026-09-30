package frontend;

import backend.IQLTV;
import backend.QLTV;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {

        Scanner sc = new Scanner(System.in);

        IQLTV iqltv = new QLTV();

        while (true) {

            System.out.println("==== Mời bạn chọn chức năng ====");
            System.out.println("1. Thêm tài liệu.");
            System.out.println("2. Xóa tài liệu theo mã.");
            System.out.println("3. Hiển thị toàn bộ tài liệu.");
            System.out.println("4. Tìm kiếm tài liệu theo loại.");
            System.out.println("5. Thoát khỏi chương trình.");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    iqltv.themTaiLieu();
                    break;

                case "2":
                    iqltv.xoaTheoMa();
                    break;

                case "3":
                    iqltv.hienThiDanhSach();
                    break;

                case "4":
                    iqltv.timKiemTheoLoai();
                    break;

                case "5":
                    System.out.println("Thoát.");
                    System.exit(0);

                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }
}