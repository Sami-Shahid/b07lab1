public class Polynomial {
    double[] coef;

    public Polynomial(){
        this.coef = new double[]{0};
    }

    public Polynomial(double[] arr){
        int len = arr.length;
        this.coef = new double[len];

        for (int i = 0; i<len; i++){
            this.coef[i] = arr[i];
        }

    }

    public Polynomial add(Polynomial pol){
        int len = 1;
        if (pol.coef.length > this.coef.length)
            len = pol.coef.length;
        else
            len = this.coef.length;
        
        double[] arr = new double[len];

        for (int i = 0; i<pol.coef.length; i++){
            arr[i] = pol.coef[i];
        }
        
        for (int i = 0; i<this.coef.length; i++){
            arr[i] += this.coef[i];
        }
        return new Polynomial(arr);
    }

    public double evaluate(double x){
        double sum = 0;
        for (int i = 0; i < this.coef.length; i++){
            sum += this.coef[i] * Math.pow(x, i);
        }
        return sum;
    }

    public boolean hasRoot(double x){
        return this.evaluate(x) == 0;
    }




}