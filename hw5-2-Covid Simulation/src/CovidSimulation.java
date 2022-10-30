class CovidSimulation {
    private int Date = 1;
    public CovidSimulation(int[] Num_Of_Citizen) {
        //The initial number of people in each city is defined here.
    }

    public void virusAttackPlan(int city, int date){
        //Covid is a highly intelligent being, they plan their attacks carefully.
        //The date on which Covid attacks a specific city would be defined here
    }

    public void TravelPlan(int NumberOfTraveller, int FromCity, int ToCity, int DateOfDeparture, int DateOfArrival){
        //The information of travellers' plan would be written here.
        //Since everyone travel with different methods, the duration to travel from City A to B would not be constant (we tried our best to simplifty the problem instead of giving an array of data!)
    }

    public int CityWithTheMostPatient(int date){
        //return the index of city which has the most patients

        //if there are more than two cities with the same amount of patients, return the largest index value.
        //if every city is clean, please return -1.
        return -1;
    }

    public static void main(String[] args) {
        CovidSimulation sol = new CovidSimulation(new int[] {10, 100, 15, 25, 10, 13});

        sol.virusAttackPlan(0, 1);
        sol.virusAttackPlan(4, 3);
        sol.TravelPlan(3, 0, 3, 3, 4);
        sol.TravelPlan(3, 4, 0, 3, 4);

        System.out.println(sol.CityWithTheMostPatient(2));
        // output = 0

        sol.virusAttackPlan(5, 5);
        sol.TravelPlan(1, 5, 0, 5, 6);

        System.out.println(sol.CityWithTheMostPatient(4));
        // output = 3
        System.out.println(sol.CityWithTheMostPatient(8));
        // output = 5

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