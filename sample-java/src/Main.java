import java.util.*;
public class Main {

    public static void main (String[] args) {

        int[] input = {4,3,6,1,7};
//quick sort


        quickSort(input,0,input.length-1);

        System.out.println(Arrays.toString(input));


    }

    static void quickSort(int[] input, int start ,int end){

        if(start<end){
            int i =start;
            int pivot =end;

            for(int j=start;j <= end;j++){
                if(input[j]<=input[pivot]){
                    int temp= input[i];
                    input[i]=input[j];
                    input[j] =temp;
                    i++;
                }
                if(input[j]==input[pivot]){
                    i--;
                }

            }
            System.out.println(Arrays.toString(input));
            pivot =i;
            System.out.println("pivot "+ pivot);
            quickSort(input,start ,pivot-1);
            quickSort(input,pivot+1,end);
        }
    }





}
