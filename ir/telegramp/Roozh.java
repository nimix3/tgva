
package ir.telegramp;

public class Roozh {
    private int day;
    private int month;
    private int year;
    private int jY;
    private int jM;
    private int jD;
    private int gY;
    private int gM;
    private int gD;
    private int leap;
    private int march;

    private int JG2JD(int year, int month, int day, int J1G0) {
        int jd = 1461 * (year + 4800 + (month - 14) / 12) / 4 + 367 * (month - 2 - 12 * ((month - 14) / 12)) / 12 - 3 * ((year + 4900 + (month - 14) / 12) / 100) / 4 + day - 32075;
        if (J1G0 == 0) {
            jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        }
        return jd;
    }

    private void JD2JG(int JD, int J1G0) {
        int j = 4 * JD + 139361631;
        if (J1G0 == 0) {
            j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        }
        int i = j % 1461 / 4 * 5 + 308;
        this.gD = i % 153 / 5 + 1;
        this.gM = i / 153 % 12 + 1;
        this.gY = j / 1461 - 100100 + (8 - this.gM) / 6;
    }

    private void JD2Jal(int JDN) {
        this.JD2JG(JDN, 0);
        this.jY = this.gY - 621;
        this.JalCal(this.jY);
        int JDN1F = this.JG2JD(this.gY, 3, this.march, 0);
        int k = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                this.jM = 1 + k / 31;
                this.jD = k % 31 + 1;
                return;
            }
            k -= 186;
        } else {
            --this.jY;
            k += 179;
            if (this.leap == 1) {
                ++k;
            }
        }
        this.jM = 7 + k / 30;
        this.jD = k % 30 + 1;
    }

    private int Jal2JD(int jY, int jM, int jD) {
        this.JalCal(jY);
        int jd = this.JG2JD(this.gY, 3, this.march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7) + jD - 1;
        return jd;
    }

    private void JalCal(int jY) {
        this.march = 0;
        this.leap = 0;
        int[] breaks = new int[]{-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210, 1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        this.gY = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];
        int jump = 0;
        for (int j = 1; j <= 19; ++j) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                if (jump % 33 == 4 && jump - N == 4) {
                    ++leapJ;
                }
                int leapG = this.gY / 4 - (this.gY / 100 + 1) * 3 / 4 - 150;
                this.march = 20 + leapJ - leapG;
                if (jump - N < 6) {
                    N = N - jump + (jump + 4) / 33 * 33;
                }
                this.leap = ((N + 1) % 33 - 1) % 4;
                if (this.leap != -1) break;
                this.leap = 4;
                break;
            }
            leapJ = leapJ + jump / 33 * 8 + jump % 33 / 4;
            jp = jm;
        }
    }

    public String toString() {
        return String.format("%04d-%02d-%02d", this.getYear(), this.getMonth(), this.getDay());
    }

    public void GregorianToPersian(int year, int month, int day) {
        int jd = this.JG2JD(year, month, day, 0);
        this.JD2Jal(jd);
        this.year = this.jY;
        this.month = this.jM;
        this.day = this.jD;
    }

    public void PersianToGregorian(int year, int month, int day) {
        int jd = this.Jal2JD(year, month, day);
        this.JD2JG(jd, 0);
        this.year = this.gY;
        this.month = this.gM;
        this.day = this.gD;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }
}

