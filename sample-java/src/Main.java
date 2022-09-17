import java.io.*;
import java.util.*;
public class Main {

    public static void main (String[] args) throws IOException {

//        int[] input = {4,3,6,1,7};
////quick sort
//
//
//        quickSort(input,0,input.length-1);
//
//        System.out.println(Arrays.toString(input));
   //     String data = "This is the data in the output file";

        List<String> data=List.of("tfhff","gjhgjhg","uftukyfuyfluy");
        // Creates a FileWriter
      //  FileWriter file = new FileWriter("/Users/henokkeraga/Documents/interview/spring/sample-java/output.txt");



        int i =0;

        while (i<3){
            var directory = new File("/Users/henokkeraga/Documents/interview/spring/sample-java/yyy");
            if(!directory.exists()){
                directory.mkdir();
            }
            OutputStream outputStream= new FileOutputStream(directory+"/sam.txt",true);
            // Creates a BufferedWriter
            PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
                // Writes the string to the file

            pr.append(data.get(i) + "\n");

                // Closes the writer
            pr.flush();
            pr.close();
            i++;
        }




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
