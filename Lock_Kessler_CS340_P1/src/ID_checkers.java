import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class ID_checkers extends Thread{
    public static long time = System.currentTimeMillis();
    public static Queue<Voters> checkIdLine = new LinkedList<>();
    public static int votersForChecker;
    private final AtomicBoolean mutex;

    public ID_checkers(int id, int numberOfVoters, AtomicBoolean mutex) {
        setName("ID_Checker_" + (id+1));
        votersForChecker = numberOfVoters;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        while(votersForChecker != 0){

            if(mutex.compareAndSet(false,true)){
                //initially mutex = false, this allows first thread to enter and set mutex = true immediately.
                // then the follow-up threads won't be able to enter b/c our expected value of mutex is false however mutex is now true.
                    try {
                        if(!checkIdLine.isEmpty()) {
                            Voters voter = checkIdLine.remove();
                            msg("Check " + voter.getName() + "'s ID");
                            voter.idChecked(true);
                            mutex.set(false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        mutex.set(false);
                    }
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        msg("No more voters, going home..");
    }

    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }
 }





