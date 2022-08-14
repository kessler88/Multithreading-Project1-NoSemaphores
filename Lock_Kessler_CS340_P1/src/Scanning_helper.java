import java.util.LinkedList;
import java.util.Queue;

public class Scanning_helper extends Thread {
    public static long time = System.currentTimeMillis();
    public static Queue<Voters> scanningLine = new LinkedList<>();
    public static Queue<Integer> scanningMachines;
    public static int votersForScanner;

    public Scanning_helper(int numOfScanningMachine, int numberOfVoters) {
        setName("ScanningHelper");
        scanningMachines = new LinkedList<>();
        for(int i = 1; i <= numOfScanningMachine; i++){
            scanningMachines.add(i);
        }
        votersForScanner = numberOfVoters;
    }

    @Override
    public void run() {
        while(votersForScanner != 0){
            if(!scanningLine.isEmpty() && !scanningMachines.isEmpty()){
                Voters voter = scanningLine.remove();
                msg("ask " + voter.getName() + " to use the scanning machine");
                voter.useScannerMachine(scanningMachines.remove());
            }
            try {
                Thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        msg("No more voters, going home..");
    }


    public void msg(String m) {
        System.out.println("["+(System.currentTimeMillis()-time)+"] "+getName()+": "+m);
    }

    public static void doneScan(int scanningMachine){
        //when voter done, add back the available kiosk.
        scanningMachines.add(scanningMachine);
    }

}
