import java.util.ArrayList;
import java.util.Random;

public class OtobusProje {

    public static void main(String[] args){
        // Kullanılacak olan uzaklık matrisi ve 10x4 lük otobüs matrisi
        double[][] acquaintanceMatrices = createAcquaintanceMatrices(); // tanışıklık matrisi oluşturur
        int[][] busShapeMatrices = new int[10][4];

        placePeople(busShapeMatrices,acquaintanceMatrices); // kişileri otobüse yerleştirir

        String[] names = {"ece kök","efe ay","ada erkin","ahmet çoban","yusuf gümüş","hanife koyun","musa duran",
                "eray atlı","amir sayil","yunus inci","umut çelik","berkay türk","lale bulut","şebnem ak",
                "sertap kara","arda al","peri akkan","nez türkmen","seda durmaz","ferhat saka","serkan demir",
                "duru öz","buse yavuz","ilker al","eray karabaş","efe aydal","aziz mutlu","ayten sam","aydın türk"
                ,"kemal ak","alina sarı","selen bal","eren öz","tevfik özmen","emre sönmez","berk uslu",
                "tunç durmaz","ali soylu","mert karasu","ayşe akan"}; // Otobüsümüzde bulunan isimler

        double acqSum = 0; // Toplam uzaklık degerimiz (bütün yolcuların toplamı)

        // Otobüs düzenini bastırır
        for(int i = 0; i < busShapeMatrices.length; i++){
            for(int j = 0; j < busShapeMatrices[0].length; j++){
                double currentAcq = findSumOfAcquaintance(busShapeMatrices, (i * 4) + j + 1, acquaintanceMatrices, busShapeMatrices[i][j]);
                if (j==1){
                    System.out.printf("| %2d-- %-2d  %-18s %7.2f  |\t\t\t\t\t" ,(i * 4) + j + 1, busShapeMatrices[i][j], names[busShapeMatrices[i][j]], currentAcq);
                }
                else{
                    System.out.printf("| %2d-- %-2d  %-18s  %7.2f |" ,(i * 4) + j + 1, busShapeMatrices[i][j], names[busShapeMatrices[i][j]], currentAcq);
                }
                acqSum+= currentAcq;
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.printf("Sum of Acquaintance is : %.2f",acqSum); // Yolcuların toplam uzaklık değerlerini yazdırır
    }
    public static double[][] createAcquaintanceMatrices(){ // uzaklık matrisini oluşturan fonksiyon

        double[][] acquaintanceMatrices = new double[40][40];

        for(int i = 0; i < acquaintanceMatrices.length; i++){

            for(int j = i; j < acquaintanceMatrices[0].length; j++){

                if(i == j){ // Kişinin kendisi ile uzaklığı 0' dır dolayısıyla 0 atadık
                    acquaintanceMatrices[i][j] = 0;
                }
                else{
                    Random random = new Random();  // [0,1) arası random deger olusturma
                    double randomNum = random.nextDouble() * 10; // oluşturulan random degeri 0 ile 10 arasına dönüştürür

                    // [i,j] = [j,i] olacak cünkü ikisi de aynı kişiler arası uzaklığı belirtir
                    acquaintanceMatrices[i][j] = randomNum;
                    acquaintanceMatrices[j][i] = randomNum;
                }
            }
        }
        return acquaintanceMatrices;
    }

    public static double findSumOfAcquaintance(int[][] busArr, int seatNo, double[][] acqMatrices, int currentIndex){

        // currentIndex olan mevcut kişinin indexi
        int rowIndex = (seatNo - 1) / 4; // alınan koltuk numarasının matriste karşılık geldiği satır
        int columnIndex = (seatNo - 1) % 4;  // alınan koltuk numarasının matriste karşılık geldiği sutun
        double sum = 0; // uzaklık değerini tutacak olan değişken

        if(seatNo == 1){ // 1. koltuk için uzaklık toplamı değeri 0 olacak soruda öyle verilmiş.
            sum = 0;
        }

        else if(seatNo < 5){ // koltuk numrası 5 ten kücükse sadece sol tarafındaki kişilere bakılır
            sum = acqMatrices[busArr[rowIndex][columnIndex - 1]][currentIndex];
        }
        else if(seatNo % 4 == 0){ // koltuk numarası 4 ün katıysa solundaki, sol ön çaprazındaki, ve önündeki
            // yolcularla olan uzaklığı dikkate alınır
            sum = acqMatrices[busArr[rowIndex][columnIndex - 1]][currentIndex] +
                    acqMatrices[busArr[rowIndex - 1][columnIndex - 1]][currentIndex]
                    + acqMatrices[busArr[rowIndex - 1][columnIndex]][currentIndex];
        }

        else if(seatNo % 4 == 1){ // koltuk numarasının matematiksel formülü 4n + 1 e eşitse bu koltuğa
            // yolcu yerleştirirken önü ve sağ ön çaprazındaki yolcular ile arasındaki
            // uzaklık dikkate alınır
            sum = acqMatrices[busArr[rowIndex - 1][columnIndex]][currentIndex]
                    + acqMatrices[busArr[rowIndex - 1][columnIndex + 1]][currentIndex];
        }

        else{ // yukarıdaki hiçbir koşula girmiyorsa bu yolcu kenarlarda veya önde oturmuyordur, otobüsün ortasında
            // herhangi bir yerde oturuyordur. o zaman bu koltuğa yerleştirilecek adamı bulmak için sağ ön çapraz,
            // sol ön çapraz, tam önü, ve solundaki kişi ile karşılaştırma yaparız
            sum = acqMatrices[busArr[rowIndex][columnIndex - 1]][currentIndex] +
                    acqMatrices[busArr[rowIndex - 1][columnIndex - 1]][currentIndex] +
                    acqMatrices[busArr[rowIndex - 1][columnIndex]][currentIndex] +
                    acqMatrices[busArr[rowIndex - 1][columnIndex + 1]][currentIndex];
        }
        return sum; // şu anki baktığımız kişi ile çevresindeki koltuklara olan uzaklığı döndürür
    }

    public static void placePeople(int[][] busAsMatrices, double[][] acqMatrices){

        // possibleIndices arrayListi daha otobüse oturmayan yolcuların indislerini tutar
        // ve bu sayede otobüste mevcut oturanları bir daha değerrlnedirmeye almamış oluruz.
        ArrayList<Integer> possibleIndices = new ArrayList<>();
        for(int i = 0; i < 40; i++){
            possibleIndices.add(i);
        }

        int indices = (int) (Math.random() * 40); // 0 <= x < 40
        busAsMatrices[0][0] = indices; // ilk koltuga oturtulan kişi tamamen rastgele oturtulur

        possibleIndices.remove(Integer.valueOf(indices)); // ilk oturttuğumuz kişiyi oturmayanların listesinden çıkarırız

        for(int seatNoIndex = 1; seatNoIndex < 40; seatNoIndex++){ // her dış döngüde bir koltuk için kontrol yapacaz
            //birinci kişiyi oturttuğumuz için koltuk indexini 0 dan değil 1 den başlatırız, en son koltuk indexi 39 olur

            // toplamSum ve indexe unreachable değerler atanır ki döngüde kontrol ederken onlardan daha küçük olanı
            // kesin olarak bulabilelim diye.
            double minSum = 999; // oturacak kişi ile çevresindeki ilgili kişilerin uzaklık değerlerni tutar.
            int minIndex =  42;

            for(int index : possibleIndices){ // oturmayan kişiler arasından karşılaştırma yaparız

                // ilgili fonksiyona matrisler, koltuk numarası, şu anki kişinin indexi atılarak toplam uzaklık değeri bulunur
                double sum = findSumOfAcquaintance(busAsMatrices, seatNoIndex + 1, acqMatrices, index);

                if(sum < minSum){  // koltuğa oturtmak için uzaklık değeri en küçük olan değeri arıyoruz.
                    // eğer ölçülen değer şu anki minSum dan düşükse yeni minSum u bu değer olarak atarız
                    minSum = sum;
                    minIndex = index;
                }
            }
            busAsMatrices[seatNoIndex / 4][seatNoIndex % 4] = minIndex; // bulunan indexteki kişiyi mevcut incelediğimiz
            // koltuğa oturtur
            possibleIndices.remove(Integer.valueOf(minIndex)); // oturttuğumuz kişinin indexini
            // oturmayanların içinden sileriz
        }
    }
}