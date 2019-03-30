import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class main {
    static int n = 20;
    static int size_frame = 3;
    public static void main(String[] args) {
        ArrayList<Integer> data_set = new ArrayList<>();
        ArrayList<Integer> in_frame = new ArrayList<>();

        //Init Data_set
        Init_Data(data_set);

        //frame = 3 , data set not sort
        test_set(data_set,in_frame,3);

        //frame = 5 , data set not sort
        test_set(data_set,in_frame,5);

        //frame = 7 , data set not sort
        test_set(data_set,in_frame,7);


        System.out.println("------------------------------------------------------------------------------------------");
        //sort data set
        Collections.sort(data_set);

        //frame = 3 , data set sorted
        test_set(data_set,in_frame,3);

        //frame = 5 , data set sorted
        test_set(data_set,in_frame,5);

        //frame = 7 , data set sorted
        test_set(data_set,in_frame,7);
    }

    public static void display(int hit,int page_fault){
        System.out.println("Hit : " + hit);
        System.out.println("Page_fault : " + page_fault);
    }

    public static  void FIFO(ArrayList<Integer> data_set,ArrayList<Integer> in_frame){
        System.out.println("--------------------------------------------");
        System.out.println("------------------- FIFO -------------------");
        System.out.println("--------------------------------------------");
        int hit = 0;
        int page_fault = 0;
        for(int i = 0; i <  n ; i++){
            if(search_frame(in_frame , data_set.get(i)) != -1 ){
                hit++;
                System.out.println("Hit ! data income: " + data_set.get(i) +" data in frame : " +in_frame);
                continue;
            }else {
                page_fault++;
                if(in_frame.size() < size_frame){
                    in_frame.add(data_set.get(i));
                } else {
                    int tmp = i%size_frame;
                    in_frame.remove(tmp);
                    in_frame.add(tmp,data_set.get(i));
                }
            }
            System.out.println("Miss! data income: " + data_set.get(i) +" data in frame : " +in_frame);
        }
        display(hit , page_fault);
        System.out.println("--------------------------------------------");
    }

    public static void OPTIMAL(ArrayList<Integer> data_set,ArrayList<Integer> in_frame) {
        System.out.println("--------------------------------------------");
        System.out.println("----------------- OPTIMAL ------------------");
        System.out.println("--------------------------------------------");
        int hit = 0;
        int page_fault = 0;
        for(int i = 0; i< n; i++){
            if(search_frame(in_frame , data_set.get(i)) != -1){
                hit++;
                System.out.println("Hit ! data income: " + data_set.get(i) +" data in frame : " +in_frame);
                continue;
            } else {
                page_fault++;
                if(in_frame.size() < size_frame){
                    in_frame.add(data_set.get(i));
                } else {
                    boolean[] replace = new boolean[size_frame];
                    for(int tmp = 0;tmp < replace.length ; tmp++) replace[tmp] = true;
                    //System.out.println("Search for : " + (n - i) + " AND " + (i+size_frame));
                    for(int j = i ; j < n && j < i+size_frame ; j++){
                        //System.out.println("position : " + j + " is "+data_set.get(j) );
                        if(search_frame(in_frame,data_set.get(j)) != -1 ){
                            //System.out.println("found in " + search_frame(in_frame,data_set.get(j)));
                            replace[search_frame(in_frame,data_set.get(j))] = false;
                        }
                    }

                    boolean check_all_false = true;
                    for (int tmp = 0 ; tmp<replace.length; tmp++) {
                        if(replace[tmp] == false) continue;
                        else check_all_false = false;
                    }
                    if(check_all_false) {
                        in_frame.remove(0);
                        in_frame.add(0,data_set.get(i));
                    } else {
                        for (int j = 0;j<size_frame;j++){
                            if(replace[j]){
                                //System.out.println("test");
                                in_frame.remove(j);
                                in_frame.add(j,data_set.get(i));
                                break;
                            }
                        }
                    }
                }
                System.out.println("Miss! data income: " + data_set.get(i) +" data in frame : " +in_frame);
            }
        }
        display(hit,page_fault);
        System.out.println("--------------------------------------------");
    }

    public static void LRU(ArrayList<Integer> data_set,ArrayList<Integer> in_frame) {
        System.out.println("--------------------------------------------");
        System.out.println("------------------- LRU --------------------");
        System.out.println("--------------------------------------------");
        int hit = 0;
        int page_fault = 0;
        int[] counter = new int[size_frame];
        for(int tmp = 0; tmp < size_frame ; tmp++) counter[tmp] = 0;
        for(int i = 0; i <  n ; i++){
            if(search_frame(in_frame , data_set.get(i)) != -1 ){
                hit++;
                counter[i%size_frame] = i;
                System.out.println("Hit ! data income: " + data_set.get(i) +" data in frame : " +in_frame);
                continue;
            }else {
                page_fault++;
                if(in_frame.size() < size_frame){
                    in_frame.add(data_set.get(i));
                    counter[i%size_frame] = i;
                } else {
                    int tmp = in_frame.indexOf(Collections.min(in_frame));
                    in_frame.remove(tmp);
                    in_frame.add(tmp,data_set.get(i));
                    counter[i%size_frame] = i;
                }
            }
            System.out.println("Miss! data income: " + data_set.get(i) +" data in frame : " +in_frame);
        }
        display(hit,page_fault);
        System.out.println("--------------------------------------------");
    }

    public static void Init_Data(ArrayList<Integer> var){
        for(int i = 0; i < n ; i++ ){
            var.add(getRandomNumberInRange(0,9));
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static int search_frame (ArrayList<Integer> in_frame,int new_data){
        for(int i = 0;i<in_frame.size();i++){
            if(new_data == in_frame.get(i)){
                return i;
            } else {
                continue;
            }
        }
        return -1;
    }

    public static void show_bool(boolean[] set_of_bool){
        for(int i = 0 ; i < set_of_bool.length ; i++) {
            System.out.println(set_of_bool[i]);
        }
    }

    public static void test_set(ArrayList<Integer>data_set , ArrayList<Integer>in_frame , int no_frame){
        size_frame = no_frame;

        //FIFO
        System.out.println(data_set);
        in_frame.removeAll(in_frame);
        FIFO(data_set,in_frame);

        //OPTIMAL
        System.out.println(data_set);
        in_frame.removeAll(in_frame);
        OPTIMAL(data_set,in_frame);

        //LRU
        System.out.println(data_set);
        in_frame.removeAll(in_frame);
        LRU(data_set,in_frame);
    }
}
