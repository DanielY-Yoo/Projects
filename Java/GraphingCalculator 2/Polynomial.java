
public class Polynomial
{
    public String[] terms; //Each term, including x's and ^'s.
    int n = 0;      //Size of the array ^
    
    double[] coeffs;
    int[] degrees;
    
    //Parse the terms in the string to doubles and add the coefficients to the array coeffs.
    public Polynomial(String s) {
        setLengthOfTerms(s);
        
        int j = 0;
        int k = 0;
        
        for(int i = 0; i < s.length() - 1; i++) {
            if(s.substring(i,i+1).equals("+") || s.substring(i,i+1).equals("-")) {
                terms[j] = s.substring(k,i);
                j++;
                k = i;
            }
        }
        terms[j] = s.substring(k);
        
        addToCoeffs();
    }
    
    private void addToCoeffs() {
        int highestDegree = 0;
        for(int i = 0; i < terms.length; i++) {
            String term = terms[i];
            int deg = 0;
            
            if(!term.equals("")) {
                if(term.indexOf("x") > -1) {
                    
                    if(term.indexOf("^") > -1) {
                        deg = Integer.parseInt(term.substring(term.indexOf("^")+1));
                    } else {
                        deg = 1;
                    }
                } else {
                    deg = 0;
                }
                
                if(deg > highestDegree) {
                    highestDegree = deg;
                }
            }
        }
        //Set the length to the highest degree
        coeffs = new double[highestDegree+1];
        degrees = new int[highestDegree+1];
        
        
        for(int i = 0; i < terms.length; i++) {
            String term = terms[i];
            double cof = 0;
            int deg = 0;
            
            if(!term.equals("")) {
                if(term.indexOf("x") > -1) {
                    
                    if(term.substring(0,1).equals("x")) {
                        cof = 1;
                    } else if(term.substring(0,2).equals("-x")) {
                        cof = -1;
                    } else {
                        if(term.indexOf("-") > -1) {
                            cof = Double.parseDouble(term.substring(0,term.indexOf("x")));
                        } else {
                            cof = Double.parseDouble(term.substring(0,term.indexOf("x")));
                        }
                    }
                    
                    if(term.indexOf("^") > -1) {
                        deg = Integer.parseInt(term.substring(term.indexOf("^")+1));
                    } else {
                        deg = 1;
                    }
                } else {
                    cof = Double.parseDouble(term.substring(0));
                    deg = 0;
                }
            }
            
            //Add each value before the end of the for-loop
            coeffs[deg] = cof;
            degrees[deg] = deg;
            
        } //End of for-loop
    }
    
    private void setLengthOfTerms(String s) {
        for(int i = 0; i < s.length(); i++) {            
            if(s.substring(i,i+1).equals("+") || s.substring(i,i+1).equals("-")) {
                n++;
            }
        }
        n++;
        terms = new String[n];
    }
    
    public double evaluate(double x) {
        double ans = 0;
        
        for(int i = 0; i < coeffs.length; i++) {
            double multiplier = 0;
            if(degrees[i] != 0) {
                multiplier = Math.pow(x,degrees[i]);
                ans += coeffs[i]*multiplier;
            }
            if(degrees[i] == 0){
                ans += coeffs[i];
            }
        }
        
        return ans;
    }
    
    public double getLowerBound() {
        double aMax = 0;
        int highestDeg = 0;
        double aN = 0;
        
        for(int i = 0; i < coeffs.length; i++) {
            if(Math.abs(coeffs[i]) > aMax) {
                aMax = coeffs[i];
            }
        }
        
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] > highestDeg) {
                highestDeg = degrees[i];
            }
        }
        aN = coeffs[highestDeg];
        
        
        double bound = 0;        
        bound = 1 + Math.abs(aMax/aN);
        
        
        return -bound;
    }
    public double getUpperBound() {
        double aMax = 0;
        int highestDeg = 0;
        double aN = 0;
        
        for(int i = 0; i < coeffs.length; i++) {
            if(Math.abs(coeffs[i]) > aMax) {
                aMax = coeffs[i];
            }
        }
        
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] > highestDeg) {
                highestDeg = degrees[i];
            }
        }
       aN = coeffs[highestDeg];
        
        
        double bound = 0;        
        bound = 1 + Math.abs(aMax/aN);
        
        
        return bound;
    }
    
    
    
    /*
     * PROBLEM: THIS METHOD IS ONLY RETURNING ONE ROOT.
       */
    
    public double[] roots() {
        double step = (getUpperBound()-getLowerBound())/10000;
        int highestDegree = 0;
        double[] rs;
        int n = 0;
        
        //Find highest degree
        for(int i = 0; i < degrees.length; i++) {
            if(degrees[i] > highestDegree) {
                highestDegree = degrees[i];
            }
        }
        rs = new double[highestDegree];
        
        //Find 
        for(double i = getLowerBound(); i < getUpperBound(); i += step) {
            //Crosses the x-axis
            if((evaluate(i) < 0 && evaluate(i+step) > 0) || (evaluate(i) > 0 && evaluate(i+step) < 0)) {
                double root = (i + (i+step))/2;
                rs[n] = root;
                n++;
           }
        }
        
        int z = 0;
        for(int i = 0; i < rs.length; i++) {
            if(rs[i] != 0) {
                z++;
            }
        }
        
        double[] newRS = new double[z];
        for(int i = 0; i < rs.length; i++) {
            if(rs[i] != 0) {
                newRS[i] = rs[i];
            }
        }
        
        
        return newRS;
    }
    
    
    
    
} //End of class
