//import java.util.Arrays;
import edu.princeton.cs.algs4.Stack;

class Warrior {
    int Strength;
    int Range;
    int Index;
    Warrior(int str, int rng, int i) {
        Strength=str;
        Range=rng;
        Index=i;
    }
}
class Warriors {
    public int[] warriors(int[] strength, int[] range) {
        Warrior[] warriors = new Warrior[strength.length];
        for (int i = 0; i < strength.length; i++) {
            warriors[i] = new Warrior(strength[i], range[i], i);
        }

        Stack<Warrior> leftToRightStack = new Stack<Warrior>();
        Stack<Warrior> rightToLeftStack = new Stack<Warrior>();
        int[] answer = new int[2 * strength.length];

        for (int i = 0; i < warriors.length; i++){
            while (!leftToRightStack.isEmpty() && leftToRightStack.peek().Strength < warriors[i].Strength) {
                leftToRightStack.pop();
            }
            if (leftToRightStack.isEmpty()) {
                answer[2 * i] = (warriors[i].Range >= warriors[i].Index) ? 0 : warriors[i].Index - warriors[i].Range;
            } else {
                answer[2 * i] = (warriors[i].Range >= (warriors[i].Index - leftToRightStack.peek().Index)) ? leftToRightStack.peek().Index + 1 : warriors[i].Index - warriors[i].Range;
            }
            leftToRightStack.push(warriors[i]);

            while(!rightToLeftStack.isEmpty() && rightToLeftStack.peek().Strength < warriors[warriors.length - 1 - i].Strength) {
                rightToLeftStack.pop();
            }
            if (rightToLeftStack.isEmpty()) {
                answer[answer.length - 2 * i -1] = (warriors[warriors.length - 1 - i].Range >= (warriors.length - warriors[warriors.length - 1 - i].Index)) ? warriors.length - 1 : warriors[warriors.length - 1 - i].Index + warriors[warriors.length - 1 - i].Range;
            } else {
                answer[answer.length - 2 * i -1] = (warriors[warriors.length - 1 - i].Range >= (rightToLeftStack.peek().Index - warriors[warriors.length - 1 - i].Index)) ? rightToLeftStack.peek().Index - 1 : warriors[warriors.length - 1 - i].Index + warriors[warriors.length - 1 - i].Range;
            }
            rightToLeftStack.push(warriors[warriors.length - 1 - i]);
        }
        return answer;
    }

//    public static void main(String[] args) {
//        Warriors sol = new Warriors();
//        System.out.println(Arrays.toString(
//                sol.warriors(new int[] {11, 13, 11, 7, 15},
//                             new int[] {1, 8, 1, 7, 2})));
//    }
}
