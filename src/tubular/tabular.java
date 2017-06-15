package tubular;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;

public class tabular {
   static String steps[]=new String[1000000];
    int count=0;
    String [] allSteps = new String[100000];
    int indexOfArr = 0;
    String alternatriveArr[];
    String essentialPrime = "";
    int indexOfEssentialPrime []; 
    static String[] binn ;
    int onlyOneArray[];
    String [] strOfMaxCoverages = new String[100000]; 
    int[] arrOfMaxCoverages = new int[100000]; 
    int indexx = 0;
    int ref = 0;
    static String[] finum ;
    static String[] finum2 ;
    static String[] pprime=new String[100000];
    static String[] fprimenum=new String[100000];
    String[] prime = new String[1000];
   
  
    private boolean checkValidInput(String expression , int inputt){
        String express = expression.replaceAll("\\s+", " ").trim();
        String [] ex = express.split(" ");
        int [] exInInteegerForm = new int[ex.length];
        // test anyterm is smaller than or equal to maxterm
        double maxTerm = Math.pow(2, (double)inputt) - 1;
        for(int i =0 ;i<ex.length;i++){
            exInInteegerForm[i] = Integer.parseInt(ex[i]);
            if(exInInteegerForm[i] > maxTerm){
                return false;
            }
        }
        // to check any invalid chars except digits
        String expressWithoutSpaces = express.replace(" ", "");
        for(int j=0 ;j<expressWithoutSpaces.length() ;j++){
            char x = expressWithoutSpaces.charAt(j);
            if(!Character.isDigit(x)){
                return false;
            }
        }
        // for repeted number
        for(int k=0 ;k<ex.length;k++){
            for(int j = k+1 ;j<ex.length;j++){
                if(ex[k].equals(ex[j])){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void initalize (String []arr){
        alternatriveArr = new String[arr.length];
        System.arraycopy(arr, 0, alternatriveArr ,0, arr.length);
        onlyOneArray  = new int[binn.length];
        Arrays.fill(onlyOneArray , -1);
        indexOfEssentialPrime = new int[arr.length];
        Arrays.fill(indexOfEssentialPrime , -1);
        Arrays.fill(arrOfMaxCoverages, -1);
    }
    
 // parameter is string representing minterms of each term
    private int[] checkOnlyOne (String[] arr){
        int i ;
        for(i =0 ;i<binn.length ;i++){
            int onlyOne = 0;
            int indexOfminTermMustToken = 0;
            boolean exist = false;
            for(int j =0  ;j<arr.length; j++){
                arr[j]=" "+arr[j]+" ";
                String a = " "+binn[i]+" ";
                if(arr[j].contains(a)){
                    onlyOne++;
                    indexOfminTermMustToken = j;
                }
                arr[j]=arr[j].trim();
            } 
            // onlyOneArray contains numbers of minterms must be token
            if(onlyOne == 1){
                // remove repeted onlyOneArray
                for(int k =0 ;k< onlyOneArray.length;k++){
                    if(onlyOneArray[k]==indexOfminTermMustToken){
                        exist= true;
                        break;
                    }
                }
                if(!exist){
                    indexOfEssentialPrime[indexOfArr] = indexOfminTermMustToken;
                    indexOfArr++;
                    onlyOneArray[i]= indexOfminTermMustToken;
                    removeTokenMinTermsFormStringArr(arr[indexOfminTermMustToken]);
                    break;
                }        
            }
        }
        if(i<binn.length)
            checkOnlyOne(alternatriveArr);
        return onlyOneArray;
    }
    
    private void removeTokenMinTermsFormStringArr(String token){
        token = token.trim();
        token = token.replaceAll("\\s+", " ");
        String [] tokenn = token.split(" ");
        for(int k =0 ;k<alternatriveArr.length ;k++){
            alternatriveArr[k] = " " + alternatriveArr[k] +" ";
            for(int i=0;i<tokenn.length;i++){
                if(tokenn[i]!=" "){
                    alternatriveArr[k] = alternatriveArr[k].replace(" "+tokenn[i]+ " "," ");
                }
            }
            alternatriveArr[k] = alternatriveArr[k].replaceAll("\\s+", " ").trim();
        }
    }
    
    private boolean IsAlternativeEmpty(String []arr){
        String term = "";
        boolean empty=true;
        for(int i =0 ;i<arr.length; i++){
            term = arr[i].trim();
            term = arr[i].replaceAll("( )+", "$1");
            if(!term.equals("")){
                empty=false;
                break;
            }
        }
        if(empty){
            return true;
        }
        else{
            return false;
        }
    }
    
    private int findEssentialPrimeImplicants(String[] arr){
        if(IsAlternativeEmpty(arr)){
            return 0;
        }
        checkOnlyOne(arr);
        int maxnoOfCoverages = 0;
        int indexOfmaxnoOfCoverages = -1;
        int indexOfmaxnoOfCoverages2 = -1;
        boolean a =false;
        for(int i =0 ;i<arr.length;i++){
            int noOfcoverages = alternatriveArr[i].split(" ").length;
            if(alternatriveArr[i].equals("")){
                noOfcoverages = 0;
            }
            if(maxnoOfCoverages < noOfcoverages){
                maxnoOfCoverages = noOfcoverages;
                indexOfmaxnoOfCoverages = i;
                a= true;
            }
        }
        if(a){
            indexOfEssentialPrime[indexOfArr]=indexOfmaxnoOfCoverages;
            indexOfArr++;
            removeTokenMinTermsFormStringArr(alternatriveArr[indexOfmaxnoOfCoverages]);
            essentialPrime += indexOfmaxnoOfCoverages; 
            int totalCoverage = maxnoOfCoverages+ findEssentialPrimeImplicants(alternatriveArr);   
        }
        return 0;
    }
    
    private String[] removeDonotCare (String[] uncorrecttStr , String donotcare){
        String[] correctStr = new String[uncorrecttStr.length];
        String[] donotcareParts = donotcare.replaceAll("\\s+", " ").trim().split(" ");
        Arrays.fill(correctStr, "");
        for(int i=0;i<uncorrecttStr.length ;i++ ){
            String [] part = uncorrecttStr[i].replaceAll("\\s+", " ").trim().split(" ");
            for(int j=0 ;j<donotcareParts.length ;j++){
                for(int k=0 ;k<part.length ;k++){
                    if(part[k].equals(donotcareParts[j])){
                        part[k] = "";
                    }
                }
            }
            for(int j = 0 ; j<part.length ; j++){
                correctStr[i]+= part[j] + " ";
            }
            correctStr[i] = correctStr[i].replaceAll("\\s+", " ").trim();
        }
        return correctStr;
    }
    
    private String[] printEssentialImplicants(String[] binx){
        // generates inputs
        String[] printedForms = new String [binx.length];
        Arrays.fill(printedForms, "");
        for(int j = 0 ;j<indexOfArr ; j++){
            String current = binx[indexOfEssentialPrime[j]]; 
            for(int k=0 ;k< current.length();k++){
                if(current.charAt(k) == '0'){
                    printedForms[j]+= Character.toChars(65+k)[0] + "'";
                }
                else if(current.charAt(k) == '1'){
                    printedForms[j]+= Character.toChars(65+k)[0];
                }
                else if (current.charAt(k) == '-'){
                }
            }
        }
        return printedForms;
    }
 
    public String[] min(String minterm, int input) {
        String bin[] = minterm.split(" ");
        pprime=bin;
       int  num[]= new int[bin.length];
        String str[] = new String[bin.length];
        for (int i = 0; i < bin.length; i++) {
            num[i] = Integer.parseInt(bin[i]);
            str[i] = new String();
            str[i] = convertBinary(num[i]);
            int numberOfDigit = input - str[i].length();
            for (int j = 0; j < numberOfDigit; j++)
                str[i] = "0" + str[i];
        }
        return str;
 
    }
 
    public String convertBinary(int num) {
        StringBuilder binary = new StringBuilder();
        while (num > 0) {
            binary.append(num % 2);
            num = num / 2;
        }
        binary.reverse();
        return binary.toString();
    }
 
    
    boolean flag=true;
    public String[] rec(String org[], int input, String [] bprime) {
        int k = 0;
        boolean[] check = new boolean[org.length];
        String[] res = new String[100];
        String[] primenum=new String[20];
        String temp = new String();
        for (int i = org.length - 1; i >= 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                temp += Math.abs(Integer.parseInt(org[i]) - Integer.parseInt(org[j]));
                int numberOfDigit = Math.abs(input - temp.length());
                for (int h = 0; h < numberOfDigit; h++)
                    temp = "0" + temp;
                if (temp.length() - temp.replace("1", "").length() == 1
                        && temp.length() - temp.replace("0", "").length() == input - 1) {
                    check[i] = true;
                    check[j] = true;
                    res[k] = new String();
                    int indexx = temp.indexOf("1");
                    primenum[k]=new String();
                    primenum[k] = bprime[i]+" "+bprime[j];
                    res[k] = org[i];
                    res[k] = res[k].substring(0, indexx) + '5' + res[k].substring((indexx + 1));
                    //if (k!=0&&!res[k].equals(org[k-1]))
                    //System.out.println(res[k].replace('5', '-')+"<--->"+primenum[k]);
                    k++;
                }
                temp = new String();
            }
        }
        for (int y = 0; y < org.length; y++) {
            if (!check[y]) {
                fprimenum[ref]=bprime[y];
                prime[ref] = new String();
                prime[ref] = org[y];
                ref++;
            }
        }
        int c = 2713;
        String symbol =  "\\u" + c;
        String s = Character.toString((char)c);
        for (int h=0;h<org.length;h++){
            if ((h==0||!org[h].equals(org[h-1]))&&!check[h]){
                
            steps[count]=new String();
            steps[count]=org[h].replace('5', '-')+" == > "+bprime[h]+" == > Prime!!";
            count++;
            }
            else if ((h==0||!org[h].equals(org[h-1]))&&check[h]){
                steps[count]=new String();

                steps[count]=org[h].replace('5', '-')+" == > "+bprime[h]+" == > X";
                count++;
            }
        }
        steps[count]=new String();
        steps[count]="---------------Next Step---------------";
        count++;
        String[] resanum = new String[k];
        String[] resa = new String[k];
        for (int l = 0; l < k; l++)
            {
            resanum[l]=primenum[l];
            resa[l] = res[l];
           
            }
    
        if (allfalse(check)) {
            String[] fiprime = new String[ref];
            finum = new String[ref];
            for (int i = 0; i < ref; i++) {
                fiprime[i] = new String();
                finum[i]=new String();
                finum[i]=fprimenum[i];
                fiprime[i] = prime[i];
                fiprime[i] = fiprime[i].replace('5', '-');
            }
           
            fiprime = removeDuplicates(fiprime);
            return fiprime;
        }
 
        else
 
            return rec(resa, input,primenum);
 
    }   
    private static boolean allfalse(boolean[] values) {
        for (boolean value : values) {
            if (value)
                return false;
        }
        return true;
    }
 
    public static String[] removeDuplicates(String[] arr) {
 
        int end = arr.length;
 
        for (int i = 0; i < end; i++) {
            for (int j = i + 1; j < end; j++) {
                if (arr[i].equals(arr[j])) {
                    int shiftLeft = j;
                    for (int k = j + 1; k < end; k++, shiftLeft++) {
                        arr[shiftLeft] = arr[k];
                        finum[shiftLeft]=finum[k];
                    }
                    end--;
                    j--;
                }
            }
        }
        finum2 = new String[end];
        String[] whitelist = new String[end];
        for (int i = 0; i < end; i++) {
            finum2[i] = finum[i];
            whitelist[i] = arr[i];
        }
        return whitelist;
    }
 
    public static void main(String[] args) {
        
//        binn = "0 1 4 8 10 12 20 23 28 30 32 35 39 44 45 49 51 56 58 61 63 71 73 76 81 85 86 88 91 93 95 96 101 103 105 111 118 121 127".split(" ");
        
        while(true){
        boolean valid = false;
        System.out.println("Enter file to scan from file or any key to scan from console");
        Scanner inputt = new Scanner(System.in);
        String choose = inputt.nextLine();
        String [] temp = new String[10];
        int counter =0 ;
        if(choose.equals("file")){
            tabular test = new tabular();
            String inputNumberss ;
            BufferedReader buffer = null;   
            String path;
            String outputPath;
            try{
                System.out.println("Enter Path");
                path = inputt.nextLine().replace("\\", "/");
                System.out.println("Enter output Path");
                outputPath = inputt.nextLine().replace("\\", "/");
                FileInputStream ss = new FileInputStream(path);
                buffer = new BufferedReader(new InputStreamReader(ss));
                while((temp[counter] = buffer.readLine()) != null){
                    counter++;
                }
                
                String minTerms = "";
                String donotcareTerms="";
                int inputNumbers=-1;
                
                try{
                inputNumberss = temp[0];
                inputNumberss = inputNumberss.replaceAll("\\s+", " ").trim();
                inputNumbers = Integer.parseInt(inputNumberss);
                minTerms = temp[1];
                donotcareTerms = temp[2];
                minTerms = minTerms.replaceAll("\\s+", " ").trim();
                donotcareTerms = donotcareTerms.replaceAll("\\s+", " ").trim();
                try{
                    valid = test.checkValidInput((minTerms+ " " +donotcareTerms).replaceAll("\\s+", " ").trim(), inputNumbers);
                }
                catch(NumberFormatException e){
                    valid = false;
                    if(minTerms.replaceAll("\\s+", " ").trim().equals("")&& donotcareTerms.replaceAll("\\s+", " ").trim().equals("")){
                        System.out.println("1");
                        System.out.println("");
                        continue;
                    }
                }
                }
                catch(NumberFormatException e){
                    valid = false;
                }
                if(valid){
                    String [] a = test.min((minTerms+ " " +donotcareTerms).replaceAll("\\s+", " ").trim(), inputNumbers);
                    String [] b = test.rec(a, inputNumbers, pprime);
                    String d [] = test.removeDonotCare(finum2,donotcareTerms);
                    binn =( minTerms + " " + donotcareTerms ).replaceAll("\\s+", " ").trim().split(" ");
                    test.initalize(d);
                    PrintWriter writer = new PrintWriter(outputPath); 
                    for (int i = 0; (steps[i]!= null) &&(i < steps.length); i++) {
                        writer.println(steps[i]);
                    }
                    
                 /*   for (int i = 0; i < finum2.length; i++) {
                        writer.println(b[i]+" ==> "+finum2[i]);
                    }*/
                    if(minTerms.replaceAll("\\s+", " ").trim().equals("")){
                        writer.println("");
                        writer.println("final result == > 0");
                        writer.close();
                    }
                    else if(minTerms.replaceAll("\\s+", " ").trim().split(" ").length == Math.pow(2, (double)inputNumbers)){
                        writer.println("");
                        writer.println("final result == > 1");
                        writer.close();
                    }
                    else  {
                        int n = test.findEssentialPrimeImplicants(d);
                        String c [] = test.printEssentialImplicants(b);
                        String total = "";
                        for (int i = 0; i < c.length; i++) {
                            total += c[i] ;
                            if(i!=c.length-1){
                                total+="+";
                            }
                        }
                        String dd=total;
                        int count2=total.length()-1;
                        while(dd.charAt(count2)=='+'){
                          dd=dd.substring(0,count2);
                          count2--;

                        }   
                        /*
                        for (int i = 0; i < finum2.length; i++) {
                            writer.println(b[i]+" ==> "+finum2[i]);
                        } */
                        writer.println(dd);
                        writer.close();
                    } 
                 } 
                    else{
                        System.out.println("Error .. try again");
                        continue;
                    }
                
            }
            catch(Exception e){
                System.out.println("invalid path");
            }
            continue;
        }
        else{
        
        String minTerms = "";
        String donotcareTerms="";
        int inputNumbers=-1;
        tabular test = new tabular();
        
        try{
        System.out.println("Please Enter no of input varibles");
        String inputNumberss = inputt.nextLine();
        inputNumberss = inputNumberss.replaceAll("\\s+", " ").trim();
        inputNumbers = Integer.parseInt(inputNumberss);
        System.out.println("Please Enter Min terms separetd by spaces");
        minTerms = inputt.nextLine();
        System.out.println("Please Enter don't care terms separetd by spaces if no don't care click space");
        donotcareTerms = inputt.nextLine();
        System.out.println("");
        minTerms = minTerms.replaceAll("\\s+", " ").trim();
        donotcareTerms = donotcareTerms.replaceAll("\\s+", " ").trim();
        try{
            valid = test.checkValidInput((minTerms+ " " +donotcareTerms).replaceAll("\\s+", " ").trim(), inputNumbers);
        }
        catch(NumberFormatException e){
            valid = false;
            if(minTerms.replaceAll("\\s+", " ").trim().equals("")&& donotcareTerms.replaceAll("\\s+", " ").trim().equals("")){
                System.out.println("1");
                System.out.println("");
                continue;
            }
        }
        }
        catch(NumberFormatException e){
            valid = false;
        }
        if(valid){
            String [] a = test.min((minTerms+ " " +donotcareTerms).replaceAll("\\s+", " ").trim(), inputNumbers);
            String [] b = test.rec(a, inputNumbers, pprime);
            
            for (int i = 0;(steps[i]!= null) && (i < steps.length) ; i++) {
                System.out.println(steps[i]);
            }
            System.out.println("");
            String d [] = test.removeDonotCare(finum2,donotcareTerms);
            binn =( minTerms + " " + donotcareTerms ).replaceAll("\\s+", " ").trim().split(" ");
            test.initalize(d);
            if(minTerms.replaceAll("\\s+", " ").trim().equals("")){
                System.out.println("0");
            }
            else if(minTerms.replaceAll("\\s+", " ").trim().split(" ").length == Math.pow(2, (double)inputNumbers)){
                System.out.println("1");
            }
            else  {
                int n = test.findEssentialPrimeImplicants(d);
                String c [] = test.printEssentialImplicants(b);
                String total = "";
                for (int i = 0; i < c.length; i++) {
                    total += c[i] ;
                    if(i!=c.length-1){
                        total+="+";
                    }
                }
                String dd=total;
                int count2=total.length()-1;
                while(dd.charAt(count2)=='+'){
                  dd=dd.substring(0,count2);
                  count2--;

                }
                System.out.println(dd);

            } 
         } 
            else{
                System.out.println("Error .. try again");
                continue;
            }
        }
       }
   }
}