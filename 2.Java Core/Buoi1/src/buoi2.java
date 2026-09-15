public class buoi2 {
    public static void main(String[] args) {
// Điều kiện
        // ví dụ về if else
        // vd1
        int number = 10;
        if (number > 0) {
            System.out.println(number + " là số dương");
        } else if (number < 0) {
            System.out.println(number + " là số âm");
        } else {
            System.out.println(number + " là số 0");
        }
        //vd2 ( nếu điểm >= 8 thì giỏi, >= 6 và <8  thì khá, >= 5 thì trung bình, < 5 thì yếu)
        int diem = 7;
        if (diem >= 8) {
            System.out.println("Giỏi");
        } else if (diem >= 6) {
            System.out.println("Khá");
        } else if (diem >= 5) {
            System.out.println("Trung bình");

        // ví dụ về switch case
        int dayOfWeek = 3;
        switch (dayOfWeek) {
            case 1:
                System.out.println("Hôm nay là thứ 2");
                break;
            case 2:
                System.out.println("Hôm nay là thứ 3");
                break;
            case 3:
                System.out.println("Hôm nay là thứ 4");
                break;
            case 4:
                System.out.println("Hôm nay là thứ 5");
                break;
            case 5:
                System.out.println("Hôm nay là thứ 6");
                break;
            case 6:
                System.out.println("Hôm nay là thứ 7");
                break;
            case 7:
                System.out.println("Hôm nay là chủ nhật");
                break;
            default:
                System.out.println("Ngày không hợp lệ");
        }
        // ví dụ toán tử tenary - toán tử ba ngôi  (condition ? value1 đúng : value2 sai)
        int a = 5;
        int b = 10;
        int max = (a > b) ? a : b;
        System.out.println("Số lớn nhất là: " + max);


        // ví dụ về vòng lặp for (tăng , giảm, lặp vô hạn)
        for (int i = 1; i <= 5; i++) {
            System.out.println("Vòng lặp for tăng: " + i);
        }
        for (int i = 5; i >= 1; i--) {
            System.out.println("Vòng lặp for giảm: " + i);
        }
        for (int i = 1; ; i++) {
            System.out.println("Vòng lặp for vô hạn: " + i);
            if (i == 5) {
                break;
            }
        }
        // ví dụ về vòng lặp for-each (dùng để duyệt mảng)
        String[] names = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C"};
        for (String name : names) {
            System.out.println("Vòng lặp for-each: " + name);
        } // khác nhau giữa for và for-each là for-each chỉ dùng để duyệt mảng còn for thì có thể làm nhiều việc khác nhau
            // gán lần lượt giá trị cho biến name trong mảng names và in ra từng giá trị của mảng names
        // ví dụ về mảng array
        int[] numbers = {1, 2, 3, 4, 5};
        for (int i = 0; i < numbers.length; i++) {
            System.out.println("Mảng array: " + numbers[i]);
        }

        // ví dụ về vòng lặp while
        int j = 1;
        while (j <= 5) {
            System.out.println("Vòng lặp while: " + j);
            j++;
        }

        // ví dụ về vòng lặp do while
        int k = 1;
        do {
            System.out.println("Vòng lặp do while: " + k);
            k++;
        } while (k <= 5);       // sự khác giữa while và do while là do while sẽ
            // chạy ít nhất 1 lần còn while thì không
            // ví dụ về break và continue
        for (int i = 1; i <= 10; i++) {
            if (i == 5) {
                break; // dừng vòng lặp khi i = 5   


    }


}
