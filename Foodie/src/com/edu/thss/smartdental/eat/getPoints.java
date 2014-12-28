package com.edu.thss.smartdental.eat;

public class getPoints {
     
    public getPoints(double[] x, String[][] advice) {
        
	double result = 0;
	for(int i = 0; i<14; i++)
	{
	    double element = element_points(x[i], miu[i], theta[i], beta[i]);
	    feedback(element, i ,x[i], advice);
	    result += element;
	}

	score = result;
    }
    
    public double score = 0;
    public static String feedback_score = "";
    public double Ca_score = 0;
    public double K_score = 0;
    public double Fe_score = 0;
    public double Mg_score = 0;
    public double Zn_score = 0;
    public double VA_score = 0;
    public double VC_score = 0;
    public double VD_score = 0;
    public double VE_score = 0;
    public double VB1_score = 0;
    public double VB2_score = 0;
    public double VB6_score = 0;
    public double VB12_score = 0;
    public double protein_score = 0;
    
    private static double PI = 3.14159;
    private static double[] miu = {800, 2000, 17.5, 350, 13, 0.75, 100, 0.005, 14, 1.5, 1.7, 1.9, 0.0024, 85};
    private static double[] theta = {327.56, 547.49, 12.40, 104.5, 273.14, 1.41, 250.71, 0.40, 221.12, 17.62, 17.56, 33.01, 0.83, 254.6};
    private static double[] beta = {5865, 9444, 222, 1871, 4890, 25, 4489, 7, 3959, 316, 314, 591, 15, 4558}; 
    private static String[] Name = {"钙","钾","铁","镁","锌","维生素A","维生素C","维生素D","维生素E","维生素B1","维生素B2","维生素B6","维生素B12","蛋白质"};
    
    private static double element_points(double x, double miu, double theta, double beta)
    {
	double result = (beta/(Math.sqrt(2*PI)*theta))*Math.exp(-(x-miu)*(x-miu)/(2*theta*theta));
	if(x == 0)
	    result = 0;
	return result;
    }
    
    private static void feedback(double x, int i, double elm, String[][] advice)
    {
	double feedback_result = x*14;
	if(feedback_result < 60)
	{
	    if(elm<miu[i])
		feedback_score += "您今天的" + Name[i] + "摄取较少，需要多吃" + advice[i][0]+advice[i][1]+advice[i][2]+"\n";
	}
    }
}
