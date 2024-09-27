package questions;

public class CanPlaceFlowers {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        // 0 0 1 0 1
//        if(flowerbed.length == 1){
//            if(flowerbed[0] == 0) return n <= 1;
//            return n == 0;
//        }
        int i = -1;
        while (i < flowerbed.length) {
            int j = checkNextPlant(flowerbed, i);
            int zeroNum = j - i - 1;
            if (i == -1) zeroNum++;
            if (j == flowerbed.length) zeroNum++;
            n -= zeroNum == 0 ? 0 : (zeroNum + 1) / 2 - 1;
            i = j;
        }
        return n <= 0;
    }

    private int checkNextPlant(int[] flowers, int from) {
        for (int i = from + 1; i < flowers.length; i++) {
            if (flowers[i] == 1) return i;
        }
        return flowers.length;
    }
}
