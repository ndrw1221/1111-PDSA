//import java.util.Arrays;
import java.util.Arrays;
import java.util.Objects;
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
    int[] recoveryDays;
    int[] maxRecoveryDays;

    class Event implements Comparable<Event> {
        String type;
        int date;
        int dateOfArrival;
        int ToCity;
        int FromCity;
        int numberOfTraveler;
        int dateOfRecovery;

        public int compareTo(Event o) {
            if (this.date == o.date) return this.numberOfTraveler - o.numberOfTraveler;
            return this.date - o.date;
        }
    }

    public void virusAttackPlan(int city, int date) {
        Event eventVirusAttack = new Event();
        eventVirusAttack.type = "Virus attack";
        eventVirusAttack.ToCity = city;
        eventVirusAttack.date = date;

        eventMinPQ.insert(eventVirusAttack);
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival) {
        Event eventDeparture = new Event();

        eventDeparture.type = "Departure";
        eventDeparture.date = DateOfDeparture;
        eventDeparture.dateOfArrival = DateOfArrival;
        eventDeparture.ToCity = ToCity;
        eventDeparture.FromCity = FromCity;
        eventDeparture.numberOfTraveler = NumberOfTraveller;

        eventMinPQ.insert(eventDeparture);
    }

    public CovidSimulation(int[] Num_Of_Citizen) {
        //The initial number of people in each ToCity is defined here.
        cities = Num_Of_Citizen.clone();
        recoveryDays = new int[Num_Of_Citizen.length];
        maxRecoveryDays = new int[Num_Of_Citizen.length];
    }

    public void DoVirusAttackPlan(int city, int date){
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific ToCity would be defined here
        if (recoveryDays[city] <= date) {
            recoveryDays[city] = date + 4;
            maxRecoveryDays[city] = date + 7;
        }
    }

    public void DoEventDeparture(int NumberOfTraveller, int ToCity, int DateOfArrival, int FromCity) {
        cities[FromCity] -= NumberOfTraveller;

        Event eventArrival = new Event();
        eventArrival.type = "Arrival";
        eventArrival.date = DateOfArrival;
        eventArrival.ToCity = ToCity;
        eventArrival.dateOfRecovery = recoveryDays[FromCity];
        eventArrival.numberOfTraveler = NumberOfTraveller;

        eventMinPQ.insert(eventArrival);
    }

    public void DoEventArrival(int NumberOfTraveller, int ToCity, int DateOfArrival, int DateOfRecovery) {
        cities[ToCity] += NumberOfTraveller;

        // If traveller already recovered on arrival or recovery day of FromCity earlier than that of ToCity
        if (DateOfRecovery <= DateOfArrival || DateOfRecovery <= recoveryDays[ToCity]) return;

        // If travels to an uninfected or recovered city
        if (recoveryDays[ToCity] == 0 || recoveryDays[ToCity] <= DateOfArrival) {
            recoveryDays[ToCity] = DateOfArrival + 4;
            maxRecoveryDays[ToCity] = DateOfArrival + 7;
            return;
        }

        // If a city is already infected and its recovery day of FromCity later than max recovery day of ToCity
        if (DateOfRecovery > maxRecoveryDays[ToCity]) {
           recoveryDays[ToCity] = maxRecoveryDays[ToCity];
           return;
        }

        recoveryDays[ToCity] = DateOfRecovery;
    }

    public int CityWithTheMostPatient(int date){
        //return the index of ToCity which has the most patients

        //if there are more than two cities with the same amount of patients, return the largest index value.
        //if every ToCity is clean, please return -1.

        while(!eventMinPQ.isEmpty() && eventMinPQ.min().date <= date) {
            Event event = eventMinPQ.delMin();
            Date = event.date;

            if (Objects.equals(event.type, "Virus attack")) {
                DoVirusAttackPlan(event.ToCity, event.date);
            } else if (Objects.equals(event.type, "Departure")) {
                DoEventDeparture(event.numberOfTraveler, event.ToCity, event.dateOfArrival, event.FromCity);
            } else {
                DoEventArrival(event.numberOfTraveler, event.ToCity, event.date, event.dateOfRecovery);
            }
        }

        int max = -1;
        boolean isAllRecovered = true;
        for (int i = 0; i < recoveryDays.length; i++) {
            if (recoveryDays[i] > date) {
                //If a city IS infected
                isAllRecovered = false;
                if (max == -1 || cities[i] >= cities[max]) {
                    //Get infected ToCity with most patients
                    max = i;
                }
            }
        }

        if (isAllRecovered) return -1;
        else return max;
    }

    public static void main(String[] args) {
//        test t = new test(args);

//        CovidSimulation sol = new CovidSimulation(new int[] {5, 5, 5, 5, 5});
//        sol.virusAttackPlan(0, 1);
//        sol.virusAttackPlan(1, 2);
//        sol.virusAttackPlan(2, 3);
//        sol.virusAttackPlan(3, 4);
//        sol.virusAttackPlan(4, 5);
//        sol.TravelPlan(1,1,2,5,6);


//        System.out.println(sol.CityWithTheMostPatient(5));

//
//        while(!sol.eventMinPQ.isEmpty()) {
//            System.out.print("Day: " + sol.eventMinPQ.min().date + ", Event: ");
//            if (sol.eventMinPQ.min().type == "Virus attack") {
//                System.out.println("Virus Attack Plan, City: " + sol.eventMinPQ.min().ToCity);
//            } else if (sol.eventMinPQ.min().type == "Departure") {
//                System.out.println("Departure, From City: " + sol.eventMinPQ.min().ToCity + ", Number of traveller: " + sol.eventMinPQ.min().numberOfTraveler);
//            } else {
//                System.out.println("Arrival, To City: " + sol.eventMinPQ.min().ToCity + ", Number of traveller: " + sol.eventMinPQ.min().numberOfTraveler);
//            }
//            sol.eventMinPQ.delMin();
//        }
//
//        System.out.println("Date: " + sol.Date);
//        System.out.println(Arrays.toString(sol.cities));
//        System.out.println(Arrays.toString(sol.recoveryDays));
//        System.out.println(Arrays.toString(sol.maxRecoveryDays));
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
//}