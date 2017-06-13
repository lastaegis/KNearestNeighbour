import java.io.File;
import java.util.*;

public class Main {
    private static float irisData[][];
    private static float dataTest[][];
    private static float ranking[][];
    private static ArrayList<Integer> rankingResult = new ArrayList<Integer>();
    private static ArrayList<Integer> possibleValue = new ArrayList<>();
    private static int scoring[][];
    private static int K;
    private static int lengthColumn;
    private static int lengthRow;
    private static Scanner scanner;

    public static void main(String[] args) {

        readDataFromFile();

        irisData    = new float[lengthRow][lengthColumn];
        dataTest    = new float[1][lengthColumn];
        ranking     = new float[lengthRow][2];

        saveDataToIrisData();
        insertKAndDataTest();
        chiSquare();
        getResult();
    }

    /**
     * Read Data From File
     */
    private static void readDataFromFile()
    {
        try
        {
            scanner = new Scanner(new File("src/ruspini.txt"));
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (scanner.hasNext())
            {
                String data = scanner.nextLine();
                String arrayData[] = data.split("\\s",-1);
                lengthColumn = arrayData.length;
                i++;
                lengthRow = i;
            }
        }
        catch (Exception e)
        {
            System.out.print("Exception: "+e);
        }
    }

    /**
     * Method to save data into variable Iris Data
     */
    private static void saveDataToIrisData()
    {
        try
        {
            scanner = new Scanner(new File("src/ruspini.txt"));
            int lastState = 0;
            while (scanner.hasNext())
            {
                String data = scanner.nextLine();
                String newValue[] = data.split("\\s",-1);
                for (int i = lastState; i < lengthRow; i++)
                {
                    for (int j = 0; j < lengthColumn; j++)
                    {
                        irisData[i][j] = parseFloat(newValue[j]);
                    }
                }
                lastState++;
            }
        }
        catch (Exception e)
        {
            System.out.print("Exception: "+e);
        }
    }

    /**
     * Method insert data test
     */
    private static void insertKAndDataTest()
    {
        scanner = new Scanner(System.in);
        System.out.println("Masukkan data test, data test dipisahkan oleh enter");
        for (int i = 0; i < 1; i++)
        {
            for (int j = 0; j < lengthColumn - 1; j++)
            {
                dataTest[i][j] = scanner.nextFloat();
            }
        }

        System.out.println("Masukkan K: ");
        K = scanner.nextInt();
    }

    /**
     * Menghitung chiSquare
     */
    private static void chiSquare()
    {
        for (int i = 0; i < lengthRow; i++)
        {
            float chiSquare = 0;
            for (int j = 0; j < lengthColumn-1; j++)
            {
                double data1 = dataTest[0][j];
                double data2 = irisData[i][j];
                chiSquare = chiSquare + (float)Math.pow((data1-data2), 2);
                ranking[i][0]   = chiSquare;
                ranking[i][1]   = irisData[i][lengthColumn-1];
            }
        }
    }

    /**
     * Memandingkan data ranking dan menampilkan hasil
     */
    private static void getResult()
    {
        Arrays.sort(ranking, new Comparator<float[]>() {
            @Override
            public int compare(float[] o1, float[] o2) {
                return Float.compare(o1[0], o2[0]);
            }
        });

        for (int i = 0; i < K; i++)
        {
            rankingResult.add((int)ranking[i][1]);
        }

        possibleValue = new ArrayList<Integer>(new LinkedHashSet<Integer>(rankingResult));
        scoring = new int[possibleValue.size()][2];
        int a = 0;
        for (int i = 0; i < possibleValue.size(); i++)
        {
            scoring[i][0] = possibleValue.get(i);
            scoring[i][1] = Collections.frequency(rankingResult, possibleValue.get(i));
        }

        Arrays.sort(scoring, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o2[1], o1[1]);
            }
        });

        System.out.println("Hasilnya adalah "+scoring[0][0]);
    }

    /**
     * Backward compactibiltu
     * @param String number
     * @return float
     */
    private static float parseFloat(String number)
    {
        if (number != null && number.length() > 0) {
            try
            {
                return Float.parseFloat(number);
            }
            catch(Exception e)
            {
                return -1;
            }
        }
        else return 0;
    }
}
