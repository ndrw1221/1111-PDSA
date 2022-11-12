import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.MinPQ;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

class CovidSimulation {
    private int Date = 1; //An integer to keep track of the latest date

    MinPQ<Event> eventMinPQ = new MinPQ<Event>();
    int[] cities;
    int[] recoveryDay;
    int[] maxRecoveryDay;

    class Event implements Comparable<Event> {
        String type;
        int date;
        int city;
        int numberOfTraveler;
        int dateOfRecovery;
        int maxDateOfRecovery;

        public int compareTo(Event o) {
            if (this.date == o.date) return this.numberOfTraveler - o.numberOfTraveler;
            return this.date - o.date;
        }
    }

    public void virusAttackPlan(int city, int date) {
        Event eventVirusAttack = new Event();
        eventVirusAttack.type = "Virus attack";
        eventVirusAttack.city = city;
        eventVirusAttack.date = date;

        eventMinPQ.insert(eventVirusAttack);
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival) {
        Event eventDeparture = new Event();
        Event eventArrival = new Event();

        eventDeparture.type = "Departure";
        eventDeparture.date = DateOfDeparture;
        eventDeparture.city = FromCity;
        eventDeparture.numberOfTraveler = NumberOfTraveller;

        eventArrival.type = "Arrival";
        eventArrival.date = DateOfArrival;
        eventDeparture.city = ToCity;
        eventDeparture.numberOfTraveler = NumberOfTraveller;
        eventDeparture.dateOfRecovery = recoveryDay[FromCity];
        eventDeparture.maxDateOfRecovery = maxRecoveryDay[FromCity];

        eventMinPQ.insert(eventArrival);
        eventMinPQ.insert(eventDeparture);
    }




    public CovidSimulation(int[] Num_Of_Citizen) {
        //The initial number of people in each city is defined here.
        cities = new int[Num_Of_Citizen.length];
        recoveryDay = new int[Num_Of_Citizen.length];
        maxRecoveryDay = new int[Num_Of_Citizen.length];
    }

    // NEED REFACTOR!!!
    public void DoVirusAttackPlan(int city, int date){
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific city would be defined here

        if (Date < date + 4) {
            // Update total days
            for (int i = 0; i < date + 4 - Date; i++) {
                //Add the last element in Arraylist to ArrayList
                cities.add(cities.get(cities.size() - 1).clone());
                attackedDay.add(attackedDay.get(attackedDay.size() - 1).clone());
                recoverDay.add(recoverDay.get(recoverDay.size() - 1).clone());
            }

            //Update current date
            Date = date + 4;
        }

        //Update infected days left if the city is not already infected
        if (attackedDay.get(date - 1)[city] == 0) {
            attackedDay.get(date - 1)[city] = date;
            attackedDay.get(date)[city] = date;
            attackedDay.get(date + 1)[city] = date;
            attackedDay.get(date + 2)[city] = date;

            recoverDay.get(date - 1)[city] = date + 4;
            recoverDay.get(date)[city] = date + 4;
            recoverDay.get(date + 1)[city] = date + 4;
            recoverDay.get(date + 2)[city] = date + 4;
        }
    }


//    public void DoTravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
//        //The information of travellers' plan would be written here.
//        //Since everyone travel with different methods, the duration to travel from City A to B would not be constant (we tried our best to simplify the problem instead of giving an array of data!)
//
//        //Update total days
//        if (DateOfArrival > Date) {
//            for (int i = 0; i < DateOfArrival - Date; i++) {
//                // add the last element in Arraylist to ArrayList
//                cities.add(cities.get(cities.size() - 1).clone());
//                attackedDay.add(attackedDay.get(attackedDay.size() - 1).clone());
//                recoverDay.add(recoverDay.get(recoverDay.size() - 1).clone());
//            }
//
//            Date = DateOfArrival;
//        }
//
//        //Update citizen number
//        for (int i = 0; i <= cities.size() - DateOfDeparture; i++) {
//            cities.get(DateOfDeparture - 1 + i)[FromCity] -= NumberOfTraveller;
//            if (DateOfArrival - 1 + i < cities.size()) {
//                cities.get(DateOfArrival - 1 + i)[ToCity] += NumberOfTraveller;
//            }
//        }
//
//        //Update infected days left
//        //If recovery day of traveler(s) later than that of the destination city
//        if (recoverDay.get(DateOfDeparture - 1)[FromCity] > recoverDay.get(DateOfArrival - 1)[ToCity]) {
//            if (recoverDay.get(DateOfArrival - 1)[ToCity] == 0) {
//                //If traveler(s) travel(s) to an uninfected city
//                for (int i = 0; i < recoverDay.get(DateOfDeparture - 1)[FromCity] - DateOfArrival; i++) {
//                    //The attacked day would be the DateOArrival
//                    attackedDay.get(DateOfArrival - 1 + i)[ToCity] = DateOfArrival;
//                    recoverDay.get(DateOfArrival - 1 + i)[ToCity] = recoverDay.get(DateOfDeparture - 1)[FromCity];
//                }
//            } else if (recoverDay.get(DateOfDeparture - 1)[FromCity] - attackedDay.get(DateOfDeparture - 1)[ToCity] <= 6) {
//                //If the total infected days wouldn't be longer than 7 days
//                for (int i = 0; i < recoverDay.get(DateOfDeparture - 1)[FromCity] - DateOfArrival; i++) {
//                    attackedDay.get(DateOfArrival - 1 + i)[ToCity] = attackedDay.get(DateOfDeparture)[ToCity];
//                    recoverDay.get(DateOfArrival - 1 + i)[ToCity] = recoverDay.get(DateOfDeparture - 1)[FromCity];
//                }
//            } else {
//                for (int i = 0; i <  6 - recoverDay.get(DateOfDeparture - 1)[ToCity] + attackedDay.get(DateOfDeparture - 1)[ToCity]; i++) {
//                    attackedDay.get(DateOfArrival + i)[ToCity] = attackedDay.get(DateOfDeparture)[ToCity];
//                    recoverDay.get(DateOfArrival + i)[ToCity] = attackedDay.get(DateOfDeparture - 1)[ToCity] + 6;
//                }
//            }
//        }
//    }

