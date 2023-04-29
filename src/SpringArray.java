import java.util.Stack;

public class SpringArray {

    public static Spring equivalentSpring(String springExpr) {
        Stack<Spring> stack = new Stack<>();
        for (char c : springExpr.toCharArray()) {
            if (c == '{') {
                stack.push(new Spring());
            } else if (c == '[') {
                stack.push(null);
            } else if (c == '}') {
                double k = 0;
                while (!stack.isEmpty() && stack.peek() != null) {
                    k += stack.pop().getStiffness();
                }
                stack.pop(); // remove null
                stack.push(new Spring(k));
            } else if (c == ']') {
                double k = 0;
                while (!stack.isEmpty() && stack.peek() != null) {
                    k += 1 / stack.pop().getStiffness();
                }
                stack.pop(); // remove null
                stack.push(new Spring(1 / k));
            }
        }
        return stack.pop();
    }

    public static Spring equivalentSpring(String springExpr, Spring[] springs) {
        Stack<Spring> stack = new Stack<>();
        for (char c : springExpr.toCharArray()) {
            if (c == '{') {
                stack.push(springs[0]);
            } else if (c == '[') {
                stack.push(null);
            } else if (c == '}') {
                Spring s = stack.pop();
                while (!stack.isEmpty() && stack.peek() != null) {
                    s = s.inSeries(stack.pop());
                }
                stack.pop(); // remove null
                stack.push(s);
            } else if (c == ']') {
                Spring s = stack.pop();
                while (!stack.isEmpty() && stack.peek() != null) {
                    s = s.inParallel(stack.pop());
                }
                stack.pop(); // remove null
                stack.push(s);
            }
        }
        return stack.pop();
    }
}
