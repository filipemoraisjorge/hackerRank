/**
 * Created by filipejorge on 25/05/16.
 */
public class FabioItPeople {


    public static Number numberMax(Number a, Number b) {
        return (a.doubleValue() > b.doubleValue() ? a : b);
    }

    public static void main(String[] args) {


        Byte a = 1;
        Short b = 2;
        Integer c = 3;
        Long d = 4L;
        Float e = 4.5f;
        Double f = 4.4d;

        Byte a2 = 2;
        Short b2 = 3;
        Integer c2 = 4;
        Long d2 = 5L;
        Float e2 = 6.5f;
        Double f2 = 5.4d;

        Number[] testArray = {a, a2, b, b2, c, c2, d, d2, e, e2, f, f2};

        for (int i = 0; i < testArray.length - 1 ; i++) {

            Number x = FabioItPeople.numberMax(testArray[i], testArray[i+1]);
            System.out.println(testArray[i] + " > " + testArray[i+1] + " = " + x + " " + x.getClass().getSimpleName());
            //quando iguais devolver sempre o 2º argumento
        }


    }
}