    // NEED IMPLEMENTING
    public void DoEventDeparture(int NumberOfTraveller, int FromCity, int DateOfDeparture) {

    }

    // NEED IMPLEMENTING
    public void DoEventArrival(int NumberOfTraveller, int ToCity, int DateOfArrival, int RecoveryDate, int MaxRecoveryDate) {

    }

    public int CityWithTheMostPatient(int date){
        //return the index of city which has the most patients

        //if there are more than two cities with the same amount of patients, return the largest index value.
        //if every city is clean, please return -1.

        while(!eventMinPQ.isEmpty() && eventMinPQ.min().date <= date) {
            Event event = eventMinPQ.delMin();
            if (event.type == "Virus attack") {
                DoVirusAttackPlan(event.city, event.date);
//                System.out.println("DoVirusAttackPlan: Day " + event.date + ", City " + event.city);
            } else if (event.type == "Departure") {
                DoEventDeparture(event.numberOfTraveler, event.city, event.date);
//                System.out.println("DoEventDeparture: Day " + event.date + ", City " + event.city);
            } else {
                DoEventArrival(event.numberOfTraveler, event.city, event.date, event.dateOfRecovery, event.maxDateOfRecovery);
//                System.out.println("DoEventArrival: Day " + event.date + ", City " + event.city);
            }
        }


        if (allZero(recoveryDay)) {
            return -1;
        }

        int max = 0;
        for (int i = 0; i < recoveryDay.length; i++) {
            //If a city IS infected
            if (recoveryDay[i] != 0) {
                //Get infected city with most patients
                if (cities[i] > cities[max]) {
                    max = i;
                }
            }
        }

        return max;

    }

