public class Main {
    public static void main(String[] args) {
     try(var a = new A ()){

     }
    }
}

class A implements AutoCloseable{

    @Override
    public void close()  {
        System.out.println(" A closed");
    }
}
