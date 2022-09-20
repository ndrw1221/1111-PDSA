import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class BoardGame {

    public BoardGame(int h, int w) // create a board of size h*w
    public void putStone(int[] x, int[] y, char stoneType) // put stones of the specified type on the board according to the coordinates
    public boolean surrounded(int x, int y) // Answer if the stone and its connected stones are surrounded by another type of stones
    public char getStoneType(int x, int y) // Get the type of the stone at (x,y)
    public int countConnectedRegions() // Get the number of connected regions in the board, including both types of the stones



    public static void test(String[] args){
        BoardGame g;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONArray a = (JSONArray) CaseInList;
                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                //Board Setup
                JSONObject argsSeting = (JSONObject) a.get(0);
                a.remove(0);

                JSONArray argSettingArr = (JSONArray) argsSeting.get("args");
                g = new BoardGame(
                        Integer.parseInt(argSettingArr.get(0).toString())
                        ,Integer.parseInt(argSettingArr.get(1).toString()));

                for (Object o : a)
                {
                    JSONObject person = (JSONObject) o;

                    String func =  person.get("func").toString();
                    JSONArray arg = (JSONArray) person.get("args");

                    switch(func){
                        case "putStone":
                            int xArray[] = JSONArraytoIntArray((JSONArray) arg.get(0));
                            int yArray[] = JSONArraytoIntArray((JSONArray) arg.get(1));
                            String stonetype =  (String) arg.get(2);

                            g.putStone(xArray,yArray,stonetype.charAt(0));
                            break;
                        case "surrounded":
                            Boolean answer = (Boolean) person.get("answer");
                            testSize++;
                            System.out.print(testSize + ": " + func + " / ");
                            Boolean ans = g.surrounded(
                                    Integer.parseInt(arg.get(0).toString()),
                                    Integer.parseInt(arg.get(1).toString())
                            );
                            if(ans==answer){
                                System.out.println("AC");
                            }else{
                                waSize++;
                                System.out.println("WA");
                            }
                            break;
                        case "countConnectedRegions":
                            testSize++;
                            int ans2 = Integer.parseInt(arg.get(0).toString());
                            int ansCR = g.countConnectedRegions();
                            System.out.print(testSize + ": " + func + " / ");
                            if(ans2==ansCR){
                                System.out.println("AC");
                            }else{
                                waSize++;
                                System.out.println("WA");
                            }
                    }

                }
                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int[] JSONArraytoIntArray(JSONArray x){
        int sizeLim = x.size();
        int MyInt[] = new int[sizeLim];
        for(int i=0;i<sizeLim;i++){
            MyInt[i]= Integer.parseInt(x.get(i).toString());
        }
        return MyInt;
    }

    public static void main(String[] args) {//mian
        test(args);
    }

}