    boolean allZero(int[] arr) {
        //Check if all values in an array are zero
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        test t = new test(args);
//        CovidSimulation sol = new CovidSimulation(new int[] {500, 100, 50, 250});
//        sol.virusAttackPlan(0, 3);
//        sol.TravelPlan(200, 0, 3, 1, 5);
//        sol.TravelPlan(50, 1, 0, 1, 4);
//        sol.TravelPlan(150, 0, 2, 1, 3);
//        sol.virusAttackPlan(1, 1);
//        sol.TravelPlan(50, 3, 2, 1, 2);
//        sol.TravelPlan(10, 1, 3, 1, 2);
//        sol.TravelPlan(70, 3, 0, 2, 3);
//        sol.virusAttackPlan(3, 3);
//
//        while(!sol.eventMinPQ.isEmpty()) {
//            System.out.print("Day: " + sol.eventMinPQ.min().date + ", Event: ");
//            System.out.println(sol.eventMinPQ.delMin().numberOfTraveler == 0 ? "Travel Plan" : "Virus Attack Plan");
////            System.out.println(sol.eventMinPQ.delMin().numberOfTraveler);
//        }

//        System.out.println(sol.CityWithTheMostPatient(1));
//        System.out.println(sol.CityWithTheMostPatient(2));
//        System.out.println(sol.CityWithTheMostPatient(3));
//        System.out.println(sol.CityWithTheMostPatient(4));
//        System.out.println(sol.CityWithTheMostPatient(5));
//        System.out.println(sol.CityWithTheMostPatient(6));
//
//        sol.virusAttackPlan(0, 1);
//        sol.virusAttackPlan(4, 3);
//        sol.TravelPlan(3, 0, 3, 3, 4);
//        sol.TravelPlan(3, 4, 0, 3, 4);
//        System.out.println(sol.CityWithTheMostPatient(2));
////        output = 0
//
//        sol.virusAttackPlan(5, 5);
//        sol.TravelPlan(1, 5, 0, 5, 6);
//        System.out.println(sol.CityWithTheMostPatient(4));
////         output = 3
//        System.out.println(sol.CityWithTheMostPatient(8));
////         output = 5
//        for (int i = 0; i < sol.cities.size(); i++) {
//            System.out.println("Day" + (i + 1) + ":");
//            System.out.println(Arrays.toString(sol.cities.get(i)));
//            System.out.println(Arrays.toString(sol.attackedDay.get(i)));
//            System.out.println(Arrays.toString(sol.recoverDay.get(i)));
//        }

        //day 1:{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 2：{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 0, 0}
        //day 3：{10, 100, 15, 25, 10, 13}
        //infectedList:{1, 0, 0, 0, 1, 0}
        //day 4：{10, 100, 15, 28, 7, 13}
        //infectedList:{1, 0, 0, 1, 1, 0}
        //day 5：{10, 100, 15, 28, 7, 13}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 6：{11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 1, 1}
        //day 7：{11, 100, 15, 28, 7, 12}
        //infectedList:{1, 0, 0, 1, 0, 1}
        //day 8：{11, 100, 15, 28, 7, 12}
        //infectedList:{0, 0, 0, 0, 0, 1}
    }
}

//class test {
//    public test(String[] args) {
//        CovidSimulation g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int waSize = 0;
//            int count = 0;
//            for(Object CaseInList : all){
//                JSONArray a = (JSONArray) CaseInList;
//                //Board Setup
//                JSONObject argsSetting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSetting.get("args");
//                int citySetting[] = new int[argSettingArr.size()];
//                for(int i=0;i<argSettingArr.size();i++){
//                    citySetting[i]=(Integer.parseInt(argSettingArr.get(i).toString()));
//                }
//                g = new CovidSimulation(citySetting);
//
//                for (Object o : a)
//                {
//                    JSONObject person = (JSONObject) o;
//                    String func =  person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch(func){
//                        case "virusPlan":
//                            g.virusAttackPlan(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            break;
//                        case "TravelPlan":
//                            g.TravelPlan(Integer.parseInt(arg.get(0).toString()),Integer.parseInt(arg.get(1).toString()),Integer.parseInt(arg.get(2).toString()),
//                                    Integer.parseInt(arg.get(3).toString()),Integer.parseInt(arg.get(4).toString()));
//                            break;
//
//                        case "CityMax":
//                            count++;
//                            int ans_sol = g.CityWithTheMostPatient(Integer.parseInt(arg.get(0).toString()));
//                            Long answer = (Long) person.get("answer");
//                            int ans = Integer.parseInt(answer.toString());
//                            if(ans_sol==ans){
//                                System.out.println(count+": AC");
//                            }else{
//                                waSize++;
//                                System.out.println(count+": WA");
//                            }
//                    }
//
//                }
//            }
//            System.out.println("Score: " + (count-waSize) + " / " + count + " ");
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//}=