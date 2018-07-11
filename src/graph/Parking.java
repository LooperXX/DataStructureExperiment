package graph;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Stack;

public class Parking {
    private static ArrayList<ParkingRecord> records = new ArrayList<>();
    private static LinkedList <String> sideWay = new LinkedList<>();
    private static Stack<String> park = new Stack<>();
    private static Stack<String> park_save = new Stack<>();
    private static int N = 10;

    private static String getCurLeaveTime() {
        Calendar c = Calendar.getInstance();
        int[] res = new int[2];
        res[0] = c.get(Calendar.HOUR_OF_DAY);
        res[1] = c.get(Calendar.MINUTE);
        return String.valueOf(res[0]) + ":" + String.valueOf(res[1]);
    }

    static void recordUpdate(String[] input){// type number time
        int type = Integer.parseInt(input[0]);
        if(type == 0){// In
            records.add(new ParkingRecord(type,input[1],input[2]));
            if(park.size() < N){
                park.push(input[1]);
                System.out.println("车牌号为" + input[1] + "的车辆已停在停车场" + park.size() + "号车道");
            }else{
                sideWay.add(input[1]);
                System.out.println("车牌号为" + input[1] + "的车辆已停在便道" + sideWay.size() + "号车道");
            }
        }else if(type == 1){// Out
            int index = -1;
            if(park.search(input[1]) != -1){
                index = park.size() - park.search(input[1]) + 1;
            }
            int index_side = sideWay.indexOf(input[1]);
            if(index == -1 && index_side == -1){
                System.out.println("您输入的车牌号所属车辆不在本停车场！");
            }else if(index_side != -1){ // 在sideWay中
                String time = getCurLeaveTime();
                records.add(new ParkingRecord(type,input[1],time));
                sideWay.remove(index_side);
                System.out.println("车牌号为" + input[1] + "的车辆已停离开便道");
            }else { // 在park中
                int num = park.search(input[1]);
                for(int i = 0; i < num; i++){
                    park_save.push(park.peek());
                    park.pop();
                }
                park_save.pop();
                for(int i = 0; i < num - 1; i++){
                    park.push(park_save.peek());
                    park_save.pop();
                }
                String time = getCurLeaveTime();
                records.add(new ParkingRecord(type,input[1],time));
                System.out.println("车牌号为" + input[1] + "的车辆已停离开停车场");
            }
        }
    }

    static void printRecord(){
        if(records.isEmpty()){
            System.out.println("当前历史记录为空。");
        }else{
            for(ParkingRecord rec : records){
                String state = "";
                if(rec.getType() == 0){
                    state = "进入";
                }else if(rec.getType() == 1){
                    state = "离开";
                }
                System.out.println("车牌号为" + rec.getNumber() + "的车辆在" + rec.getTime() + state + "了本停车场。");
            }
        }
    }
}
