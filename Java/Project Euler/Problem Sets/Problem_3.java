public class Problem_3
{
    public static long biggestPrime(long given) {
        long biggestPrime = 0;
        long prime = 3;
        if(given % 2 == 0) {
            given /= 2; 
            biggestPrime = 2;
        }
        while(given > 1) {
            prime+=2;
            while(isPrime(prime) && given % prime == 0) {
                given /= prime;
                biggestPrime = prime;
            }
        }
        return biggestPrime;
    }
    
    public static boolean isPrime(long n) {
        if(n % 2 == 0) return false;
        for(int i = 3; i*i < n; i+=2) {
            if(n%i == 0) return false;
        }
        return true;
    }
}
