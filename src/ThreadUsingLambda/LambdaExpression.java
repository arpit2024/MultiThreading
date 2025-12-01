package ThreadUsingLambda;

public class LambdaExpression {

        public static void main(String[] args){
//                Runnable task1=new Runnable() {
//                        @Override
//                        public void run() {
//                                System.out.println("Hello");
//                        }
//                };
//                Runnable task1=()->System.out.println("Hello");
//                Thread t1=new Thread(task1);
                Thread t1=new Thread(()->System.out.println("Hello"));
                t1.start();

                Thread t2=new Thread(()->{
                        for(int i=0;i<10;i++){
                                System.out.println("Hello World");
                }
                });

                Runnable task=()-> {
                        for(int i=0;i<10;i++){
                                System.out.println("Hello World");
                        }
                };
                t1.start();
        }

}
