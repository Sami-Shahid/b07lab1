import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    double[] coef;
    int[] exp;

    public Polynomial(){
        this.coef = new double[]{ 0 };
        this.exp = new int[] { 0 };
    }

    public Polynomial(double[] arr){
        int len = arr.length;
        this.coef = new double[len];
        this.exp = new int[len];

        for (int i = 0; i<len; i++){
            this.coef[i] = arr[i];
            this.exp[i] = i;
        }

    }

    public Polynomial(double[] coef, int[] exp) {
        this.coef = new double[coef.length];
        this.exp = new int[exp.length];

        for (int i = 0; i < coef.length; i++) {
            this.coef[i] = coef[i];
            this.exp[i] = exp[i];
        }
    }

    public Polynomial(File f) throws FileNotFoundException {
        Scanner input = new Scanner(f);
        String str = input.nextLine();
        input.close();

        double[] tempCoef = new double[1024];
        int[] tempExp = new int[1024];
        int count = 0;

        int i = 0;
        int len = str.length();

        while (i < len) {
            int flag = 1;

            if (str.charAt(i) == '+') {
                flag = 1;
                i++;
            } else if (str.charAt(i) == '-') {
                flag = -1;
                i++;
            }

            double coef = 0;
            boolean a = false;
            boolean b = false;
            double d = 0.1;

            while (i < len && (Character.isDigit(str.charAt(i))||str.charAt(i) == '.')) {
                if (str.charAt(i) == '.') {
                    b = true;
                    i++;
                } else {
                    a = true;
                    if (!b) {
                        coef = coef * 10 + (str.charAt(i) - '0');
                    } else {
                        coef += (str.charAt(i) - '0') * d;
                        d *= 0.1;
                    }
                    i++;
                }
            }

            if (!a)
                coef = 1;
            coef *= flag;
            int exp = 0;
            if (i < len && str.charAt(i) == 'x') {
                i++;
                if (i < len && Character.isDigit(str.charAt(i))) {
                    while (i < len && Character.isDigit(str.charAt(i))) {
                        exp = exp * 10 + (str.charAt(i) - '0');
                        i++;
                    }
                } else {
                    exp = 1;
                }
            }

            tempCoef[count] = coef;
            tempExp[count] = exp;
            count++;
        }
        this.coef = new double[count];
        this.exp = new int[count];
        for (i=0;i < count; i++) {
            this.coef[i] = tempCoef[i];
            this.exp[i] = tempExp[i];
        }
    }

    public void saveToFile(String f) throws IOException{
    FileWriter writer = new FileWriter(f);

    for (int i=0;i<coef.length; i++) {
        double c = coef[i];
        int e = exp[i];

        if (i >0 &&c > 0) {
            writer.write("+");
        }
        if (e == 0||(c!= 1 && c!= -1)) {
            writer.write(Double.toString(c));
        } else if (c == -1) {
            writer.write("-");
        }
        if (e > 0) {
            writer.write("x");
            if (e > 1) {
                writer.write(Integer.toString(e));
            }
        }
    }

    writer.close();
}


    

    public Polynomial add(Polynomial pol){
        int tot = this.coef.length + pol.coef.length;

        double[] coef = new double[tot];
        int[] exp = new int[tot];

        for (int i = 0; i < this.coef.length; i++){
            coef[i] = this.coef[i];
            exp[i] = this.exp[i];
        }

        int id = this.coef.length;

        for (int i = 0; i < pol.coef.length; i++){
            boolean flag = true;
            for (int j = 0; j < this.coef.length && flag; j++){
                if(exp[j] == pol.exp[i]){
                    coef[j] += pol.coef[i];
                    flag = false;
                }
            }
            if (flag){
                exp[id] = pol.exp[i];
                coef[id] = pol.coef[i];
                id++;
            }
        }

        int count = 0;
        for (int i = 0; i < id; i++) {
            if (coef[i] != 0) {
                count++;
            }
        }


        double[] c = new double[count];
        int[] e = new int[count];
        int idx = 0;
        for (int i = 0; i < id; i++) {
            if (coef[i] != 0) {
                c[idx] = coef[i];
                e[idx] = exp[i];
                idx++;
            }
        }

        return new Polynomial(c,e);
    }

    public double evaluate(double x){
        double sum = 0;
        for (int i = 0; i < this.coef.length; i++){
            sum += this.coef[i] * Math.pow(x, this.exp[i]);
        }
        return sum;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial pol){
        int tot = this.coef.length * pol.coef.length;

        double[] coef = new double[tot];
        int[] exp = new int[tot];
        int id = 0;
        for (int i = 0; i < this.coef.length; i++) {
            for (int j = 0; j< pol.coef.length; j++){
                double newCoef = this.coef[i] * pol.coef[j];
                int newExp = this.exp[i] + pol.exp[j];

                boolean flag = false;
                for (int k = 0; k < id; k++) {
                    if (exp[k] == newExp) {
                        coef[k] += newCoef;
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    coef[id] = newCoef;
                    exp[id] = newExp;
                    id++;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < id; i++) {
            if (coef[i] != 0) {
                count++;
            }
        }

        double[] c = new double[count];
        int[] e = new int[count];
        int idx = 0;
        for (int i = 0; i < id; i++) {
            if (coef[i] != 0) {
                c[idx] = coef[i];
                e[idx] = exp[i];
                idx++;
            }
        }

        return new Polynomial(c, e);


    }
}