import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Kings {
    public Kings(int[] strength, int[] range){
        
    }

    public int[] topKKings(int N){

    }


    public static void main(String[] args) {
        test t = new test(args);
    }
}
class test{
    public test(String[] args){
        Kings sol;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            for(Object CaseInList : all){
                JSONArray a = (JSONArray) CaseInList;
                int q_cnt = 0, wa = 0,ac = 0;
                for (Object o : a) {
                    q_cnt++;
                    JSONObject person = (JSONObject) o;
                    JSONArray arg_str = (JSONArray) person.get("strength");
                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
                    Long arg_k = (Long) person.get("k");
                    JSONArray arg_ans = (JSONArray) person.get("answer");
                    int STH[] = new int[arg_str.size()];
                    int RNG[] = new int[arg_str.size()];
                    int k = Integer.parseInt(arg_k.toString());

                    int Answer[] = new int[arg_ans.size()];
                    int Answer_W[] = new int[arg_ans.size()];
                    for(int i=0;i<arg_ans.size();i++){
                        Answer[i]=(Integer.parseInt(arg_ans.get(i).toString()));
                    }
                    for(int i=0;i<arg_str.size();i++){
                        STH[i]=(Integer.parseInt(arg_str.get(i).toString()));
                        RNG[i]=(Integer.parseInt(arg_rng.get(i).toString()));
                    }
                    sol = new Kings(STH,RNG);
                    Answer_W = sol.topKKings(k);
                    for(int i=0;i<arg_ans.size();i++){
                        if(Answer_W[i]==Answer[i]){
                            if(i==arg_ans.size()-1){
                                System.out.println(q_cnt+": AC");
                            }
                        }else {
                            wa++;
                            System.out.println(q_cnt+": WA");
                            break;
                        }
                    }

                }
                System.out.println("Score: "+(q_cnt-wa)+"/"+q_cnt);

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
