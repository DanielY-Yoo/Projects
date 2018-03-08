
/**
 * If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. 
 * The sum of these multiples is 23. 
 * Find the sum of all the multiples of 3 or 5 below 1000.
 */
public class Problem_1
{
    /**
     * A method that finds sum of multiples of 3,5 below y
     * 
     * @param y: maximum
     * @return prints: sum of multiples of 3, 5 below y
     */
    public static void sumofmultiples(int y)
    {
        int sum = 0;
        for(int i = 0; i < y; i++) {
            if(i % 3 == 0 || i % 5 == 0) sum += i;
        }
        System.out.println(sum);
    }
}
